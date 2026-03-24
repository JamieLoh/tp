package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.application.ApplicationMatchesPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_noPrefix_throwsParseException() {
        assertParseFailure(parser, "Google",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_namePrefix_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new ApplicationMatchesPredicate(
                        "Google", null, null, null,
                        null, null, null, Collections.emptyList()));
        assertParseSuccess(parser, "n/Google", expectedFindCommand);
    }

    @Test
    public void parse_multiplePrefixes_returnsFindCommand() {
        FindCommand expected =
                new FindCommand(new ApplicationMatchesPredicate(
                        "Google", "Engineer", null, null,
                        null, null, "Pending", Collections.emptyList()));

        assertParseSuccess(parser, "n/Google r/Engineer s/Pending", expected);
    }

    @Test
    public void parse_tagPrefixes_returnsFindCommand() {
        FindCommand expected =
                new FindCommand(new ApplicationMatchesPredicate(
                        null, null, null, null,
                        null, null, null, Arrays.asList("AI", "ML")));

        assertParseSuccess(parser, "t/AI t/ML", expected);
    }

    @Test
    public void parse_emptyPrefix_returnsFindCommand() {
        FindCommand expected =
                new FindCommand(new ApplicationMatchesPredicate(
                        null, null, "", null,
                        null, null, null, Collections.emptyList()));

        assertParseSuccess(parser, "e/", expected);
    }

    @Test
    public void parse_tagEmptyAndNormal_returnsFindCommand() {
        FindCommand expected =
                new FindCommand(new ApplicationMatchesPredicate(
                        null, null, null, null,
                        null, null, null, Arrays.asList("", "AI")));

        assertParseSuccess(parser, "t/ t/AI", expected);
    }

}

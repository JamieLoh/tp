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

    // =========================
    // Empty values
    // =========================

    @Test
    public void parse_emptyNamePrefix_returnsFindCommand() {
        FindCommand expected =
                new FindCommand(new ApplicationMatchesPredicate(
                        "", null, null, null,
                        null, null, null, Collections.emptyList()));

        assertParseSuccess(parser, "n/", expected);
    }

    @Test
    public void parse_emptyEmailPrefix_returnsFindCommand() {
        FindCommand expected =
                new FindCommand(new ApplicationMatchesPredicate(
                        null, null, "", null,
                        null, null, null, Collections.emptyList()));

        assertParseSuccess(parser, "e/", expected);
    }

    @Test
    public void parse_allEmptyPrefixes_returnsFindCommand() {
        FindCommand expected =
                new FindCommand(new ApplicationMatchesPredicate(
                        "", "", "", "", "", "", "",
                        Collections.singletonList("")));

        assertParseSuccess(parser, "n/ r/ e/ w/ a/ d/ s/ t/", expected);
    }

    @Test
    public void parse_emptyTag_returnsFindCommand() {
        FindCommand expected =
                new FindCommand(new ApplicationMatchesPredicate(
                        null, null, null, null,
                        null, null, null, Collections.singletonList("")));

        assertParseSuccess(parser, "t/", expected);
    }

    @Test
    public void parse_tagEmptyAndNormal_returnsFindCommand() {
        FindCommand expected =
                new FindCommand(new ApplicationMatchesPredicate(
                        null, null, null, null,
                        null, null, null, Arrays.asList("", "AI")));

        assertParseSuccess(parser, "t/ t/AI", expected);
    }

    // =========================
    // Date - Valid
    // =========================

    @Test
    public void parse_validDate_returnsFindCommand() {
        FindCommand expected =
                new FindCommand(new ApplicationMatchesPredicate(
                        null, null, null, null,
                        null, "01-12-2025", null, Collections.emptyList()));

        assertParseSuccess(parser, "d/01-12-2025", expected);
    }

    // =========================
    // Date - Invalid
    // =========================

    @Test
    public void parse_invalidDateFormat_throwsParseException() {
        assertParseFailure(parser, "d/1-12-2025",
                FindCommandParser.MESSAGE_INVALID_DATE);
    }

    @Test
    public void parse_invalidCalendarDate_throwsParseException() {
        assertParseFailure(parser, "d/32-01-2025",
                FindCommandParser.MESSAGE_INVALID_DATE);
    }

    @Test
    public void parse_invalidMonth_throwsParseException() {
        assertParseFailure(parser, "d/01-13-2025",
                FindCommandParser.MESSAGE_INVALID_DATE);
    }

    // =========================
    // Status - Valid
    // =========================

    @Test
    public void parse_validStatus_returnsFindCommand() {
        FindCommand expected =
                new FindCommand(new ApplicationMatchesPredicate(
                        null, null, null, null,
                        null, null, "pending", Collections.emptyList()));

        assertParseSuccess(parser, "s/Pending", expected);
    }

    @Test
    public void parse_validStatusLowerCase_returnsFindCommand() {
        FindCommand expected =
                new FindCommand(new ApplicationMatchesPredicate(
                        null, null, null, null,
                        null, null, "pending", Collections.emptyList()));

        assertParseSuccess(parser, "s/pending", expected);
    }

    // =========================
    // Status - Invalid (EP)
    // =========================

    @Test
    public void parse_invalidStatus_throwsParseException() {
        assertParseFailure(parser, "s/Waiting",
                FindCommandParser.MESSAGE_INVALID_STATUS);
    }
}

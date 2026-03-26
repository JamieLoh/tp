package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_APPLICATION;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.OpenCommand;

/**
 * Tests for {@code OpenCommandParser}.
 */
public class OpenCommandParserTest {

    private OpenCommandParser parser = new OpenCommandParser();

    @Test
    public void parse_validArgsViewMode_returnsOpenCommand() {
        assertParseSuccess(parser, "1", new OpenCommand(INDEX_FIRST_APPLICATION, false));
    }

    @Test
    public void parse_validArgsEditModeTrue_returnsOpenCommand() {
        assertParseSuccess(parser, "1 m/true", new OpenCommand(INDEX_FIRST_APPLICATION, true));
    }

    @Test
    public void parse_validArgsEditModeFalse_returnsOpenCommand() {
        assertParseSuccess(parser, "1 m/false", new OpenCommand(INDEX_FIRST_APPLICATION, false));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, OpenCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidModifyValue_throwsParseException() {
        assertParseFailure(parser, "1 m/invalid",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, OpenCommand.MESSAGE_USAGE));
    }
}

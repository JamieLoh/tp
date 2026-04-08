package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODIFY;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.OpenCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new OpenCommand object.
 */
public class OpenCommandParser implements Parser<OpenCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the OpenCommand
     * and returns an OpenCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public OpenCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_MODIFY);
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_MODIFY);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, OpenCommand.MESSAGE_USAGE), pe);
        }

        boolean edit = false;
        if (argMultimap.getValue(PREFIX_MODIFY).isPresent()) {
            String modifyValue = argMultimap.getValue(PREFIX_MODIFY).get().trim().toLowerCase();
            if (modifyValue.equals("true")) {
                edit = true;
            } else if (!modifyValue.equals("false")) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, OpenCommand.MESSAGE_USAGE));
            }
        }

        return new OpenCommand(index, edit);
    }
}

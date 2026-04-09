package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEBSITE;

import java.util.List;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.application.ApplicationMatchesPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        // Trim the raw input and validate that it is not empty.
        String trimmedArgs = trimAndValidateArgs(args);

        // Tokenize the validated input into prefix-value mappings. e.g. PREFIX_NAME -> ["Google"]
        ArgumentMultimap argMultimap = tokenizeArguments(trimmedArgs);

        // Ensure that at least one searchable field is present.
        validateSearchFields(argMultimap);

        // Construct the predicate containing the user's search criteria.
        ApplicationMatchesPredicate predicate = buildPredicate(argMultimap);

        // Return a FindCommand that uses the constructed predicate.
        return new FindCommand(predicate);
    }

    /**
     * Trims the raw input and ensures that it is not empty.
     */
    private String trimAndValidateArgs(String args) throws ParseException {
        String trimmedArgs = args.trim();

        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        return trimmedArgs;
    }

    /**
     * Tokenizes the user input into prefixed arguments.
     */
    private ArgumentMultimap tokenizeArguments(String trimmedArgs) {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(" " + trimmedArgs,
                        PREFIX_NAME,
                        PREFIX_ROLE,
                        PREFIX_EMAIL,
                        PREFIX_WEBSITE,
                        PREFIX_ADDRESS,
                        PREFIX_DATE,
                        PREFIX_STATUS,
                        PREFIX_TAG
                );
        return argMultimap;
    }

    /**
     * Ensures that at least one searchable field is provided.
     */
    private void validateSearchFields(ArgumentMultimap argMultimap) throws ParseException {
        if (!argMultimap.getValue(PREFIX_NAME).isPresent()
                && !argMultimap.getValue(PREFIX_ROLE).isPresent()
                && !argMultimap.getValue(PREFIX_EMAIL).isPresent()
                && !argMultimap.getValue(PREFIX_WEBSITE).isPresent()
                && !argMultimap.getValue(PREFIX_ADDRESS).isPresent()
                && !argMultimap.getValue(PREFIX_DATE).isPresent()
                && !argMultimap.getValue(PREFIX_STATUS).isPresent()
                && argMultimap.getAllValues(PREFIX_TAG).isEmpty()) {

            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Builds the predicate used by the find command from the parsed arguments.
     */
    private ApplicationMatchesPredicate buildPredicate(ArgumentMultimap argMultimap) {
        String name = argMultimap.getValue(PREFIX_NAME).orElse(null);
        String role = argMultimap.getValue(PREFIX_ROLE).orElse(null);
        String email = argMultimap.getValue(PREFIX_EMAIL).orElse(null);
        String website = argMultimap.getValue(PREFIX_WEBSITE).orElse(null);
        String address = argMultimap.getValue(PREFIX_ADDRESS).orElse(null);
        String date = argMultimap.getValue(PREFIX_DATE).orElse(null);
        String status = argMultimap.getValue(PREFIX_STATUS).orElse(null);
        List<String> tags = argMultimap.getAllValues(PREFIX_TAG);

        ApplicationMatchesPredicate predicate =
                new ApplicationMatchesPredicate(name, role, email, website, address, date, status, tags);

        return predicate;
    }

}

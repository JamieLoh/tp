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
import seedu.address.model.application.Date;
import seedu.address.model.application.Status;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    public static final String MESSAGE_INVALID_DATE =
            "Date filter keyword must be in DD-MM-YYYY format and be a valid calendar date.";

    public static final String MESSAGE_INVALID_STATUS =
            "Status filter keyword must be one of: Offered, Pending, or Rejected.";

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        // check the input
        String trimmedArgs = args.trim();

        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        // parse prefixes and their values. e.g. PREFIX_NAME -> ["Google"]
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

        // get the value. e.g. name = "Google"
        String name = argMultimap.getValue(PREFIX_NAME).orElse(null);
        String role = argMultimap.getValue(PREFIX_ROLE).orElse(null);
        String email = argMultimap.getValue(PREFIX_EMAIL).orElse(null);
        String website = argMultimap.getValue(PREFIX_WEBSITE).orElse(null);
        String address = argMultimap.getValue(PREFIX_ADDRESS).orElse(null);
        String date = argMultimap.getValue(PREFIX_DATE).orElse(null);
        String status = argMultimap.getValue(PREFIX_STATUS).orElse(null);

        if (date != null && !date.isEmpty()) {
            if (!Date.isValidDate(date)) {
                throw new ParseException(MESSAGE_INVALID_DATE);
            }
        }

        if (status != null && !status.isEmpty()) {
            if (!Status.isValidStatus(status)) {
                throw new ParseException(MESSAGE_INVALID_STATUS);
            }
        }

        List<String> tags = argMultimap.getAllValues(PREFIX_TAG);

        ApplicationMatchesPredicate predicate = new ApplicationMatchesPredicate(
                        name, role, email, website, address, date, status, tags);

        return new FindCommand(predicate);
    }

}

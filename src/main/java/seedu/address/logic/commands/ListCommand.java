package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_APPLICATIONS;

import seedu.address.model.Model;

/**
 * Lists all applications in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all applications! "
            + "Now you have %d application(s) in your list!";

    public static final String MESSAGE_SUCCESS_EMPTY_LIST = "You have not added any applications yet!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        model.updateFilteredApplicationList(PREDICATE_SHOW_ALL_APPLICATIONS);

        // check if list is empty
        if (model.getFilteredApplicationList().isEmpty()) {
            return new CommandResult(MESSAGE_SUCCESS_EMPTY_LIST);
        }

        int size = model.getFilteredApplicationList().size();
        return new CommandResult(String.format(MESSAGE_SUCCESS, size));
    }
}

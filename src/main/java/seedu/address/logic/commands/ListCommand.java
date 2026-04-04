package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ARCHIVED_APPLICATIONS;
import static seedu.address.model.Model.PREDICATE_SHOW_UNARCHIVED_APPLICATIONS;

import seedu.address.model.Model;

/**
 * Lists all applications in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";
    public static final String ARCHIVED_ARGUMENT = "archived";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists applications.\n"
            + "Format: " + COMMAND_WORD + " [" + ARCHIVED_ARGUMENT + "]";

    public static final String MESSAGE_SUCCESS = "Listed all applications! "
            + "Now you have %d application(s) in your list! Archived applications are hidden.";
    public static final String MESSAGE_SUCCESS_ARCHIVED = "Listed all archived applications! "
            + "Now you have %d archived application(s) in your list!";

    public static final String MESSAGE_SUCCESS_EMPTY_LIST =
            "No active applications to display. Archived applications are hidden.";
    public static final String MESSAGE_SUCCESS_EMPTY_ARCHIVED_LIST = "You have no archived applications.";

    private final boolean showArchived;

    public ListCommand() {
        this(false);
    }

    public ListCommand(boolean showArchived) {
        this.showArchived = showArchived;
    }


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        model.updateFilteredApplicationList(showArchived
                ? PREDICATE_SHOW_ARCHIVED_APPLICATIONS
                : PREDICATE_SHOW_UNARCHIVED_APPLICATIONS);

        if (model.getFilteredApplicationList().isEmpty()) {
            return new CommandResult(showArchived ? MESSAGE_SUCCESS_EMPTY_ARCHIVED_LIST : MESSAGE_SUCCESS_EMPTY_LIST);
        }

        int size = model.getFilteredApplicationList().size();
        return new CommandResult(String.format(showArchived ? MESSAGE_SUCCESS_ARCHIVED : MESSAGE_SUCCESS, size));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ListCommand)) {
            return false;
        }

        ListCommand otherListCommand = (ListCommand) other;
        return showArchived == otherListCommand.showArchived;
    }
}

package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODIFY;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.application.Application;

/**
 * Opens the notes for an application identified by the index number in the application list.
 */
public class OpenCommand extends Command {
    public static final String COMMAND_WORD = "open";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Opens the notes written during the internship application process.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_MODIFY + "CHOICE_OF_EDIT] (must be true or false, defaults to false)";

    public static final String MESSAGE_SUCCESS_WITH_EDIT = "Editing application notes: %1$s";
    public static final String MESSAGE_SUCCESS_WITHOUT_EDIT = "Viewing application notes: %1$s";
    private final Index targetIndex;
    private final boolean edit;

    /**
     * Creates an OpenCommand to open notes for the application at the specified {@code targetIndex}.
     */
    public OpenCommand(Index targetIndex, boolean edit) {
        this.targetIndex = targetIndex;
        this.edit = edit;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Application> lastShownList = model.getFilteredApplicationList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_APPLICATION_DISPLAYED_INDEX);
        }

        Application targetApplication = lastShownList.get(targetIndex.getZeroBased());

        if (edit) {
            model.editApplicationNotes(targetApplication);
            return new CommandResult(String.format(MESSAGE_SUCCESS_WITH_EDIT,
                    Messages.format(targetApplication)), UiAction.EDIT_NOTE);
        } else {
            model.viewApplicationNotes(targetApplication);
            return new CommandResult(String.format(MESSAGE_SUCCESS_WITHOUT_EDIT,
                    Messages.format(targetApplication)), UiAction.SHOW_NOTE);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof OpenCommand)) {
            return false;
        }

        OpenCommand otherOpenCommand = (OpenCommand) other;
        return targetIndex.equals(otherOpenCommand.targetIndex)
                && edit == otherOpenCommand.edit;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .add("edit", edit)
                .toString();
    }
}

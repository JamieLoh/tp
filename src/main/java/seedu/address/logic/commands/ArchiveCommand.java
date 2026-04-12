package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.application.Application;

/**
 * Archives an application identified using its displayed index from the application list.
 */
public class ArchiveCommand extends Command {

    public static final String COMMAND_WORD = "archive";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Archives the application identified by the index number used in the displayed application list.\n"
            + "Parameters: INDEX (must be a positive integer and within bounds of the current list)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_ARCHIVE_APPLICATION_SUCCESS = "Archived Application: %1$s";
    public static final String MESSAGE_APPLICATION_ALREADY_ARCHIVED = "This application is already archived.";

    private final Index targetIndex;

    public ArchiveCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Application> lastShownList = model.getFilteredApplicationList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_APPLICATION_DISPLAYED_INDEX);
        }

        Application applicationToArchive = lastShownList.get(targetIndex.getZeroBased());

        if (applicationToArchive.isArchived()) {
            return new CommandResult(MESSAGE_APPLICATION_ALREADY_ARCHIVED);
        }

        Application archivedApplication = new Application(
                applicationToArchive.getCompanyName(),
                applicationToArchive.getRole(),
                applicationToArchive.getEmail(),
                applicationToArchive.getWebsite(),
                applicationToArchive.getAddress(),
                applicationToArchive.getDate(),
                applicationToArchive.getStatus(),
                applicationToArchive.getTags(),
                applicationToArchive.getNotes(),
                true
        );

        model.setApplication(applicationToArchive, archivedApplication);

        return new CommandResult(String.format(MESSAGE_ARCHIVE_APPLICATION_SUCCESS,
                Messages.format(archivedApplication)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ArchiveCommand)) {
            return false;
        }

        ArchiveCommand otherArchiveCommand = (ArchiveCommand) other;
        return targetIndex.equals(otherArchiveCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}

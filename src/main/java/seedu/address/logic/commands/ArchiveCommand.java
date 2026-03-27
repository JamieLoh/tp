package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.application.Application;
import seedu.address.model.tag.Tag;

/**
 * Archives an application identified using its displayed index from the application list.
 */
public class ArchiveCommand extends Command {

    public static final String COMMAND_WORD = "archive";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Archives the application identified by the index number used in the displayed application list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
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

        if (applicationToArchive.getTags().contains(Model.ARCHIVED_TAG)) {
            return new CommandResult(MESSAGE_APPLICATION_ALREADY_ARCHIVED);
        }

        Set<Tag> archivedTags = new HashSet<>(applicationToArchive.getTags());
        archivedTags.add(Model.ARCHIVED_TAG);

        Application archivedApplication = new Application(
                applicationToArchive.getCompanyName(),
                applicationToArchive.getRole(),
                applicationToArchive.getEmail(),
                applicationToArchive.getWebsite(),
                applicationToArchive.getAddress(),
                applicationToArchive.getDate(),
                applicationToArchive.getStatus(),
                archivedTags,
                applicationToArchive.getNotes()
        );

        model.setApplication(applicationToArchive, archivedApplication);
        model.updateFilteredApplicationList(Model.PREDICATE_SHOW_UNARCHIVED_APPLICATIONS);

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

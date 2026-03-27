package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalApplications.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_APPLICATION;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_APPLICATION;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.application.Application;
import seedu.address.model.tag.Tag;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code UnarchiveCommand}.
 */
public class UnarchiveCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexFilteredList_success() {
        model.updateFilteredApplicationList(Model.PREDICATE_SHOW_ALL_APPLICATIONS);
        Application applicationToArchive = model.getFilteredApplicationList()
                .get(INDEX_FIRST_APPLICATION.getZeroBased());
        Application archivedApplication = getArchivedVersion(applicationToArchive);
        model.setApplication(applicationToArchive, archivedApplication);

        model.updateFilteredApplicationList(application -> application.getTags().contains(Model.ARCHIVED_TAG));

        UnarchiveCommand unarchiveCommand = new UnarchiveCommand(INDEX_FIRST_APPLICATION);

        Application expectedUnarchivedApplication = getUnarchivedVersion(archivedApplication);
        String expectedMessage = String.format(UnarchiveCommand.MESSAGE_UNARCHIVE_APPLICATION_SUCCESS,
                Messages.format(expectedUnarchivedApplication));

        Model expectedModel = new ModelManager(model.getAddressBook(), model.getUserPrefs());
        expectedModel.updateFilteredApplicationList(expectedApplication -> expectedApplication.getTags()
                .contains(Model.ARCHIVED_TAG));
        Application expectedApplicationToUnarchive = expectedModel.getFilteredApplicationList()
                .get(INDEX_FIRST_APPLICATION.getZeroBased());
        expectedModel.setApplication(expectedApplicationToUnarchive, expectedUnarchivedApplication);
        expectedModel.updateFilteredApplicationList(Model.PREDICATE_SHOW_UNARCHIVED_APPLICATIONS);

        assertCommandSuccess(unarchiveCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        model.updateFilteredApplicationList(Model.PREDICATE_SHOW_ALL_APPLICATIONS);
        Application applicationToArchive = model.getFilteredApplicationList()
                .get(INDEX_FIRST_APPLICATION.getZeroBased());
        Application archivedApplication = getArchivedVersion(applicationToArchive);
        model.setApplication(applicationToArchive, archivedApplication);

        model.updateFilteredApplicationList(application -> application
                .getTags().contains(Model.ARCHIVED_TAG));

        Index outOfBoundIndex = INDEX_SECOND_APPLICATION;
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getApplicationList().size());

        UnarchiveCommand unarchiveCommand = new UnarchiveCommand(outOfBoundIndex);

        assertCommandFailure(unarchiveCommand, model, Messages.MESSAGE_INVALID_APPLICATION_DISPLAYED_INDEX);
    }

    @Test
    public void execute_notArchived_returnsNotArchivedMessage() {
        model.updateFilteredApplicationList(Model.PREDICATE_SHOW_ALL_APPLICATIONS);
        UnarchiveCommand unarchiveCommand = new UnarchiveCommand(INDEX_FIRST_APPLICATION);

        Model expectedModel = new ModelManager(model.getAddressBook(), model.getUserPrefs());
        expectedModel.updateFilteredApplicationList(Model.PREDICATE_SHOW_ALL_APPLICATIONS);

        assertCommandSuccess(unarchiveCommand, model, UnarchiveCommand.MESSAGE_APPLICATION_NOT_ARCHIVED, expectedModel);
    }

    @Test
    public void equals() {
        UnarchiveCommand unarchiveFirstCommand = new UnarchiveCommand(INDEX_FIRST_APPLICATION);
        UnarchiveCommand unarchiveSecondCommand = new UnarchiveCommand(INDEX_SECOND_APPLICATION);

        assertTrue(unarchiveFirstCommand.equals(unarchiveFirstCommand));

        UnarchiveCommand unarchiveFirstCommandCopy = new UnarchiveCommand(INDEX_FIRST_APPLICATION);
        assertTrue(unarchiveFirstCommand.equals(unarchiveFirstCommandCopy));

        assertFalse(unarchiveFirstCommand.equals(1));
        assertFalse(unarchiveFirstCommand.equals(null));
        assertFalse(unarchiveFirstCommand.equals(unarchiveSecondCommand));
    }

    private Application getArchivedVersion(Application sourceApplication) {
        Set<Tag> tags = new HashSet<>(sourceApplication.getTags());
        tags.add(Model.ARCHIVED_TAG);

        return new Application(
                sourceApplication.getCompanyName(),
                sourceApplication.getRole(),
                sourceApplication.getEmail(),
                sourceApplication.getWebsite(),
                sourceApplication.getAddress(),
                sourceApplication.getDate(),
                sourceApplication.getStatus(),
                tags,
                sourceApplication.getNotes()
        );
    }

    private Application getUnarchivedVersion(Application sourceApplication) {
        Set<Tag> tags = new HashSet<>(sourceApplication.getTags());
        tags.remove(Model.ARCHIVED_TAG);

        return new Application(
                sourceApplication.getCompanyName(),
                sourceApplication.getRole(),
                sourceApplication.getEmail(),
                sourceApplication.getWebsite(),
                sourceApplication.getAddress(),
                sourceApplication.getDate(),
                sourceApplication.getStatus(),
                tags,
                sourceApplication.getNotes()
        );
    }
}

package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showApplicationAtIndex;
import static seedu.address.testutil.TypicalApplications.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_APPLICATION;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_APPLICATION;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.application.Application;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code ArchiveCommand}.
 */
public class ArchiveCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Application applicationToArchive = model.getFilteredApplicationList()
                .get(INDEX_FIRST_APPLICATION.getZeroBased());
        ArchiveCommand archiveCommand = new ArchiveCommand(INDEX_FIRST_APPLICATION);

        Application expectedArchivedApplication = getArchivedVersion(applicationToArchive);
        String expectedMessage = String.format(ArchiveCommand.MESSAGE_ARCHIVE_APPLICATION_SUCCESS,
                Messages.format(expectedArchivedApplication));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setApplication(applicationToArchive, expectedArchivedApplication);

        assertCommandSuccess(archiveCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showApplicationAtIndex(model, INDEX_FIRST_APPLICATION);

        Application applicationToArchive = model.getFilteredApplicationList()
                .get(INDEX_FIRST_APPLICATION.getZeroBased());
        ArchiveCommand archiveCommand = new ArchiveCommand(INDEX_FIRST_APPLICATION);

        Application expectedArchivedApplication = getArchivedVersion(applicationToArchive);
        String expectedMessage = String.format(ArchiveCommand.MESSAGE_ARCHIVE_APPLICATION_SUCCESS,
                Messages.format(expectedArchivedApplication));

        Model expectedModel = new ModelManager(model.getAddressBook(), model.getUserPrefs());
        showApplicationAtIndex(expectedModel, INDEX_FIRST_APPLICATION);

        Application applicationToArchiveInExpectedModel = expectedModel.getFilteredApplicationList()
                .get(INDEX_FIRST_APPLICATION.getZeroBased());
        expectedModel.setApplication(applicationToArchiveInExpectedModel, expectedArchivedApplication);

        assertCommandSuccess(archiveCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredApplicationList().size() + 1);
        ArchiveCommand archiveCommand = new ArchiveCommand(outOfBoundIndex);

        assertCommandFailure(archiveCommand, model, Messages.MESSAGE_INVALID_APPLICATION_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showApplicationAtIndex(model, INDEX_FIRST_APPLICATION);

        Index outOfBoundIndex = INDEX_SECOND_APPLICATION;
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getApplicationList().size());

        ArchiveCommand archiveCommand = new ArchiveCommand(outOfBoundIndex);

        assertCommandFailure(archiveCommand, model, Messages.MESSAGE_INVALID_APPLICATION_DISPLAYED_INDEX);
    }

    @Test
    public void execute_alreadyArchived_returnsAlreadyArchivedMessage() {
        model.updateFilteredApplicationList(Model.PREDICATE_SHOW_ALL_APPLICATIONS);

        Application applicationToArchive = model.getFilteredApplicationList()
                .get(INDEX_FIRST_APPLICATION.getZeroBased());

        Model expectedModel = new ModelManager(model.getAddressBook(), model.getUserPrefs());
        expectedModel.updateFilteredApplicationList(Model.PREDICATE_SHOW_ALL_APPLICATIONS);
        Application expectedApplicationToArchive = expectedModel.getFilteredApplicationList()
                .get(INDEX_FIRST_APPLICATION.getZeroBased());

        Application archivedApplication = getArchivedVersion(applicationToArchive);
        Application expectedArchivedApplication = getArchivedVersion(expectedApplicationToArchive);

        model.setApplication(applicationToArchive, archivedApplication);
        expectedModel.setApplication(expectedApplicationToArchive, expectedArchivedApplication);

        ArchiveCommand archiveCommand = new ArchiveCommand(INDEX_FIRST_APPLICATION);

        assertCommandSuccess(archiveCommand, model,
                ArchiveCommand.MESSAGE_APPLICATION_ALREADY_ARCHIVED, expectedModel);
    }

    @Test
    public void equals() {
        ArchiveCommand archiveFirstCommand = new ArchiveCommand(INDEX_FIRST_APPLICATION);
        ArchiveCommand archiveSecondCommand = new ArchiveCommand(INDEX_SECOND_APPLICATION);

        assertTrue(archiveFirstCommand.equals(archiveFirstCommand));

        ArchiveCommand archiveFirstCommandCopy = new ArchiveCommand(INDEX_FIRST_APPLICATION);
        assertTrue(archiveFirstCommand.equals(archiveFirstCommandCopy));

        assertFalse(archiveFirstCommand.equals(1));
        assertFalse(archiveFirstCommand.equals(null));
        assertFalse(archiveFirstCommand.equals(archiveSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        ArchiveCommand archiveCommand = new ArchiveCommand(targetIndex);
        String expected = ArchiveCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, archiveCommand.toString());
    }

    private Application getArchivedVersion(Application sourceApplication) {
        return new Application(
                sourceApplication.getCompanyName(),
                sourceApplication.getRole(),
                sourceApplication.getEmail(),
                sourceApplication.getWebsite(),
                sourceApplication.getAddress(),
                sourceApplication.getDate(),
                sourceApplication.getStatus(),
                sourceApplication.getTags(),
                sourceApplication.getNotes(),
                true
        );
    }

    /**
     * Updates {@code model}'s filtered list to show no application.
     */
    private void showNoApplication(Model model) {
        model.updateFilteredApplicationList(p -> false);
        assertTrue(model.getFilteredApplicationList().isEmpty());
    }
}

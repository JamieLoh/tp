package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
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
 * {@code OpenCommand}.
 */
public class OpenCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexViewMode_success() throws Exception {
        Application applicationToOpen = model.getFilteredApplicationList()
                .get(INDEX_FIRST_APPLICATION.getZeroBased());
        OpenCommand openCommand = new OpenCommand(INDEX_FIRST_APPLICATION, false);

        CommandResult result = openCommand.execute(model);

        String expectedMessage = String.format(OpenCommand.MESSAGE_SUCCESS_WITHOUT_EDIT,
                Messages.format(applicationToOpen));
        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertTrue(result.isShowNote());
        assertFalse(result.isEditNote());
        assertEquals(applicationToOpen, model.getSelectedNotesApplication());
    }

    @Test
    public void execute_validIndexEditMode_success() throws Exception {
        Application applicationToOpen = model.getFilteredApplicationList()
                .get(INDEX_FIRST_APPLICATION.getZeroBased());
        OpenCommand openCommand = new OpenCommand(INDEX_FIRST_APPLICATION, true);

        CommandResult result = openCommand.execute(model);

        String expectedMessage = String.format(OpenCommand.MESSAGE_SUCCESS_WITH_EDIT,
                Messages.format(applicationToOpen));
        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertTrue(result.isEditNote());
        assertFalse(result.isShowNote());
        assertEquals(applicationToOpen, model.getSelectedNotesApplication());
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredApplicationList().size() + 1);
        OpenCommand openCommand = new OpenCommand(outOfBoundIndex, false);

        assertCommandFailure(openCommand, model, Messages.MESSAGE_INVALID_APPLICATION_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showApplicationAtIndex(model, INDEX_FIRST_APPLICATION);

        Index outOfBoundIndex = INDEX_SECOND_APPLICATION;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getApplicationList().size());

        OpenCommand openCommand = new OpenCommand(outOfBoundIndex, false);

        assertCommandFailure(openCommand, model, Messages.MESSAGE_INVALID_APPLICATION_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        OpenCommand openFirstCommand = new OpenCommand(INDEX_FIRST_APPLICATION, false);
        OpenCommand openSecondCommand = new OpenCommand(INDEX_SECOND_APPLICATION, false);
        OpenCommand openFirstEditCommand = new OpenCommand(INDEX_FIRST_APPLICATION, true);

        // same object -> returns true
        assertTrue(openFirstCommand.equals(openFirstCommand));

        // same values -> returns true
        OpenCommand openFirstCommandCopy = new OpenCommand(INDEX_FIRST_APPLICATION, false);
        assertTrue(openFirstCommand.equals(openFirstCommandCopy));

        // different types -> returns false
        assertFalse(openFirstCommand.equals(1));

        // null -> returns false
        assertFalse(openFirstCommand.equals(null));

        // different index -> returns false
        assertFalse(openFirstCommand.equals(openSecondCommand));

        // different edit flag -> returns false
        assertFalse(openFirstCommand.equals(openFirstEditCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        OpenCommand openCommand = new OpenCommand(targetIndex, false);
        String expected = OpenCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + ", edit=false}";
        assertEquals(expected, openCommand.toString());
    }
}

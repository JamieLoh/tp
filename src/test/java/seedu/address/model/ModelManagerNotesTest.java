package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static seedu.address.testutil.TypicalApplications.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.application.Application;
import seedu.address.testutil.ApplicationBuilder;

/**
 * Tests for notes-related methods in {@code ModelManager}.
 */
public class ModelManagerNotesTest {

    private ModelManager modelManager;

    @BeforeEach
    public void setUp() {
        modelManager = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void getSelectedNotesApplication_noSelection_returnsNull() {
        assertNull(modelManager.getSelectedNotesApplication());
    }

    @Test
    public void viewApplicationNotes_setsSelectedApplication() {
        Application target = modelManager.getFilteredApplicationList().get(0);
        modelManager.viewApplicationNotes(target);
        assertEquals(target, modelManager.getSelectedNotesApplication());
    }

    @Test
    public void editApplicationNotes_setsSelectedApplication() {
        Application target = modelManager.getFilteredApplicationList().get(0);
        modelManager.editApplicationNotes(target);
        assertEquals(target, modelManager.getSelectedNotesApplication());
    }

    @Test
    public void saveApplicationNotes_updatesApplicationNotes() {
        Application target = modelManager.getFilteredApplicationList().get(0);
        modelManager.editApplicationNotes(target);

        String newNotes = "Interview scheduled for next week";
        modelManager.saveApplicationNotes(newNotes);

        Application updated = modelManager.getSelectedNotesApplication();
        assertEquals(newNotes, updated.getNotes());
    }

    @Test
    public void saveApplicationNotes_applicationInListIsUpdated() {
        Application target = modelManager.getFilteredApplicationList().get(0);
        modelManager.editApplicationNotes(target);

        String newNotes = "Received offer";
        modelManager.saveApplicationNotes(newNotes);

        // The application in the filtered list should also reflect the updated notes
        Application fromList = modelManager.getFilteredApplicationList().get(0);
        assertEquals(newNotes, fromList.getNotes());
    }

    @Test
    public void saveApplicationNotes_preservesOtherFields() {
        Application target = modelManager.getFilteredApplicationList().get(0);
        modelManager.editApplicationNotes(target);

        String newNotes = "Some notes";
        modelManager.saveApplicationNotes(newNotes);

        Application updated = modelManager.getSelectedNotesApplication();
        assertEquals(target.getCompanyName(), updated.getCompanyName());
        assertEquals(target.getRole(), updated.getRole());
        assertEquals(target.getEmail(), updated.getEmail());
        assertEquals(target.getWebsite(), updated.getWebsite());
        assertEquals(target.getAddress(), updated.getAddress());
        assertEquals(target.getDate(), updated.getDate());
        assertEquals(target.getStatus(), updated.getStatus());
        assertEquals(target.getTags(), updated.getTags());
    }

    @Test
    public void applicationBuilder_withNotes_buildsCorrectly() {
        Application app = new ApplicationBuilder().withNotes("test notes").build();
        assertEquals("test notes", app.getNotes());
    }

    @Test
    public void applicationBuilder_defaultNotes_isEmpty() {
        Application app = new ApplicationBuilder().build();
        assertEquals("", app.getNotes());
    }
}

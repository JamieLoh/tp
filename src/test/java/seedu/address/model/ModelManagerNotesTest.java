package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
        assertEquals(target.isArchived(), updated.isArchived());
    }

    @Test
    public void saveApplicationNotes_applicationEditedAfterSelection() {
        Application target = modelManager.getFilteredApplicationList().get(0);
        modelManager.editApplicationNotes(target);

        Application editedApplication = new ApplicationBuilder(target).withStatus("Offered").build();
        modelManager.setApplication(target, editedApplication);

        String newNotes = "Offer received after status update";
        modelManager.saveApplicationNotes(newNotes);

        Application updated = modelManager.getSelectedNotesApplication();
        assertEquals(newNotes, updated.getNotes());
        assertEquals(editedApplication.getStatus(), updated.getStatus());
    }

    @Test
    public void saveApplicationNotes_applicationArchivedAfterSelection() {
        Application target = modelManager.getFilteredApplicationList().get(0);
        modelManager.editApplicationNotes(target);

        Application archivedApplication = new ApplicationBuilder(target).withArchived(true).build();
        modelManager.setApplication(target, archivedApplication);

        String newNotes = "Archived with final notes";
        modelManager.saveApplicationNotes(newNotes);

        Application updated = modelManager.getSelectedNotesApplication();
        assertEquals(newNotes, updated.getNotes());
        assertEquals(true, updated.isArchived());
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

    @Test
    public void application_nullNotes_defaultsToEmpty() {
        Application app = new Application(
                new seedu.address.model.application.CompanyName("Google"),
                new seedu.address.model.application.Role("Engineer"),
                null,
                new seedu.address.model.application.Website("https://google.com"),
                new seedu.address.model.application.Address("123 Street"),
                new seedu.address.model.application.Date("01-01-2026"),
                new seedu.address.model.application.Status("Pending"),
                new java.util.HashSet<>(),
                null,
                false
        );
        assertEquals("", app.getNotes());
    }

    @Test
    public void application_equalsWithDifferentNotes_returnsFalse() {
        Application app1 = new ApplicationBuilder().withNotes("notes A").build();
        Application app2 = new ApplicationBuilder().withNotes("notes B").build();
        assertFalse(app1.equals(app2));
    }

    @Test
    public void application_equalsWithSameNotes_returnsTrue() {
        Application app1 = new ApplicationBuilder().withNotes("same").build();
        Application app2 = new ApplicationBuilder().withNotes("same").build();
        assertEquals(app1, app2);
    }
}

package seedu.address.model.application;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class StatusTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Status(null));
    }

    @Test
    public void constructor_invalidStatus_throwsIllegalArgumentException() {
        String invalidStatus = "Applied";
        assertThrows(IllegalArgumentException.class, () -> new Status(invalidStatus));
    }

    @Test
    public void isValidStatus() {
        // null status
        assertThrows(NullPointerException.class, () -> Status.isValidStatus(null));

        // blank statuses
        assertFalse(Status.isValidStatus("")); // empty string
        assertFalse(Status.isValidStatus(" ")); // spaces only

        // invalid statuses
        assertFalse(Status.isValidStatus(""));
        assertFalse(Status.isValidStatus("Applied"));
        assertFalse(Status.isValidStatus("accepted"));
        assertFalse(Status.isValidStatus("unknown"));

        // valid statuses
        assertTrue(Status.isValidStatus("Offered"));
        assertTrue(Status.isValidStatus("Pending"));
        assertTrue(Status.isValidStatus("Rejected"));

        // valid statuses - case insensitive
        assertTrue(Status.isValidStatus("offered"));
        assertTrue(Status.isValidStatus("PENDING"));
        assertTrue(Status.isValidStatus("rejected"));
    }

    @Test
    public void equals() {
        Status status = new Status("Pending");

        // same values -> returns true
        assertTrue(status.equals(new Status("Pending")));

        // same object -> returns true
        assertTrue(status.equals(status));

        // null -> returns false
        assertFalse(status.equals(null));

        // different types -> returns false
        assertFalse(status.equals(5.0f));

        // different values -> returns false
        assertFalse(status.equals(new Status("Offered")));
        assertFalse(status.equals(new Status("Rejected")));
    }
}

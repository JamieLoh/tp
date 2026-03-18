package seedu.address.model.application;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class RoleTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Role(null));
    }

    @Test
    public void constructor_invalidRole_throwsIllegalArgumentException() {
        String invalidRole = "";
        assertThrows(IllegalArgumentException.class, () -> new Role(invalidRole));
    }

    @Test
    public void isValidRole() {
        // null role
        assertThrows(NullPointerException.class, () -> Role.isValidRole(null));

        // invalid roles
        assertFalse(Role.isValidRole("")); // empty string
        assertFalse(Role.isValidRole(" ")); // spaces only
        assertFalse(Role.isValidRole(" Engineer")); // leading space
        assertFalse(Role.isValidRole("@Engineer")); // special characters at the beginning
        assertFalse(Role.isValidRole("Dev!Ops")); // special characters inside
        assertFalse(Role.isValidRole("Engineer#")); // special characters at the end

        // valid roles
        assertTrue(Role.isValidRole("E")); // single character
        assertTrue(Role.isValidRole("Engineer")); // alphabets
        assertTrue(Role.isValidRole("Software Engineer")); // spaces inside
        assertTrue(Role.isValidRole("SoftwareEngineer1")); // alphanumeric
        assertTrue(Role.isValidRole("Backend Developer2")); // alphanumeric with space
    }

    @Test
    public void equals() {
        Role role = new Role("Algorithm Engineer");

        // same values -> returns true
        assertTrue(role.equals(new Role("Algorithm Engineer")));

        // same object -> returns true
        assertTrue(role.equals(role));

        // null -> returns false
        assertFalse(role.equals(null));

        // different types -> returns false
        assertFalse(role.equals(5.0f));

        // different values -> returns false
        assertFalse(role.equals(new Role("Backend Developer")));
    }
}

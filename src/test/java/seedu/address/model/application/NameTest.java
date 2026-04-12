package seedu.address.model.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class NameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new CompanyName(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new CompanyName(invalidName));
    }

    @Test
    public void isValidName() {
        // null name
        assertThrows(NullPointerException.class, () -> CompanyName.isValidCompanyName(null));

        // invalid name
        assertFalse(CompanyName.isValidCompanyName("")); // empty string
        assertFalse(CompanyName.isValidCompanyName(" ")); // spaces only
        assertFalse(CompanyName.isValidCompanyName("  Google")); // leading space

        // valid name
        assertTrue(CompanyName.isValidCompanyName("^")); // only non-alphanumeric characters
        assertTrue(CompanyName.isValidCompanyName("Google")); // alphabets only
        assertTrue(CompanyName.isValidCompanyName("Google*")); // contains non-alphanumeric characters
        assertTrue(CompanyName.isValidCompanyName("google inc")); // alphabets with space
        assertTrue(CompanyName.isValidCompanyName("Google     Inc")); // alphabets with multiple spaces
        assertTrue(CompanyName.isValidCompanyName("12345")); // numbers only
        assertTrue(CompanyName.isValidCompanyName("Google123")); // alphanumeric characters
        assertTrue(CompanyName.isValidCompanyName("Google Inc")); // with capital letters
        assertTrue(CompanyName.isValidCompanyName(
                "Google Pte Ltd aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")); // long names
    }

    @Test
    public void constructor_normalization() {
        CompanyName name = new CompanyName("  Google     Inc  ");
        assertEquals("Google Inc", name.fullCompanyName);
    }

    @Test
    public void isSameCompanyName() {
        CompanyName name = new CompanyName("Google Inc");

        // same object
        assertTrue(name.isSameCompanyName(name));

        // null
        assertFalse(name.isSameCompanyName(null));

        // same name, different case
        assertTrue(name.isSameCompanyName(new CompanyName("google inc")));

        // same name, multiple spaces
        assertTrue(name.isSameCompanyName(new CompanyName("Google     Inc")));

        // same name, leading/trailing spaces
        assertTrue(name.isSameCompanyName(new CompanyName("  Google Inc  ")));

        // same name, leading/trailing and multiple spaces
        assertTrue(name.isSameCompanyName(new CompanyName("  Google     Inc  ")));

        // different name
        assertFalse(name.isSameCompanyName(new CompanyName("Amazon")));
    }

    @Test
    public void equals() {
        CompanyName name = new CompanyName("Google Inc");

        // same values -> returns true
        assertTrue(name.equals(new CompanyName("Google Inc")));

        // normalization makes them equal
        assertTrue(name.equals(new CompanyName("Google     Inc")));

        // same object -> returns true
        assertTrue(name.equals(name));

        // null -> returns false
        assertFalse(name.equals(null));

        // different types -> returns false
        assertFalse(name.equals(5.0f));

        // different values -> returns false
        assertFalse(name.equals(new CompanyName("Apple Inc")));
    }

    @Test
    public void equalsVsIsSameCompanyName_caseDifference() {
        CompanyName name1 = new CompanyName("Google Inc");
        CompanyName name2 = new CompanyName("google inc");

        assertTrue(name1.isSameCompanyName(name2)); // logical same
        assertFalse(name1.equals(name2)); // strict different
    }
}

package seedu.address.model.application;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class DateTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Date(null));
    }

    @Test
    public void constructor_invalidDate_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Date("2026-03-01"));
    }

    @Test
    public void equals() {
        Date date = new Date("01-03-2026");

        // same values -> returns true
        assertTrue(date.equals(new Date("01-03-2026")));

        // same object -> returns true
        assertTrue(date.equals(date));

        // null -> returns false
        assertFalse(date.equals(null));

        // different types -> returns false
        assertFalse(date.equals("01-03-2026"));

        // different values -> returns false
        assertFalse(date.equals(new Date("02-03-2026")));
    }

    @Test
    public void isValidDate() {
        // null date
        assertFalse(Date.isValidDate(null));

        // blank date
        assertFalse(Date.isValidDate(""));
        assertFalse(Date.isValidDate("   "));

        // wrong format: YYYY-MM-DD instead of DD-MM-YYYY
        assertFalse(Date.isValidDate("2026-03-01"));

        // wrong separator
        assertFalse(Date.isValidDate("01/03/2026"));
        assertFalse(Date.isValidDate("01 03 2026"));

        // wrong digit counts
        assertFalse(Date.isValidDate("1-3-2026"));
        assertFalse(Date.isValidDate("01-3-2026"));
        assertFalse(Date.isValidDate("1-03-2026"));
        assertFalse(Date.isValidDate("01-03-26"));

        // non-numeric characters
        assertFalse(Date.isValidDate("ab-cd-efgh"));

        // valid dates in DD-MM-YYYY format
        assertTrue(Date.isValidDate("01-03-2026"));
        assertTrue(Date.isValidDate("31-12-2025"));
        assertTrue(Date.isValidDate("15-06-2024"));
    }

    @Test
    public void isValidDate_invalidCalendarDates() {
        // invalid days for given months
        assertFalse(Date.isValidDate("31-04-2026")); // April has 30 days
        assertFalse(Date.isValidDate("31-06-2026"));
        assertFalse(Date.isValidDate("31-09-2026"));
        assertFalse(Date.isValidDate("31-11-2026"));

        // February cases
        assertFalse(Date.isValidDate("30-02-2026"));
        assertFalse(Date.isValidDate("31-02-2026"));
    }

    @Test
    public void isValidDate_leapYear() {
        // valid leap year
        assertTrue(Date.isValidDate("29-02-2024")); // 2024 is leap year

        // invalid non-leap year
        assertFalse(Date.isValidDate("29-02-2023"));

        // century rules
        assertFalse(Date.isValidDate("29-02-1900")); // not leap
        assertTrue(Date.isValidDate("29-02-2000")); // leap
    }

    @Test
    public void isValidDate_invalidMonth() {
        // month out of range
        assertFalse(Date.isValidDate("01-00-2026"));
        assertFalse(Date.isValidDate("01-13-2026"));
    }

    @Test
    public void isValidDate_invalidDay() {
        // day out of range
        assertFalse(Date.isValidDate("00-01-2026"));
        assertFalse(Date.isValidDate("32-01-2026"));
    }
}

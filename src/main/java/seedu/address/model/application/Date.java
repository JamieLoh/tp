package seedu.address.model.application;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents an Application's date in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class Date {

    public static final String MESSAGE_CONSTRAINTS =
            "Date should be a valid calendar date in DD-MM-YYYY format, and it should not be blank";

    public static final String VALIDATION_REGEX = "\\d{2}-\\d{2}-\\d{4}";

    public final String value;

    /**
     * Constructs a {@code Date}.
     *
     * @param date A valid date.
     */
    public Date(String date) {
        requireNonNull(date);
        checkArgument(isValidDate(date), MESSAGE_CONSTRAINTS);
        value = date;
    }

    /**
     * Returns true if a given string is a valid date.
     */
    public static boolean isValidDate(String test) {
        if (test == null) {
            return false;
        }
        // Check if the date matches the format DD-MM-YYYY
        if (!test.matches(VALIDATION_REGEX)) {
            return false;
        }
        // Check if the date is a valid calendar date
        return isValidCalendarDate(test);
    }

    /**
     * Returns true if the given date string represents a valid calendar date.
     * Assumes the input is in the format DD-MM-YYYY and has already been validated against the regex.
     */
    private static boolean isValidCalendarDate(String test) {
        String[] parts = test.split("-");
        int day = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);
        int year = Integer.parseInt(parts[2]);
        if (month < 1 || month > 12) {
            return false;
        }
        int[] daysInMonth = {31, (isLeapYear(year) ? 29 : 28), 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        return day >= 1 && day <= daysInMonth[month - 1];
    }

    /**
     * Returns true if the given year is a leap year.
     */
    private static boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Date)) {
            return false;
        }

        Date otherDate = (Date) other;
        return value.equals(otherDate.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}

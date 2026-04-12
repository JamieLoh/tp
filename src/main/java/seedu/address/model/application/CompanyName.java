package seedu.address.model.application;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Company's Name in the application.
 * Guarantees: immutable; is valid as declared in {@link #isValidCompanyName(String)}
 */
public class CompanyName {

    public static final String MESSAGE_CONSTRAINTS =
            "Company name may only contain printable English keyboard characters "
                    + "(letters, digits, symbols), and must not be blank or start with a space";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "^[!-~][\\x20-\\x7E]*$";

    public final String fullCompanyName;

    /**
     * Constructs a {@code companyName}.
     *
     * @param companyName A valid companyName.
     */
    public CompanyName(String companyName) {
        requireNonNull(companyName);
        String normalizedCompanyName = normalize(companyName);
        checkArgument(isValidCompanyName(normalizedCompanyName), MESSAGE_CONSTRAINTS);
        fullCompanyName = normalizedCompanyName;
    }

    /**
     * Normalizes the input string by trimming leading/trailing spaces
     * and collapsing multiple spaces into one.
     */
    private String normalize(String input) {
        return input.trim().replaceAll("\\s+", " ");
    }

    /**
     * Returns true if both company names are the same, ignoring case.
     * Note: Leading/trailing and multiple spaces are already normalized during construction.
     */
    public boolean isSameCompanyName(CompanyName other) {
        return other != null
                && this.fullCompanyName
                .equalsIgnoreCase(other.fullCompanyName);
    }

    /**
     * Returns true if a given string is a valid companyName.
     */
    public static boolean isValidCompanyName(String test) {
        requireNonNull(test);
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return fullCompanyName;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CompanyName)) {
            return false;
        }

        CompanyName otherName = (CompanyName) other;
        return fullCompanyName.equals(otherName.fullCompanyName);
    }

    @Override
    public int hashCode() {
        return fullCompanyName.hashCode();
    }

}

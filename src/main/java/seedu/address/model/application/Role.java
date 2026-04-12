package seedu.address.model.application;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents role applied for in the job application.
 * Guarantees: immutable; is valid as declared in {@link #isValidRole(String)}
 */
public class Role {

    public static final String MESSAGE_CONSTRAINTS =
            "Role may only contain printable English keyboard characters "
                    + "(letters, digits, symbols), and must not be blank or start with a space";
    public static final String VALIDATION_REGEX = "^[!-~][\\x20-\\x7E]*$";
    public final String value;

    /**
     * Constructs a {@code Role}.
     *
     * @param role A valid role.
     */
    public Role(String role) {
        requireNonNull(role);
        String normalizedRole = normalize(role);
        checkArgument(isValidRole(normalizedRole), MESSAGE_CONSTRAINTS);
        value = normalizedRole;
    }

    /**
     * Normalizes the input string by trimming leading/trailing spaces
     * and collapsing multiple spaces into one.
     */
    private String normalize(String input) {
        return input.trim().replaceAll("\\s+", " ");
    }

    /**
     * Returns true if both roles are the same, ignoring case.
     * Note: Leading/trailing and multiple spaces are already normalized during construction.
     */
    public boolean isSameRole(Role other) {
        return other != null
                && this.value
                .equalsIgnoreCase(other.value);
    }

    /**
     * Returns true if a given string is a valid role.
     */
    public static boolean isValidRole(String test) {
        requireNonNull(test);
        return test.matches(VALIDATION_REGEX);
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
        if (!(other instanceof Role)) {
            return false;
        }

        Role otherRole = (Role) other;
        return value.equals(otherRole.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}

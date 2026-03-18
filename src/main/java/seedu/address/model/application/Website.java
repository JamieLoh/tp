package seedu.address.model.application;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents an Application's website in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidWebsite(String)}
 */
public class Website {

    public static final String MESSAGE_CONSTRAINTS =
            "Websites should only contain alphanumeric characters and it should not be blank";

    public final String websiteName;

    /**
     * Constructs a {@code Name}.
     *
     * @param website A valid name.
     */
    public Website(String website) {
        requireNonNull(website);
        checkArgument(isValidWebsite(website), MESSAGE_CONSTRAINTS);
        websiteName = website;
    }

    /**
     * Returns true if a given string is a valid website.
     */
    public static boolean isValidWebsite(String test) {
        return test != null && !test.trim().isEmpty();
    }


    @Override
    public String toString() {
        return websiteName;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Website)) {
            return false;
        }

        Website otherWebsiteName = (Website) other;
        return websiteName.equals(otherWebsiteName.websiteName);
    }

    @Override
    public int hashCode() {
        return websiteName.hashCode();
    }

}

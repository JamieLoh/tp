package seedu.address.model.application;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Represents an Application's website in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidWebsite(String)}
 */
public class Website {

    public static final String MESSAGE_CONSTRAINTS =
            "Websites should be valid domain names or URLs, such as "
                    + "example.com or https://example.com";

    public final String websiteName;

    /**
     * Constructs a {@code Website}.
     *
     * @param website A valid website.
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
        requireNonNull(test);

        if (test.isBlank()) {
            return false;
        }

        String url = test.trim();

        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            url = "https://" + url;
        }

        try {
            URI uri = new URI(url);
            String host = uri.getHost();
            return host != null && host.contains(".");
        } catch (URISyntaxException e) {
            return false;
        }
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

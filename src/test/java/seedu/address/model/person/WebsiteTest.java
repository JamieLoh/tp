package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class WebsiteTest {

    @Test
    public void isValidWebsite() {
        assertFalse(Website.isValidWebsite(null));
        assertFalse(Website.isValidWebsite(""));
        assertFalse(Website.isValidWebsite("   "));

        assertTrue(Website.isValidWebsite("https://example.com"));
        assertTrue(Website.isValidWebsite("example.com"));
    }
}
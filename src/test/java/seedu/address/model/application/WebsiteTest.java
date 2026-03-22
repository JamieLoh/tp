package seedu.address.model.application;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class WebsiteTest {

    @Test
    public void isValidWebsite() {
        assertTrue(Website.isValidWebsite("google.com"));
        assertTrue(Website.isValidWebsite("www.google.com"));
        assertTrue(Website.isValidWebsite("https://google.com"));
        assertTrue(Website.isValidWebsite("https://nus.edu.sg"));

        assertFalse(Website.isValidWebsite(""));
        assertFalse(Website.isValidWebsite(" "));
        assertFalse(Website.isValidWebsite("abc"));
        assertFalse(Website.isValidWebsite("http://"));
        assertFalse(Website.isValidWebsite("not a website"));
    }
}

package seedu.address.model.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.ApplicationBuilder;

public class ApplicationMatchesPredicateTest {

    @Test
    public void equals() {
        ApplicationMatchesPredicate first =
                new ApplicationMatchesPredicate(
                        "Google", null, null, null,
                        null, null, null, Collections.emptyList());
        ApplicationMatchesPredicate second =
                new ApplicationMatchesPredicate(
                        "Grab", null, null, null,
                        null, null, null, Collections.emptyList());


        // same object -> returns true
        assertTrue(first.equals(first));

        // same values -> returns true
        ApplicationMatchesPredicate firstPredicateCopy =
                new ApplicationMatchesPredicate(
                        "Google", null, null, null,
                        null, null, null, Collections.emptyList());
        assertTrue(first.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(first.equals(1));

        // null -> returns false
        assertFalse(first.equals(null));

        // different application -> returns false
        assertFalse(first.equals(second));
    }

    @Test
    public void test_nameMatches_returnsTrue() {
        ApplicationMatchesPredicate predicate =
                new ApplicationMatchesPredicate(
                        "Google", null, null, null,
                        null, null, null, Collections.emptyList());
        assertTrue(predicate.test(new ApplicationBuilder().withName("Google").build()));
    }

    @Test
    public void test_nameDoesNotMatch_returnsFalse() {
        ApplicationMatchesPredicate predicate =
                new ApplicationMatchesPredicate(
                        "Google", null, null, null,
                        null, null, null, Collections.emptyList());
        assertFalse(predicate.test(new ApplicationBuilder().withName("Grab").build()));
    }

    @Test
    public void test_emailMatches_returnsTrue() {
        ApplicationMatchesPredicate predicate =
                new ApplicationMatchesPredicate(null, null, "example", null,
                        null, null, null, Collections.emptyList());

        assertTrue(predicate.test(new ApplicationBuilder()
                .withEmail("example@gmail.com").build()));
    }

    @Test
    public void test_emailEmpty_returnsTrueWhenNoEmail() {
        ApplicationMatchesPredicate predicate =
                new ApplicationMatchesPredicate(null, null, "", null,
                        null, null, null, Collections.emptyList());

        assertTrue(predicate.test(new ApplicationBuilder().withEmail(null).build()));
    }

    @Test
    public void test_emailKeywordButNoEmail_returnsFalse() {
        ApplicationMatchesPredicate predicate =
                new ApplicationMatchesPredicate(null, null, "gmail", null,
                        null, null, null, Collections.emptyList());

        assertFalse(predicate.test(new ApplicationBuilder().withEmail(null).build()));
    }

    @Test
    public void test_emailDoesNotMatch_returnsFalse() {
        ApplicationMatchesPredicate predicate =
                new ApplicationMatchesPredicate(null, null, "gmail", null,
                        null, null, null, Collections.emptyList());

        assertFalse(predicate.test(new ApplicationBuilder()
                .withEmail("yahoo@yahoo.com").build()));
    }

    @Test
    public void test_websiteDoesNotMatch_returnsFalse() {
        ApplicationMatchesPredicate predicate =
                new ApplicationMatchesPredicate(null, null, null, "google",
                        null, null, null, Collections.emptyList());

        assertFalse(predicate.test(new ApplicationBuilder()
                .withWebsite("amazon.com").build()));
    }


    @Test
    public void test_addressDoesNotMatch_returnsFalse() {
        ApplicationMatchesPredicate predicate =
                new ApplicationMatchesPredicate(null, null, null, null,
                        "clementi", null, null, Collections.emptyList());

        assertFalse(predicate.test(new ApplicationBuilder()
                .withAddress("jurong").build()));
    }

    @Test
    public void test_dateDoesNotMatch_returnsFalse() {
        ApplicationMatchesPredicate predicate =
                new ApplicationMatchesPredicate(null, null, null, null,
                        null, "06-03-2026", null, Collections.emptyList());

        assertFalse(predicate.test(new ApplicationBuilder()
                .withDate("02-03-2025").build()));
    }

    @Test
    public void test_statusDoesNotMatch_returnsFalse() {
        ApplicationMatchesPredicate predicate =
                new ApplicationMatchesPredicate(null, null, null, null,
                        null, null, "pending", Collections.emptyList());

        assertFalse(predicate.test(new ApplicationBuilder()
                .withStatus("rejected").build()));
    }

    @Test
    public void test_tagMatches_returnsTrue() {
        ApplicationMatchesPredicate predicate =
                new ApplicationMatchesPredicate(null, null, null, null,
                        null, null, null, Arrays.asList("AI"));

        assertTrue(predicate.test(new ApplicationBuilder()
                .withTags("AI").build()));
    }

    @Test
    public void test_tagOrLogic_returnsTrue() {
        ApplicationMatchesPredicate predicate =
                new ApplicationMatchesPredicate(null, null, null, null,
                        null, null, null, Arrays.asList("AI", "ML"));

        assertTrue(predicate.test(new ApplicationBuilder()
                .withTags("ML").build()));
    }

    @Test
    public void test_tagEmpty_returnsTrueWhenNoTags() {
        ApplicationMatchesPredicate predicate =
                new ApplicationMatchesPredicate(null, null, null, null,
                        null, null, null, Collections.singletonList(""));

        assertTrue(predicate.test(new ApplicationBuilder().withTags().build()));
    }

    @Test
    public void test_tagDoesNotMatch_returnsFalse() {
        ApplicationMatchesPredicate predicate =
                new ApplicationMatchesPredicate(null, null, null, null,
                        null, null, null, Arrays.asList("AI"));

        assertFalse(predicate.test(new ApplicationBuilder()
                .withTags("Finance").build()));
    }

    @Test
    public void test_multipleFieldsMatch_returnsTrue() {
        ApplicationMatchesPredicate predicate =
                new ApplicationMatchesPredicate("Google", "Engineer", null, null,
                        null, null, null, Arrays.asList("AI"));

        assertTrue(predicate.test(new ApplicationBuilder()
                .withName("Google")
                .withRole("Engineer")
                .withTags("AI")
                .build()));
    }

    @Test
    public void test_multipleFieldsFail_returnsFalse() {
        ApplicationMatchesPredicate predicate =
                new ApplicationMatchesPredicate("Google", "Engineer", null, null,
                        null, null, null, Arrays.asList("AI"));

        assertFalse(predicate.test(new ApplicationBuilder()
                .withName("Grab")
                .withRole("Designer")
                .withTags("Finance")
                .build()));
    }


    @Test
    public void toStringMethod() {
        ApplicationMatchesPredicate predicate =
                new ApplicationMatchesPredicate(
                        "Google", null, null, null,
                        null, null, null, Collections.emptyList());

        String expected = ApplicationMatchesPredicate.class.getCanonicalName()
                + "{name=google, role=null, email=null, website=null, address=null, date=null, status=null, tags=[]}";
        assertEquals(expected, predicate.toString());
    }
}

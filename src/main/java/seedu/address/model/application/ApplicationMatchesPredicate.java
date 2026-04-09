package seedu.address.model.application;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;

/**
 * Tests that {@code Application} matches the keywords given.
 * All matching is case-insensitive and partial.
 */
public class ApplicationMatchesPredicate implements Predicate<Application> {
    private static final Logger logger = LogsCenter.getLogger(ApplicationMatchesPredicate.class);
    private final String nameKeyword;
    private final String roleKeyword;
    private final String emailKeyword;
    private final String websiteKeyword;
    private final String addressKeyword;
    private final String dateKeyword;
    private final String statusKeyword;
    private final List<String> tagKeywords;

    /**
     * Constructs an ApplicationMatchesPredicate with the given keywords.
     * All keywords are converted to lower case for case-insensitive matching.
     */
    public ApplicationMatchesPredicate(
            String nameKeyword,
            String roleKeyword,
            String emailKeyword,
            String websiteKeyword,
            String addressKeyword,
            String dateKeyword,
            String statusKeyword,
            List<String> tagKeywords) {
        this.nameKeyword = toLowerCase(nameKeyword);
        this.roleKeyword = toLowerCase(roleKeyword);
        this.emailKeyword = toLowerCase(emailKeyword);
        this.websiteKeyword = toLowerCase(websiteKeyword);
        this.addressKeyword = toLowerCase(addressKeyword);
        this.dateKeyword = toLowerCase(dateKeyword);
        this.statusKeyword = toLowerCase(statusKeyword);
        this.tagKeywords =
                tagKeywords == null
                        ? null
                        : tagKeywords.stream().map(this::toLowerCase).toList();
    }

    /**
     * Converts the input string to lower case. If the input is null, returns null.
     */
    private String toLowerCase(String string) {
        return string == null ? null : string.toLowerCase();
    }

    @Override
    public boolean test(Application application) {
        logger.fine("Testing Application: " + application);

        boolean result = matchName(application)
                && matchRole(application)
                && matchEmail(application)
                && matchWebsite(application)
                && matchAddress(application)
                && matchDate(application)
                && matchStatus(application)
                && matchTags(tagKeywords, application.getTags());
        logger.fine("Match result: " + result);
        return result;
    }

    /**
     * Each match method checks if the corresponding field in the application matches the keyword.
     * If the keyword is null, it matches all values for that field.
     */
    private boolean matchName(Application application) {
        return matchCompulsoryField(nameKeyword,
                application.getCompanyName().toString());
    }

    /** Similar to matchName, but for role. */
    private boolean matchRole(Application application) {
        return matchCompulsoryField(roleKeyword,
                application.getRole().toString());
    }

    /** Similar to matchName, but for date. */
    private boolean matchDate(Application application) {
        return matchCompulsoryField(dateKeyword,
                application.getDate().toString());
    }

    /** Similar to matchName, but for status. */
    private boolean matchStatus(Application application) {
        return matchCompulsoryField(statusKeyword,
                application.getStatus().toString());
    }

    /**
     * Each match method checks if the corresponding field in the application matches the keyword.
     * If the keyword is null, it matches all values for that field.
     * For optional fields here (email, website, address),
     * if the keyword is empty, it matches applications with no value for that field.
     */
    private boolean matchEmail(Application application) {
        return matchOptionalField(emailKeyword,
                application.getEmail() == null ? null : application.getEmail().toString());
    }

    /** Similar to matchEmail, but for website. */
    private boolean matchWebsite(Application application) {
        return matchOptionalField(websiteKeyword,
                application.getWebsite() == null ? null : application.getWebsite().toString());
    }

    /** Similar to matchEmail, but for address. */
    private boolean matchAddress(Application application) {
        return matchOptionalField(addressKeyword,
                application.getAddress() == null ? null : application.getAddress().toString());
    }


    /**
     * Returns true if the application's field matches the keyword.
     * If the keyword is null, it matches all values for that field.
     */
    private boolean matchCompulsoryField(String keyword, String value) {
        if (keyword == null) {
            return true;
        }
        return value.toLowerCase().contains(keyword);
    }

    /**
     * Returns true if the application's field matches the keyword.
     * If the keyword is null, it matches all values for that field.
     * If the keyword is empty, it matches applications with no value for that field.
     */
    private boolean matchOptionalField(String keyword, String value) {
        if (keyword == null) {
            return true;
        }

        // search for empty keyword (e.g. e/ should match applications with no email)
        if (keyword.isEmpty()) {
            return value == null;
        }

        // normal matching: search for keyword in the value
        // (e.g. e/gmail should match applications with email containing "gmail")
        return value != null && value.toLowerCase().contains(keyword);
    }


    /**
     * Returns true if the application's tags match the tag keywords.
     * If no tag keywords are provided, it matches all tags.
     * If the tag keywords contain only an empty string, it matches applications with no tags.
     */
    private boolean matchTags(List<String> keywords, Set<Tag> tags) {
        // If no tag keywords are provided, we consider it as matching all tags
        if (keywords == null || keywords.isEmpty()) {
            return true;
        }

        // check if any tag prefix (i.e. t/ or t/ t/)
        if (isSearchingForEmptyTag(keywords)) {
            return tags.isEmpty();
        }

        // match if any tag matches any of the tag keywords
        for (Tag tag : tags) {
            String tagName = tag.tagName.toLowerCase();

            for (String keyword : keywords) {
                if (keyword.isEmpty()) {
                    continue;
                }

                if (tagName.contains(keyword)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns true if the keywords list contains only an empty string.
     * This indicates that the user is searching for applications with no tags.
     */
    private boolean isSearchingForEmptyTag(List<String> keywords) {
        for (String keyword : keywords) {
            if (!keyword.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ApplicationMatchesPredicate)) {
            return false;
        }

        ApplicationMatchesPredicate otherPredicate = (ApplicationMatchesPredicate) other;
        return Objects.equals(nameKeyword, otherPredicate.nameKeyword)
                && Objects.equals(roleKeyword, otherPredicate.roleKeyword)
                && Objects.equals(emailKeyword, otherPredicate.emailKeyword)
                && Objects.equals(websiteKeyword, otherPredicate.websiteKeyword)
                && Objects.equals(addressKeyword, otherPredicate.addressKeyword)
                && Objects.equals(dateKeyword, otherPredicate.dateKeyword)
                && Objects.equals(statusKeyword, otherPredicate.statusKeyword)
                && Objects.equals(tagKeywords, otherPredicate.tagKeywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", nameKeyword)
                .add("role", roleKeyword)
                .add("email", emailKeyword)
                .add("website", websiteKeyword)
                .add("address", addressKeyword)
                .add("date", dateKeyword)
                .add("status", statusKeyword)
                .add("tags", tagKeywords)
                .toString();
    }
}

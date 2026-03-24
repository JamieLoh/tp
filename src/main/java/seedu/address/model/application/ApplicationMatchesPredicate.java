package seedu.address.model.application;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;

/**
 * Tests that {@code Application} matches the keywords given.
 * All matching is case-insensitive and partial.
 */
public class ApplicationMatchesPredicate implements Predicate<Application> {
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
        if (nameKeyword != null) {
            if (!application.getCompanyName().toString().toLowerCase()
                    .contains(nameKeyword)) {
                return false;
            }
        }

        if (roleKeyword != null) {
            if (!application.getRole().toString().toLowerCase()
                    .contains(roleKeyword)) {
                return false;
            }
        }

        // optional field: find e/ should match applications with no email
        if (emailKeyword != null) {
            if (emailKeyword.isEmpty()) {
                if (application.getEmail() != null) {
                    return false;
                }
            } else {
                if (application.getEmail() == null
                        || !application.getEmail().toString().toLowerCase()
                        .contains(emailKeyword)) {
                    return false;
                }
            }

        }

        if (websiteKeyword != null) {
            if (websiteKeyword.isEmpty()) {
                if (application.getWebsite() != null) {
                    return false;
                }
            } else {
                if (application.getWebsite() == null
                        || !application.getWebsite().toString().toLowerCase()
                        .contains(websiteKeyword)) {
                    return false;
                }
            }
        }

        if (addressKeyword != null) {
            if (addressKeyword.isEmpty()) {
                if (application.getAddress() != null) {
                    return false;
                }
            } else {
                if (application.getAddress() == null
                        || !application.getAddress().toString().toLowerCase()
                        .contains(addressKeyword)) {
                    return false;
                }
            }
        }

        if (dateKeyword != null) {
            if (!application.getDate().toString().toLowerCase()
                    .contains(dateKeyword)) {
                return false;
            }
        }

        if (statusKeyword != null) {
            if (!application.getStatus().toString().toLowerCase()
                    .contains(statusKeyword)) {
                return false;
            }
        }

        if (!matchTags(tagKeywords, application.getTags())) {
            return false;
        }

        return true;
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

package seedu.address.model.application;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;

/**
 * Represents a Application in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Application {

    // Identity fields
    private final CompanyName companyName;
    private final Role role;
    private final Email email;

    // Data fields
    private final Website website;
    private final Address address;
    private final Date date;
    private final Status status;
    private final String notes;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Application(CompanyName companyName, Role role, Email email, Website website,
                       Address address, Date date, Status status, Set<Tag> tags) {
        this(companyName, role, email, website, address, date, status, tags, "");
    }

    /**
     * Every field must be present and not null. Notes can be empty.
     */
    public Application(CompanyName companyName, Role role, Email email, Website website,
                       Address address, Date date, Status status, Set<Tag> tags, String notes) {
        requireAllNonNull(companyName, role, website, address, date, status, tags);
        this.companyName = companyName;
        this.role = role;
        this.email = email; //can be null
        this.website = website;
        this.address = address;
        this.date = date;
        this.status = status;
        this.notes = notes == null ? "" : notes;
        this.tags.addAll(tags);
    }

    public CompanyName getCompanyName() {
        return companyName;
    }

    public Role getRole() {
        return role;
    }

    public Email getEmail() {
        return email;
    }

    public Website getWebsite() {
        return website;
    }

    public Address getAddress() {
        return address;
    }

    public Date getDate() {
        return date;
    }

    public Status getStatus() {
        return status;
    }

    public String getNotes() {
        return notes;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both applications have the same name.
     * This defines a weaker notion of equality between two applications.
     */
    public boolean isSameApplication(Application otherApplication) {
        if (otherApplication == this) {
            return true;
        }

        return otherApplication != null
                && otherApplication.getCompanyName().equals(getCompanyName())
                && otherApplication.getRole().equals(getRole());
    }

    /**
     * Returns true if both applications have the same identity and data fields.
     * This defines a stronger notion of equality between two applications.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Application)) {
            return false;
        }

        Application otherApplication = (Application) other;
        return companyName.equals(otherApplication.companyName)
                && role.equals(otherApplication.role)
                && java.util.Objects.equals(email, otherApplication.email)
                && website.equals(otherApplication.website)
                && address.equals(otherApplication.address)
                && date.equals(otherApplication.date)
                && status.equals(otherApplication.status)
                && notes.equals(otherApplication.notes)
                && tags.equals(otherApplication.tags);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(companyName, role, email, website, address, date, status, notes, tags);
    }

    @Override
    public String toString() {
        ToStringBuilder builder = new ToStringBuilder(this)
                .add("companyName", companyName)
                .add("role", role)
                .add("website", website)
                .add("address", address)
                .add("date", date)
                .add("status", status)
                .add("tags", tags);

        if (email != null) {
            builder.add("email", email);
        }

        return builder.toString();
    }

}

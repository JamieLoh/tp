package seedu.address.model.application;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;

/**
 * Represents an Application in the HireME.
 * Guarantees: companyName, role, date, status, and tags are present and not null.
 * Email, website, and address are optional and may be null.
 * Field values are validated, immutable.
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
    private final boolean isArchived;
    private final Set<Tag> tags = new HashSet<>();

    public Application(CompanyName companyName, Role role, Email email, Website website,
                       Address address, Date date, Status status, Set<Tag> tags) {
        this(companyName, role, email, website, address, date, status, tags, "", false);
    }

    /**
     * Every field must be present and not null. Notes can be empty.
     */
    public Application(CompanyName companyName, Role role, Email email, Website website,
                       Address address, Date date, Status status, Set<Tag> tags, String notes) {
        this(companyName, role, email, website, address, date, status, tags, notes, false);
    }

    /**
     * Every field must be present and not null.
     */
    public Application(CompanyName companyName, Role role, Email email, Website website,
                       Address address, Date date, Status status, Set<Tag> tags, boolean isArchived) {
        this(companyName, role, email, website, address, date, status, tags, "", isArchived);
    }

    /**
     * Every field must be present and not null. Notes can be empty.
     */
    public Application(CompanyName companyName, Role role, Email email, Website website,
                       Address address, Date date, Status status, Set<Tag> tags, String notes, boolean isArchived) {
        requireAllNonNull(companyName, role, date, status, tags);
        this.companyName = companyName;
        this.role = role;
        this.email = email; //can be null
        this.website = website; //can be null
        this.address = address; //can be null
        this.date = date;
        this.status = status;
        this.notes = notes == null ? "" : notes;
        this.isArchived = isArchived;
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

    public boolean isArchived() {
        return isArchived;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both applications have the same company name and role, ignoring case.
     * Note: Leading/trailing and multiple spaces are already normalized in CompanyName and Role.
     */
    public boolean isSameApplication(Application otherApplication) {
        if (otherApplication == this) {
            return true;
        }

        return otherApplication != null
                && this.getCompanyName().isSameCompanyName(otherApplication.getCompanyName())
                && this.getRole().isSameRole(otherApplication.getRole());
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
                && Objects.equals(email, otherApplication.email)
                && Objects.equals(website, otherApplication.website)
                && Objects.equals(address, otherApplication.address)
                && date.equals(otherApplication.date)
                && status.equals(otherApplication.status)
                && notes.equals(otherApplication.notes)
                && isArchived == otherApplication.isArchived
                && tags.equals(otherApplication.tags);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(companyName, role, email, website, address, date, status, notes, isArchived, tags);
    }

    @Override
    public String toString() {
        ToStringBuilder builder = new ToStringBuilder(this)
                .add("companyName", companyName)
                .add("role", role)
                .add("date", date)
                .add("status", status)
                .add("isArchived", isArchived)
                .add("tags", tags);

        if (email != null) {
            builder.add("email", email);
        }
        if (website != null) {
            builder.add("website", website);
        }

        if (address != null) {
            builder.add("address", address);
        }

        return builder.toString();
    }

}

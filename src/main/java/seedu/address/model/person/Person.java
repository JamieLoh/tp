package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final CompanyName companyName;
    private final Role role;
    private final Email email;

    // Data fields
    private final Website website;
    private final Address address;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Person(CompanyName companyName, Role role, Email email, Website website, Address address, Set<Tag> tags) {
        requireAllNonNull(companyName, role, email, website, address, tags);
        this.companyName = companyName;
        this.role = role;
        this.email = email;
        this.website = website;
        this.address = address;
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

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getCompanyName().equals(getCompanyName());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return companyName.equals(otherPerson.companyName)
                && role.equals(otherPerson.role)
                && email.equals(otherPerson.email)
                && website.equals(otherPerson.website)
                && address.equals(otherPerson.address)
                && tags.equals(otherPerson.tags);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(companyName, role, email, website, address, tags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("companyName", companyName)
                .add("role", role)
                .add("email", email)
                .add("website", website)
                .add("address", address)
                .add("tags", tags)
                .toString();
    }

}

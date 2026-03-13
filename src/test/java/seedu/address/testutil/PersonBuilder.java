package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.person.Address;
import seedu.address.model.person.CompanyName;
import seedu.address.model.person.Email;
import seedu.address.model.person.Person;
import seedu.address.model.person.Role;
import seedu.address.model.person.Website;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_COMPANY_NAME = "Amazon";
    public static final String DEFAULT_ROLE = "Backend Developer Intern";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_WEBSITE = "https://example.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";


    private CompanyName companyName;
    private Role role;
    private Email email;
    private Website website;
    private Address address;
    private Set<Tag> tags;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        companyName = new CompanyName(DEFAULT_COMPANY_NAME);
        role = new Role(DEFAULT_ROLE);
        email = new Email(DEFAULT_EMAIL);
        website = new Website(DEFAULT_WEBSITE);
        address = new Address(DEFAULT_ADDRESS);
        tags = new HashSet<>();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        companyName = personToCopy.getCompanyName();
        role = personToCopy.getRole();
        email = personToCopy.getEmail();
        website = personToCopy.getWebsite();
        address = personToCopy.getAddress();
        tags = new HashSet<>(personToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.companyName = new CompanyName(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Person} that we are building.
     */
    public PersonBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Role} of the {@code Role} that we are building.
     */
    public PersonBuilder withRole(String role) {
        this.role = new Role(role);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */
    public PersonBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code Website} of the {@code Person} that we are building.
     */
    public PersonBuilder withWebsite(String website) {
        this.website = new Website(website);
        return this;
    }

    public Person build() {
        return new Person(companyName, role, email, website, address, tags);
    }

}

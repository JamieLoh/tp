package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.application.Address;
import seedu.address.model.application.Application;
import seedu.address.model.application.CompanyName;
import seedu.address.model.application.Date;
import seedu.address.model.application.Email;
import seedu.address.model.application.Role;
import seedu.address.model.application.Status;
import seedu.address.model.application.Website;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Application objects.
 */
public class ApplicationBuilder {

    public static final String DEFAULT_COMPANY_NAME = "Amazon";
    public static final String DEFAULT_ROLE = "Backend Developer Intern";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_WEBSITE = "https://example.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_DATE = "01-03-2026";
    public static final String DEFAULT_STATUS = "Pending";

    private CompanyName companyName;
    private Role role;
    private Email email;
    private Website website;
    private Address address;
    private Date date;
    private Status status;
    private Set<Tag> tags;

    /**
     * Creates a {@code ApplicationBuilder} with the default details.
     */
    public ApplicationBuilder() {
        companyName = new CompanyName(DEFAULT_COMPANY_NAME);
        role = new Role(DEFAULT_ROLE);
        email = new Email(DEFAULT_EMAIL);
        website = new Website(DEFAULT_WEBSITE);
        address = new Address(DEFAULT_ADDRESS);
        date = new Date(DEFAULT_DATE);
        status = new Status(DEFAULT_STATUS);
        tags = new HashSet<>();
    }

    /**
     * Initializes the ApplicationBuilder with the data of {@code applicationToCopy}.
     */
    public ApplicationBuilder(Application applicationToCopy) {
        companyName = applicationToCopy.getCompanyName();
        role = applicationToCopy.getRole();
        email = applicationToCopy.getEmail();
        website = applicationToCopy.getWebsite();
        address = applicationToCopy.getAddress();
        date = applicationToCopy.getDate();
        status = applicationToCopy.getStatus();
        tags = new HashSet<>(applicationToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Application} that we are building.
     */
    public ApplicationBuilder withName(String name) {
        this.companyName = new CompanyName(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Application} that we are building.
     */
    public ApplicationBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Application} that we are building.
     */
    public ApplicationBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Role} of the {@code Role} that we are building.
     */
    public ApplicationBuilder withRole(String role) {
        this.role = new Role(role);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Application} that we are building.
     */
    public ApplicationBuilder withEmail(String email) {
        if (email == null) {
            this.email = null;
        } else {
            this.email = new Email(email);
        }
        return this;
    }

    /**
     * Sets the {@code Website} of the {@code Application} that we are building.
     */
    public ApplicationBuilder withWebsite(String website) {
        this.website = new Website(website);
        return this;
    }

    /**
     * Sets the {@code Date} of the {@code Application} that we are building.
     */
    public ApplicationBuilder withDate(String date) {
        this.date = new Date(date);
        return this;
    }

    /**
     * Sets the {@code Status} of the {@code Application} that we are building.
     */
    public ApplicationBuilder withStatus(String status) {
        this.status = new Status(status);
        return this;
    }

    public Application build() {
        return new Application(companyName, role, email, website, address, date, status, tags);
    }

}

package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.application.Address;
import seedu.address.model.application.Application;
import seedu.address.model.application.CompanyName;
import seedu.address.model.application.Date;
import seedu.address.model.application.Email;
import seedu.address.model.application.Role;
import seedu.address.model.application.Website;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Application[] getSampleApplications() {
        return new Application[] {
            new Application(new CompanyName("Alex Yeoh"), new Role("87438807"), new Email("alexyeoh@example.com"),
                new Website("https://example.com"), new Address("Blk 30 Geylang Street 29, #06-40"),
                new Date("01-03-2026"), getTagSet("friends")),
            new Application(new CompanyName("Bernice Yu"), new Role("99272758"), new Email("berniceyu@example.com"),
                    new Website("https://example.com"), new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                new Date("02-03-2026"), getTagSet("colleagues", "friends")),
            new Application(new CompanyName("Charlotte Oliveiro"), new Role("93210283"),
                    new Email("charlotte@example.com"),
                    new Website("https://example.com"), new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                new Date("03-03-2026"), getTagSet("neighbours")),
            new Application(new CompanyName("David Li"), new Role("91031282"), new Email("lidavid@example.com"),
                    new Website("https://example.com"), new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                new Date("04-03-2026"), getTagSet("family")),
            new Application(new CompanyName("Irfan Ibrahim"), new Role("92492021"), new Email("irfan@example.com"),
                    new Website("https://example.com"), new Address("Blk 47 Tampines Street 20, #17-35"),
                new Date("05-03-2026"), getTagSet("classmates")),
            new Application(new CompanyName("Roy Balakrishnan"), new Role("92624417"), new Email("royb@example.com"),
                    new Website("https://example.com"), new Address("Blk 45 Aljunied Street 85, #11-31"),
                new Date("06-03-2026"), getTagSet("colleagues"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Application sampleApplication : getSampleApplications()) {
            sampleAb.addApplication(sampleApplication);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}

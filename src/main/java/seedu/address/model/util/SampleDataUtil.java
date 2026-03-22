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
import seedu.address.model.application.Status;
import seedu.address.model.application.Website;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Application[] getSampleApplications() {
        return new Application[] {
            new Application(new CompanyName("ByteDance"), new Role("Software Engineer"),
                new Email("careers@bytedance.com"),
                new Website("https://jobs.bytedance.com"),
                new Address("8 Shenton Way, AXA Tower, Singapore 068811"),
                new Date("01-03-2026"), new Status("Pending"), getTagSet("OA")),
            new Application(new CompanyName("Google"), new Role("Software Engineer"),
                new Email("careers@google.com"),
                new Website("https://careers.google.com"),
                new Address("70 Pasir Panjang Road, Google Singapore, Singapore 117371"),
                new Date("02-03-2026"), new Status("Offered"), getTagSet("FANG", "OA")),
            new Application(new CompanyName("Meta"), new Role("Backend Engineer"),
                new Email("careers@meta.com"),
                new Website("https://www.metacareers.com"),
                new Address("9 Straits View, Marina One, Singapore 018937"),
                new Date("03-03-2026"), new Status("Rejected"), getTagSet("InterviewRound2")),
            new Application(new CompanyName("Amazon"), new Role("Cloud Engineer"), new Email("careers@amazon.com"),
                new Website("https://www.amazon.jobs"),
                new Address("23 Church Street, Capital Square, Singapore 049481"),
                new Date("04-03-2026"), new Status("Pending"), getTagSet("FANG", "InterviewRound1")),
            new Application(new CompanyName("Grab"), new Role("Data Engineer"), new Email("careers@grab.com"),
                new Website("https://careers.grab.com"), new Address("3 Media Close, Singapore 138498"),
                new Date("05-03-2026"), new Status("Pending"), getTagSet("InterviewRound1")),
            new Application(new CompanyName("Stripe"), new Role("Full Stack Engineer"),
                new Email("careers@stripe.com"),
                new Website("https://stripe.com/jobs"),
                new Address("1 One-North Crescent, Infinite Studios, Singapore 138538"),
                new Date("06-03-2026"), new Status("Offered"), getTagSet("Fintech", "InterviewRound3")),
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

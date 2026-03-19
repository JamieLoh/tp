package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COMPANY_NAME_AMAZON;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COMPANY_NAME_BMW;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROLE_BACKEND_DEVELOPER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROLE_FRONTEND_DEVELOPER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STATUS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STATUS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WEBSITE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WEBSITE_BOB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.application.Application;

/**
 * A utility class containing a list of {@code Application} objects to be used in tests.
 */
public class TypicalApplications {

    public static final Application ALICE = new ApplicationBuilder().withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withRole("Machine Learning Engineer").withWebsite("https://alice.com")
            .withDate("01-01-2026").withTags("friends").build();
    public static final Application BENSON = new ApplicationBuilder().withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25").withWebsite("https://benson.com")
            .withEmail("johnd@example.com").withRole("Data Engineer")
            .withDate("02-02-2026").withTags("owesMoney", "friends").build();
    public static final Application CARL = new ApplicationBuilder().withName("Carl Kurz").withRole("Game Developer")
            .withEmail("heinz@example.com").withWebsite("https://carl.com")
            .withAddress("wall street").withDate("13-01-2026").build();
    public static final Application DANIEL = new ApplicationBuilder()
            .withName("Daniel Meier").withRole("Security Engineer")
            .withEmail("cornelia@example.com").withWebsite("https://daniel.com")
            .withAddress("10th street").withDate("24-01-2026").withTags("friends").build();
    public static final Application ELLE = new ApplicationBuilder()
            .withName("Elle Meyer").withRole("Full Stack Developer")
            .withEmail("werner@example.com").withWebsite("https://elle.com")
            .withAddress("michegan ave").withDate("05-02-2026").build();
    public static final Application FIONA = new ApplicationBuilder().withName("Fiona Kunz").withRole("DevOps Engineer")
            .withEmail("lydia@example.com").withWebsite("https://fiona.com")
            .withAddress("little tokyo").withDate("06-02-2026").build();
    public static final Application GEORGE = new ApplicationBuilder()
            .withName("George Best").withRole("Cloud Architect")
            .withEmail("anna@example.com").withWebsite("https://george.com")
            .withAddress("4th street").withDate("07-01-2026").build();

    // Manually added
    public static final Application HOON = new ApplicationBuilder()
            .withName("Hoon Meier").withRole("QA Test Automation Engineer")
            .withEmail("stefan@example.com").withWebsite("https://hoon.com")
            .withAddress("little india").withDate("08-01-2026").build();
    public static final Application IDA = new ApplicationBuilder()
            .withName("Ida Mueller").withRole("Embedded Systems Engineer")
            .withEmail("hans@example.com").withWebsite("https://ida.com")
            .withAddress("chicago ave").withDate("09-01-2026").build();

    // Manually added - Application's details found in {@code CommandTestUtil}
    public static final Application AMY = new ApplicationBuilder()
            .withName(VALID_COMPANY_NAME_AMAZON).withRole(VALID_ROLE_FRONTEND_DEVELOPER)
            .withEmail(VALID_EMAIL_AMY).withWebsite(VALID_WEBSITE_AMY)
            .withAddress(VALID_ADDRESS_AMY).withDate(VALID_DATE_AMY)
            .withStatus(VALID_STATUS_AMY).withTags(VALID_TAG_FRIEND).build();
    public static final Application BOB = new ApplicationBuilder()
            .withName(VALID_COMPANY_NAME_BMW).withRole(VALID_ROLE_BACKEND_DEVELOPER)
            .withEmail(VALID_EMAIL_BOB).withWebsite(VALID_WEBSITE_BOB)
            .withAddress(VALID_ADDRESS_BOB).withDate(VALID_DATE_BOB)
            .withStatus(VALID_STATUS_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalApplications() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical applications.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Application application : getTypicalApplications()) {
            ab.addApplication(application);
        }
        return ab;
    }

    public static List<Application> getTypicalApplications() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}

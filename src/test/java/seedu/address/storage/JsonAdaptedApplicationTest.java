package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedApplication.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalApplications.BENSON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.application.Address;
import seedu.address.model.application.CompanyName;
import seedu.address.model.application.Date;
import seedu.address.model.application.Email;
import seedu.address.model.application.Role;
import seedu.address.model.application.Status;
import seedu.address.model.application.Website;

public class JsonAdaptedApplicationTest {
    private static final String INVALID_COMPANY_NAME = "R@chel";
    private static final String INVALID_ROLE = "Algorithm@Engineer";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_DATE = "2026-03-01";
    private static final String INVALID_TAG = "#friend";
    private static final String INVALID_WEBSITE = " ";
    private static final String INVALID_STATUS = "Unknown";
    private static final String VALID_COMPANY_NAME = BENSON.getCompanyName().toString();
    private static final String VALID_ROLE = BENSON.getRole().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_ADDRESS = BENSON.getAddress().toString();
    private static final String VALID_WEBSITE = BENSON.getWebsite().toString();
    private static final String VALID_DATE = BENSON.getDate().toString();
    private static final String VALID_STATUS = BENSON.getStatus().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validApplicationDetails_returnsApplication() throws Exception {
        JsonAdaptedApplication application = new JsonAdaptedApplication(BENSON);
        assertEquals(BENSON, application.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedApplication application =
                new JsonAdaptedApplication(INVALID_COMPANY_NAME, VALID_ROLE, VALID_EMAIL, VALID_WEBSITE,
                        VALID_ADDRESS, VALID_DATE, VALID_STATUS, VALID_TAGS);
        String expectedMessage = CompanyName.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, application::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedApplication application = new JsonAdaptedApplication(null, VALID_ROLE, VALID_EMAIL,
                VALID_WEBSITE, VALID_ADDRESS, VALID_DATE, VALID_STATUS, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, CompanyName.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, application::toModelType);
    }

    @Test
    public void toModelType_invalidRole_throwsIllegalValueException() {
        JsonAdaptedApplication application =
                new JsonAdaptedApplication(VALID_COMPANY_NAME, INVALID_ROLE, VALID_EMAIL, VALID_WEBSITE,
                        VALID_ADDRESS, VALID_DATE, VALID_STATUS, VALID_TAGS);
        String expectedMessage = Role.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, application::toModelType);
    }

    @Test
    public void toModelType_nullRole_throwsIllegalValueException() {
        JsonAdaptedApplication application = new JsonAdaptedApplication(VALID_COMPANY_NAME, null, VALID_EMAIL,
                VALID_WEBSITE, VALID_ADDRESS, VALID_DATE, VALID_STATUS, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Role.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, application::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedApplication application =
                new JsonAdaptedApplication(VALID_COMPANY_NAME, VALID_ROLE, INVALID_EMAIL, VALID_WEBSITE,
                        VALID_ADDRESS, VALID_DATE, VALID_STATUS, VALID_TAGS);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, application::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedApplication application = new JsonAdaptedApplication(VALID_COMPANY_NAME, VALID_ROLE, null,
                VALID_WEBSITE, VALID_ADDRESS, VALID_DATE, VALID_STATUS, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, application::toModelType);
    }

    @Test
    public void toModelType_nullWebsite_throwsIllegalValueException() {
        JsonAdaptedApplication application = new JsonAdaptedApplication(VALID_COMPANY_NAME, VALID_ROLE, VALID_EMAIL,
                null, VALID_ADDRESS, VALID_DATE, VALID_STATUS, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Website.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, application::toModelType);
    }

    @Test
    public void toModelType_invalidWebsite_throwsIllegalValueException() {
        JsonAdaptedApplication application =
                new JsonAdaptedApplication(VALID_COMPANY_NAME, VALID_ROLE, VALID_EMAIL, INVALID_WEBSITE,
                        VALID_ADDRESS, VALID_DATE, VALID_STATUS, VALID_TAGS);
        String expectedMessage = Website.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, application::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedApplication application =
                new JsonAdaptedApplication(VALID_COMPANY_NAME, VALID_ROLE, VALID_EMAIL, VALID_WEBSITE,
                        INVALID_ADDRESS, VALID_DATE, VALID_STATUS, VALID_TAGS);
        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, application::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        JsonAdaptedApplication application = new JsonAdaptedApplication(VALID_COMPANY_NAME, VALID_ROLE, VALID_EMAIL,
                VALID_WEBSITE, null, VALID_DATE, VALID_STATUS, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, application::toModelType);
    }

    @Test
    public void toModelType_invalidDate_throwsIllegalValueException() {
        JsonAdaptedApplication application =
                new JsonAdaptedApplication(VALID_COMPANY_NAME, VALID_ROLE, VALID_EMAIL, VALID_WEBSITE,
                        VALID_ADDRESS, INVALID_DATE, VALID_STATUS, VALID_TAGS);
        String expectedMessage = Date.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, application::toModelType);
    }

    @Test
    public void toModelType_nullDate_throwsIllegalValueException() {
        JsonAdaptedApplication application = new JsonAdaptedApplication(VALID_COMPANY_NAME, VALID_ROLE, VALID_EMAIL,
                VALID_WEBSITE, VALID_ADDRESS, null, VALID_STATUS, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, application::toModelType);
    }

    @Test
    public void toModelType_invalidStatus_throwsIllegalValueException() {
        JsonAdaptedApplication application =
                new JsonAdaptedApplication(VALID_COMPANY_NAME, VALID_ROLE, VALID_EMAIL, VALID_WEBSITE,
                        VALID_ADDRESS, VALID_DATE, INVALID_STATUS, VALID_TAGS);
        String expectedMessage = Status.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, application::toModelType);
    }

    @Test
    public void toModelType_nullStatus_throwsIllegalValueException() {
        JsonAdaptedApplication application = new JsonAdaptedApplication(VALID_COMPANY_NAME, VALID_ROLE, VALID_EMAIL,
                VALID_WEBSITE, VALID_ADDRESS, VALID_DATE, null, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Status.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, application::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedApplication application =
                new JsonAdaptedApplication(VALID_COMPANY_NAME, VALID_ROLE, VALID_EMAIL, VALID_WEBSITE,
                        VALID_ADDRESS, VALID_DATE, VALID_STATUS, invalidTags);
        assertThrows(IllegalValueException.class, application::toModelType);
    }

}

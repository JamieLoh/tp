package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_COMPANY_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ROLE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_STATUS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_WEBSITE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.ROLE_DESC_BACKEND_DEVELOPER;
import static seedu.address.logic.commands.CommandTestUtil.ROLE_DESC_FRONTEND_DEVELOPER;
import static seedu.address.logic.commands.CommandTestUtil.STATUS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.STATUS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COMPANY_NAME_BMW;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROLE_BACKEND_DEVELOPER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STATUS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.WEBSITE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.WEBSITE_DESC_BOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEBSITE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalApplications.AMY;
import static seedu.address.testutil.TypicalApplications.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddCommand;
import seedu.address.model.application.Address;
import seedu.address.model.application.Application;
import seedu.address.model.application.CompanyName;
import seedu.address.model.application.Date;
import seedu.address.model.application.Email;
import seedu.address.model.application.Role;
import seedu.address.model.application.Status;
import seedu.address.model.application.Website;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.ApplicationBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Application expectedApplication = new ApplicationBuilder(BOB).withTags(VALID_TAG_FRIEND).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB
                + ROLE_DESC_BACKEND_DEVELOPER + EMAIL_DESC_BOB + WEBSITE_DESC_BOB
                + ADDRESS_DESC_BOB + DATE_DESC_BOB + STATUS_DESC_BOB + TAG_DESC_FRIEND,
                new AddCommand(expectedApplication));

        // multiple tags - all accepted
        Application expectedApplicationMultipleTags = new ApplicationBuilder(BOB)
                .withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
                .build();
        assertParseSuccess(parser,
                NAME_DESC_BOB + ROLE_DESC_BACKEND_DEVELOPER + EMAIL_DESC_BOB
                        + WEBSITE_DESC_BOB + ADDRESS_DESC_BOB + DATE_DESC_BOB + STATUS_DESC_BOB
                        + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                new AddCommand(expectedApplicationMultipleTags));
    }

    @Test
    public void parse_repeatedNonTagValue_failure() {
        String validExpectedApplicationString = NAME_DESC_BOB + ROLE_DESC_BACKEND_DEVELOPER + EMAIL_DESC_BOB
                + WEBSITE_DESC_BOB + ADDRESS_DESC_BOB + DATE_DESC_BOB + STATUS_DESC_BOB + TAG_DESC_FRIEND;

        // multiple names
        assertParseFailure(parser, NAME_DESC_AMY + validExpectedApplicationString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // multiple roles
        assertParseFailure(parser, ROLE_DESC_FRONTEND_DEVELOPER + validExpectedApplicationString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ROLE));

        // multiple emails
        assertParseFailure(parser, EMAIL_DESC_AMY + validExpectedApplicationString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // multiple websites
        assertParseFailure(parser, WEBSITE_DESC_AMY + validExpectedApplicationString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_WEBSITE));

        // multiple addresses
        assertParseFailure(parser, ADDRESS_DESC_AMY + validExpectedApplicationString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));

        // multiple dates
        assertParseFailure(parser, DATE_DESC_AMY + validExpectedApplicationString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DATE));

        // multiple statuses
        assertParseFailure(parser, STATUS_DESC_AMY + validExpectedApplicationString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_STATUS));

        // multiple fields repeated
        assertParseFailure(parser,
                ROLE_DESC_FRONTEND_DEVELOPER + EMAIL_DESC_AMY + NAME_DESC_AMY
                        + WEBSITE_DESC_AMY + ADDRESS_DESC_AMY + DATE_DESC_AMY + STATUS_DESC_AMY
                        + validExpectedApplicationString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME, PREFIX_ADDRESS,
                        PREFIX_WEBSITE, PREFIX_EMAIL, PREFIX_ROLE, PREFIX_DATE, PREFIX_STATUS));

        // invalid value followed by valid value

        // invalid name
        assertParseFailure(parser, INVALID_COMPANY_NAME_DESC + validExpectedApplicationString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid email
        assertParseFailure(parser, INVALID_EMAIL_DESC + validExpectedApplicationString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // invalid role
        assertParseFailure(parser, INVALID_ROLE_DESC + validExpectedApplicationString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ROLE));

        // invalid website
        assertParseFailure(parser, validExpectedApplicationString + INVALID_WEBSITE_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_WEBSITE));

        // invalid address
        assertParseFailure(parser, INVALID_ADDRESS_DESC + validExpectedApplicationString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));

        // invalid date
        assertParseFailure(parser, INVALID_DATE_DESC + validExpectedApplicationString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DATE));

        // invalid status
        assertParseFailure(parser, INVALID_STATUS_DESC + validExpectedApplicationString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_STATUS));

        // valid value followed by invalid value

        // invalid name
        assertParseFailure(parser, validExpectedApplicationString + INVALID_COMPANY_NAME_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid email
        assertParseFailure(parser, validExpectedApplicationString + INVALID_EMAIL_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // invalid role
        assertParseFailure(parser, validExpectedApplicationString + INVALID_ROLE_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ROLE));

        // invalid address
        assertParseFailure(parser, validExpectedApplicationString + INVALID_ADDRESS_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));

        // invalid date
        assertParseFailure(parser, validExpectedApplicationString + INVALID_DATE_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DATE));

        // invalid status
        assertParseFailure(parser, validExpectedApplicationString + INVALID_STATUS_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_STATUS));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Application expectedApplication = new ApplicationBuilder(AMY).withTags().build();
        assertParseSuccess(parser, NAME_DESC_AMY + ROLE_DESC_FRONTEND_DEVELOPER + EMAIL_DESC_AMY
                        + WEBSITE_DESC_AMY + ADDRESS_DESC_AMY + DATE_DESC_AMY + STATUS_DESC_AMY,
                new AddCommand(expectedApplication));

        // missing email
        Application expectedApplicationNoEmail = new ApplicationBuilder(BOB).withEmail(null).withTags().build();
        assertParseSuccess(parser, NAME_DESC_BOB + ROLE_DESC_BACKEND_DEVELOPER
                        + WEBSITE_DESC_BOB + ADDRESS_DESC_BOB + DATE_DESC_BOB + STATUS_DESC_BOB,
                new AddCommand(expectedApplicationNoEmail));

        // missing website
        Application expectedApplicationNoWebsite = new ApplicationBuilder(BOB).withWebsite(null).withTags().build();
        assertParseSuccess(parser, NAME_DESC_BOB + ROLE_DESC_BACKEND_DEVELOPER
                       + ADDRESS_DESC_BOB + DATE_DESC_BOB + STATUS_DESC_BOB + EMAIL_DESC_BOB,
                new AddCommand(expectedApplicationNoWebsite));
        // missing address
        Application expectedApplicationNoAddress = new ApplicationBuilder(BOB).withAddress(null).withTags().build();
        assertParseSuccess(parser, NAME_DESC_BOB + ROLE_DESC_BACKEND_DEVELOPER + EMAIL_DESC_BOB
                        + WEBSITE_DESC_BOB + DATE_DESC_BOB + STATUS_DESC_BOB,
                new AddCommand(expectedApplicationNoAddress));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_COMPANY_NAME_BMW + ROLE_DESC_BACKEND_DEVELOPER + EMAIL_DESC_BOB
                        + WEBSITE_DESC_BOB + ADDRESS_DESC_BOB + DATE_DESC_BOB + STATUS_DESC_BOB,
                expectedMessage);

        // missing role prefix
        assertParseFailure(parser, NAME_DESC_BOB + VALID_ROLE_BACKEND_DEVELOPER + EMAIL_DESC_BOB
                        + WEBSITE_DESC_BOB + ADDRESS_DESC_BOB + DATE_DESC_BOB + STATUS_DESC_BOB,
                expectedMessage);

        // missing date prefix
        assertParseFailure(parser, NAME_DESC_BOB + ROLE_DESC_BACKEND_DEVELOPER + EMAIL_DESC_BOB
                        + WEBSITE_DESC_BOB + ADDRESS_DESC_BOB + VALID_DATE_BOB + STATUS_DESC_BOB,
                expectedMessage);

        // missing status prefix
        assertParseFailure(parser, NAME_DESC_BOB + ROLE_DESC_BACKEND_DEVELOPER + EMAIL_DESC_BOB
                        + WEBSITE_DESC_BOB + ADDRESS_DESC_BOB + DATE_DESC_BOB + VALID_STATUS_BOB,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_COMPANY_NAME_BMW + VALID_ROLE_BACKEND_DEVELOPER + VALID_EMAIL_BOB
                        + WEBSITE_DESC_BOB + VALID_ADDRESS_BOB,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser,
                INVALID_COMPANY_NAME_DESC + ROLE_DESC_BACKEND_DEVELOPER
                        + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + WEBSITE_DESC_BOB + DATE_DESC_BOB + STATUS_DESC_BOB
                        + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                CompanyName.MESSAGE_CONSTRAINTS);

        // invalid role
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_ROLE_DESC + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + WEBSITE_DESC_BOB + DATE_DESC_BOB + STATUS_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Role.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, NAME_DESC_BOB + ROLE_DESC_BACKEND_DEVELOPER
                + INVALID_EMAIL_DESC + ADDRESS_DESC_BOB + WEBSITE_DESC_BOB + DATE_DESC_BOB + STATUS_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Email.MESSAGE_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, NAME_DESC_BOB + ROLE_DESC_BACKEND_DEVELOPER
                + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC + WEBSITE_DESC_BOB + DATE_DESC_BOB + STATUS_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Address.MESSAGE_CONSTRAINTS);

        // invalid website
        assertParseFailure(parser, NAME_DESC_BOB + ROLE_DESC_BACKEND_DEVELOPER
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + INVALID_WEBSITE_DESC + DATE_DESC_BOB + STATUS_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Website.MESSAGE_CONSTRAINTS);

        // invalid date
        assertParseFailure(parser, NAME_DESC_BOB + ROLE_DESC_BACKEND_DEVELOPER
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + WEBSITE_DESC_BOB + INVALID_DATE_DESC + STATUS_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Date.MESSAGE_CONSTRAINTS);

        // invalid status
        assertParseFailure(parser, NAME_DESC_BOB + ROLE_DESC_BACKEND_DEVELOPER
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + WEBSITE_DESC_BOB + DATE_DESC_BOB + INVALID_STATUS_DESC
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Status.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_BOB + ROLE_DESC_BACKEND_DEVELOPER
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + WEBSITE_DESC_BOB + DATE_DESC_BOB + STATUS_DESC_BOB
                + INVALID_TAG_DESC + VALID_TAG_FRIEND, Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_COMPANY_NAME_DESC + ROLE_DESC_BACKEND_DEVELOPER + EMAIL_DESC_BOB
                        + WEBSITE_DESC_BOB + DATE_DESC_BOB + STATUS_DESC_BOB + INVALID_ADDRESS_DESC,
                CompanyName.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + ROLE_DESC_BACKEND_DEVELOPER
                        + EMAIL_DESC_BOB + WEBSITE_DESC_BOB + ADDRESS_DESC_BOB + DATE_DESC_BOB + STATUS_DESC_BOB
                        + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}

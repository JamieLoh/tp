package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
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
 * Jackson-friendly version of {@link Application}.
 */
class JsonAdaptedApplication {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Application's %s field is missing!";

    private final String companyName;
    private final String role;
    private final String email;
    private final String website;
    private final String date;
    private final String address;
    private final String status;
    private final String notes;
    private final List<JsonAdaptedTag> tags = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedApplication} with the given application details.
     */
    @JsonCreator
    public JsonAdaptedApplication(@JsonProperty("companyName") String companyName, @JsonProperty("role") String role,
                                  @JsonProperty("email") String email, @JsonProperty("website") String website,
                                  @JsonProperty("address") String address, @JsonProperty("date") String date,
                                  @JsonProperty("status") String status, @JsonProperty("notes") String notes,
                                  @JsonProperty("tags") List<JsonAdaptedTag> tags) {
        this.companyName = companyName;
        this.role = role;
        this.email = email;
        this.website = website;
        this.address = address;
        this.date = date;
        this.status = status;
        this.notes = notes;
        if (tags != null) {
            this.tags.addAll(tags);
        }
    }

    /**
     * Converts a given {@code Application} into this class for Jackson use.
     */
    public JsonAdaptedApplication(Application source) {
        companyName = source.getCompanyName().fullCompanyName;
        role = source.getRole().value;
        email = source.getEmail() == null ? null : source.getEmail().value;
        website = source.getWebsite() == null ? null : source.getWebsite().websiteName;
        address = source.getAddress() == null ? null : source.getAddress().value;
        date = source.getDate().value;
        status = source.getStatus().toString();
        notes = source.getNotes();
        tags.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted application object into the model's {@code Application} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted application.
     */
    public Application toModelType() throws IllegalValueException {
        final List<Tag> applicationTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tags) {
            applicationTags.add(tag.toModelType());
        }

        if (companyName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    CompanyName.class.getSimpleName()));
        }
        if (!CompanyName.isValidCompanyName(companyName)) {
            throw new IllegalValueException(CompanyName.MESSAGE_CONSTRAINTS);
        }
        final CompanyName modelName = new CompanyName(companyName);

        if (role == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Role.class.getSimpleName()));
        }
        if (!Role.isValidRole(role)) {
            throw new IllegalValueException(Role.MESSAGE_CONSTRAINTS);
        }
        final Role modelRole = new Role(role);

        final Email modelEmail;
        if (email == null) {
            modelEmail = null;
        } else {
            if (!Email.isValidEmail(email)) {
                throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
            }
            modelEmail = new Email(email);
        }

        final Website modelWebsite;
        if (website == null) {
            modelWebsite = null;
        } else {
            if (!Website.isValidWebsite(website)) {
                throw new IllegalValueException(Website.MESSAGE_CONSTRAINTS);
            }
            modelWebsite = new Website(website);
        }

        final Address modelAddress;
        if (address == null) {
            modelAddress = null;
        } else {

            if (!Address.isValidAddress(address)) {
                throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
            }
            modelAddress = new Address(address);
        }

        if (date == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName()));
        }
        if (!Date.isValidDate(date)) {
            throw new IllegalValueException(Date.MESSAGE_CONSTRAINTS);
        }
        final Date modelDate = new Date(date);

        if (status == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Status.class.getSimpleName()));
        }
        if (!Status.isValidStatus(status)) {
            throw new IllegalValueException(Status.MESSAGE_CONSTRAINTS);
        }
        final Status modelStatus = new Status(status);

        final String modelNotes = notes == null ? "" : notes;

        final Set<Tag> modelTags = new HashSet<>(applicationTags);
        return new Application(
                modelName, modelRole, modelEmail, modelWebsite, modelAddress, modelDate, modelStatus,
                modelTags, modelNotes
        );
    }

}

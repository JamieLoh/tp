package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEBSITE;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
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
 * Edits the details of an existing application in the address book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the application identified "
            + "by the index number used in the displayed application list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "COMPANY_NAME] "
            + "[" + PREFIX_ROLE + "ROLE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_WEBSITE + "WEBSITE] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_DATE + "DATE] "
            + "[" + PREFIX_STATUS + "STATUS]\n"
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_ROLE + "Backend Developer Intern "
            + PREFIX_EMAIL + "johndoe@example.com";

    public static final String MESSAGE_EDIT_APPLICATION_SUCCESS = "Edited Application: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_APPLICATION = "This application already exists in the address book.";

    private final Index index;
    private final EditApplicationDescriptor editApplicationDescriptor;

    /**
     * @param index of the application in the filtered application list to edit
     * @param editApplicationDescriptor details to edit the application with
     */
    public EditCommand(Index index, EditApplicationDescriptor editApplicationDescriptor) {
        requireNonNull(index);
        requireNonNull(editApplicationDescriptor);

        this.index = index;
        this.editApplicationDescriptor = new EditApplicationDescriptor(editApplicationDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Application> lastShownList = model.getFilteredApplicationList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_APPLICATION_DISPLAYED_INDEX);
        }

        Application applicationToEdit = lastShownList.get(index.getZeroBased());
        Application editedApplication = createEditedApplication(applicationToEdit, editApplicationDescriptor);

        if (!applicationToEdit.isSameApplication(editedApplication) && model.hasApplication(editedApplication)) {
            throw new CommandException(MESSAGE_DUPLICATE_APPLICATION);
        }

        model.setApplication(applicationToEdit, editedApplication);
        model.updateFilteredApplicationList(model.getFilteredApplicationListPredicate());
        return new CommandResult(String.format(MESSAGE_EDIT_APPLICATION_SUCCESS, Messages.format(editedApplication)));
    }

    /**
     * Creates and returns a {@code Application} with the details of {@code applicationToEdit}
     * edited with {@code editApplicationDescriptor}.
     */
    private static Application createEditedApplication(Application applicationToEdit,
                                                       EditApplicationDescriptor editApplicationDescriptor) {
        assert applicationToEdit != null;

        CompanyName updatedName = editApplicationDescriptor.getCompanyName().orElse(applicationToEdit.getCompanyName());
        Role updatedRole = editApplicationDescriptor.getRole().orElse(applicationToEdit.getRole());
        Date updatedDate = editApplicationDescriptor.getDate().orElse(applicationToEdit.getDate());
        Status updatedStatus = editApplicationDescriptor.getStatus().orElse(applicationToEdit.getStatus());
        Set<Tag> updatedTags = editApplicationDescriptor.getTags().orElse(applicationToEdit.getTags());
        Email updatedEmail = applicationToEdit.getEmail();
        if (editApplicationDescriptor.isEmailEdited()) {
            updatedEmail = editApplicationDescriptor.getEmail().orElse(null);
        }

        Website updatedWebsite = applicationToEdit.getWebsite();
        if (editApplicationDescriptor.isWebsiteEdited()) {
            updatedWebsite = editApplicationDescriptor.getWebsite().orElse(null);
        }

        Address updatedAddress = applicationToEdit.getAddress();
        if (editApplicationDescriptor.isAddressEdited()) {
            updatedAddress = editApplicationDescriptor.getAddress().orElse(null);
        }

        return new Application(updatedName, updatedRole, updatedEmail,
                updatedWebsite, updatedAddress, updatedDate, updatedStatus, updatedTags,
                applicationToEdit.getNotes(), applicationToEdit.isArchived());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        EditCommand otherEditCommand = (EditCommand) other;
        return index.equals(otherEditCommand.index)
                && editApplicationDescriptor.equals(otherEditCommand.editApplicationDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("editApplicationDescriptor", editApplicationDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the application with. Each non-empty field value will replace the
     * corresponding field value of the application.
     */
    public static class EditApplicationDescriptor {
        private CompanyName companyName;
        private Role role;
        private Email email;
        private Website website;
        private Address address;
        private Date date;
        private Status status;
        private Set<Tag> tags;
        private boolean isEmailEdited = false;
        private boolean isWebsiteEdited = false;
        private boolean isAddressEdited = false;

        public EditApplicationDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditApplicationDescriptor(EditApplicationDescriptor toCopy) {
            setCompanyName(toCopy.companyName);
            setRole(toCopy.role);
            setDate(toCopy.date);
            setStatus(toCopy.status);
            setTags(toCopy.tags);

            if (toCopy.isEmailEdited) {
                setEmail(toCopy.email);
            }
            if (toCopy.isWebsiteEdited) {
                setWebsite(toCopy.website);
            }
            if (toCopy.isAddressEdited) {
                setAddress(toCopy.address);
            }
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(companyName, role, email, website, address, date, status, tags)
                    || isEmailEdited
                    || isWebsiteEdited
                    || isAddressEdited;
        }

        public boolean isEmailEdited() {
            return isEmailEdited;
        }

        public boolean isWebsiteEdited() {
            return isWebsiteEdited;
        }

        public boolean isAddressEdited() {
            return isAddressEdited;
        }

        public void setCompanyName(CompanyName name) {
            this.companyName = name;
        }

        public Optional<CompanyName> getCompanyName() {
            return Optional.ofNullable(companyName);
        }

        public void setRole(Role role) {
            this.role = role;
        }

        public Optional<Role> getRole() {
            return Optional.ofNullable(role);
        }

        public void setEmail(Email email) {
            this.email = email;
            isEmailEdited = true;
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        public void setWebsite(Website website) {
            this.website = website;
            isWebsiteEdited = true;
        }

        public Optional<Website> getWebsite() {
            return Optional.ofNullable(website);
        }

        public void setAddress(Address address) {
            this.address = address;
            isAddressEdited = true;
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public Optional<Date> getDate() {
            return Optional.ofNullable(date);
        }

        public void setStatus(Status status) {
            this.status = status;
        }

        public Optional<Status> getStatus() {
            return Optional.ofNullable(status);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditApplicationDescriptor)) {
                return false;
            }

            EditApplicationDescriptor otherEditApplicationDescriptor = (EditApplicationDescriptor) other;
            return Objects.equals(companyName, otherEditApplicationDescriptor.companyName)
                    && Objects.equals(role, otherEditApplicationDescriptor.role)
                    && Objects.equals(email, otherEditApplicationDescriptor.email)
                    && Objects.equals(website, otherEditApplicationDescriptor.website)
                    && Objects.equals(address, otherEditApplicationDescriptor.address)
                    && Objects.equals(date, otherEditApplicationDescriptor.date)
                    && Objects.equals(status, otherEditApplicationDescriptor.status)
                    && Objects.equals(tags, otherEditApplicationDescriptor.tags);
        }

        @Override
        public String toString() {
            ToStringBuilder builder = new ToStringBuilder(this)
                    .add("companyName", companyName)
                    .add("role", role)
                    .add("date", date)
                    .add("status", status)
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
}

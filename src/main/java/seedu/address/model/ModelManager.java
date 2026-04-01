package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.application.Application;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Application> filteredApplications;
    private Predicate<Application> currentApplicationPredicate;
    private Application selectedNotesApplication;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredApplications = new FilteredList<>(this.addressBook.getApplicationList());
        currentApplicationPredicate = PREDICATE_SHOW_UNARCHIVED_APPLICATIONS;
        filteredApplications.setPredicate(currentApplicationPredicate);
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public boolean hasApplication(Application application) {
        requireNonNull(application);
        return addressBook.hasApplication(application);
    }

    @Override
    public void deleteApplication(Application target) {
        addressBook.removeApplication(target);
    }

    @Override
    public void addApplication(Application application) {
        addressBook.addApplication(application);
        updateFilteredApplicationList(PREDICATE_SHOW_UNARCHIVED_APPLICATIONS);
    }

    @Override
    public void setApplication(Application target, Application editedApplication) {
        requireAllNonNull(target, editedApplication);

        addressBook.setApplication(target, editedApplication);
    }

    @Override
    public void editApplicationNotes(Application target) {
        requireNonNull(target);
        this.selectedNotesApplication = target;
    }

    @Override
    public void viewApplicationNotes(Application target) {
        requireNonNull(target);
        this.selectedNotesApplication = target;
    }

    @Override
    public Application getSelectedNotesApplication() {
        return selectedNotesApplication;
    }

    @Override
    public void saveApplicationNotes(String notes) {
        requireAllNonNull(notes, selectedNotesApplication);

        Application updatedApplication = new Application(
                selectedNotesApplication.getCompanyName(),
                selectedNotesApplication.getRole(),
                selectedNotesApplication.getEmail(),
                selectedNotesApplication.getWebsite(),
                selectedNotesApplication.getAddress(),
                selectedNotesApplication.getDate(),
                selectedNotesApplication.getStatus(),
                selectedNotesApplication.getTags(),
                notes
        );
        addressBook.setApplication(selectedNotesApplication, updatedApplication);
        selectedNotesApplication = updatedApplication;
        updateFilteredApplicationList(currentApplicationPredicate);
    }

    //=========== Filtered Application List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Application} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Application> getFilteredApplicationList() {
        return filteredApplications;
    }

    @Override
    public Predicate<Application> getFilteredApplicationListPredicate() {
        return currentApplicationPredicate;
    }

    @Override
    public void updateFilteredApplicationList(Predicate<Application> predicate) {
        requireNonNull(predicate);
        currentApplicationPredicate = predicate;
        filteredApplications.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModelManager)) {
            return false;
        }

        ModelManager otherModelManager = (ModelManager) other;
        return addressBook.equals(otherModelManager.addressBook)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredApplications.equals(otherModelManager.filteredApplications);
    }

}

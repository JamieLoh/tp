package seedu.address.testutil;

import java.nio.file.Files;
import java.nio.file.Path;

import javafx.stage.Stage;
import seedu.address.logic.Logic;
import seedu.address.logic.LogicManager;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.storage.JsonAddressBookStorage;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.StorageManager;
import seedu.address.ui.UiManager;

/**
 * Minimal test application that starts the real UI with typical test data.
 * This avoids running MainApp.init() which touches real files and config.
 */
public class TestApp extends javafx.application.Application {

    private UiManager ui;
    private Logic logic;
    private Model model;

    @Override
    public void start(Stage primaryStage) throws Exception {
        model = new ModelManager(TypicalApplications.getTypicalAddressBook(), new UserPrefs());

        Path tempDir = Files.createTempDirectory("testui");
        JsonAddressBookStorage addressBookStorage =
                new JsonAddressBookStorage(tempDir.resolve("addressBook.json"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(tempDir.resolve("userprefs.json"));
        StorageManager storage = new StorageManager(addressBookStorage, userPrefsStorage);

        logic = new LogicManager(model, storage);

        ui = new UiManager(logic);
        ui.start(primaryStage);
    }
}

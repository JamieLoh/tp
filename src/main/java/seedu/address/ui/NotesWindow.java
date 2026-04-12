package seedu.address.ui;

import java.util.function.Function;
import java.util.logging.Logger;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.util.Duration;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.NotesSaveStatus;

/**
 * Controller for the notes window.
 * Supports both read-only viewing and editing of application notes.
 */
public class NotesWindow extends UiPart<Stage> {

    private static final Logger logger = LogsCenter.getLogger(NotesWindow.class);
    private static final String FXML = "NotesWindow.fxml";
    private static final int NOTES_WINDOW_WIDTH = 500;
    private static final int NOTES_WINDOW_HEIGHT = 400;

    private Function<String, NotesSaveStatus> saveCallback;
    private boolean isEditMode;
    private PauseTransition currentPause;

    @FXML
    private ScrollPane viewPane;

    @FXML
    private TextFlow notesMessage;

    @FXML
    private TextArea notesTextArea;

    @FXML
    private Button saveButton;

    /**
     * Creates a new NotesWindow.
     *
     * @param root Stage to use as the root of the NotesWindow.
     */
    public NotesWindow(Stage root) {
        super(FXML, root);
        root.setMinWidth(NOTES_WINDOW_WIDTH);
        root.setMinHeight(NOTES_WINDOW_HEIGHT);
        root.setWidth(NOTES_WINDOW_WIDTH);
        root.setHeight(NOTES_WINDOW_HEIGHT);

        KeyCombination saveShortcut = new KeyCodeCombination(KeyCode.S, KeyCombination.SHORTCUT_DOWN);
        root.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (saveShortcut.match(event)) {
                handleSave();
                event.consume();
            }
        });
    }

    /**
     * Creates a new NotesWindow.
     */
    public NotesWindow() {
        this(new Stage());
    }

    /**
     * Sets up the window in read-only mode displaying the given notes.
     */
    public void setViewMode(String notes, String companyName) {
        cancelPendingPause();
        isEditMode = false;
        updateTitle(companyName);

        viewPane.setVisible(true);
        viewPane.setManaged(true);
        notesTextArea.setVisible(false);
        notesTextArea.setManaged(false);
        saveButton.setVisible(false);
        saveButton.setManaged(false);

        String displayText = (notes == null || notes.isEmpty()) ? "(No notes yet)" : notes;
        notesMessage.getChildren().setAll(new Text(displayText));
    }

    /**
     * Sets up the window in edit mode with the given notes and save callback.
     */
    public void setEditMode(String notes, Function<String, NotesSaveStatus> saveCallback, String companyName) {
        cancelPendingPause();
        this.saveCallback = saveCallback;
        this.isEditMode = true;
        updateTitle(companyName);

        viewPane.setVisible(false);
        viewPane.setManaged(false);
        notesTextArea.setVisible(true);
        notesTextArea.setManaged(true);
        saveButton.setVisible(true);
        saveButton.setManaged(true);
        resetSaveButton();

        notesTextArea.setText(notes == null ? "" : notes);
    }

    /**
     * Updates the notes window title to reflect the current company name.
     *
     * @param companyName Name of the application company to display in the window title.
     */
    public void refreshCompanyName(String companyName) {
        this.updateTitle(companyName);
    }

    /**
     * Updates the notes window title based on the current mode.
     * Shows either edit mode or view mode together with the given company name.
     *
     * @param companyName Name of the application company to display in the window title.
     */
    private void updateTitle(String companyName) {
        if (isEditMode) {
            getRoot().setTitle("Notes (Edit) - " + companyName);
        } else {
            getRoot().setTitle("Notes (View) - " + companyName);
        }
    }

    /**
     * Handles the save button click.
     */
    @FXML
    private void handleSave() {
        if (saveCallback == null || !isEditMode || saveButton.isDisabled()) {
            return;
        }

        NotesSaveStatus saveStatus = saveCallback.apply(notesTextArea.getText());

        if (saveStatus == NotesSaveStatus.APPLICATION_UNAVAILABLE) {
            showApplicationUnavailableAndClose();
            return;
        }

        if (saveStatus == NotesSaveStatus.STORAGE_FAILURE) {
            showSaveFailure();
            schedulePause(Duration.seconds(1.5), event -> resetSaveButton());
            return;
        }

        showSaveSuccess();
        schedulePause(Duration.seconds(1.5), event -> resetSaveButton());
    }

    /**
     * Reset button appearance to original.
     */
    private void resetSaveButton() {
        saveButton.getStyleClass().remove("danger");
        saveButton.setText("Save");
        saveButton.setDisable(false);
    }

    /**
     * Sequence to show save action is success.
     */
    private void showSaveSuccess() {
        saveButton.setText("Saved!");
        saveButton.setDisable(true);
        logger.fine("Notes saved.");
    }

    /**
     * Show save failure when save is unsuccessful.
     */
    private void showSaveFailure() {
        getRoot().setTitle("Notes (Save Failed)");
        saveButton.setText("Save failed");
        if (!saveButton.getStyleClass().contains("danger")) {
            saveButton.getStyleClass().add("danger");
        }
        saveButton.setDisable(true);
        logger.warning("Failed to save notes.");
    }

    /**
     * Shows that the currently opened application is no longer available, then closes the window.
     */
    public void showApplicationUnavailableAndClose() {
        getRoot().setTitle("Notes (Unavailable)");
        saveButton.setVisible(true);
        saveButton.setManaged(true);
        saveButton.setText("Application deleted");
        if (!saveButton.getStyleClass().contains("danger")) {
            saveButton.getStyleClass().add("danger");
        }
        saveButton.setDisable(true);
        logger.warning("Closing notes window because the selected application no longer exists.");
        schedulePause(Duration.seconds(1.5), event -> hide());
    }

    /**
     * Cancels any pending pause transition to prevent stale callbacks from firing.
     */
    private void cancelPendingPause() {
        if (currentPause != null) {
            currentPause.stop();
            currentPause = null;
        }
    }

    /**
     * Set pause on certain component to show notification before closing window.
     */
    private void schedulePause(Duration duration, EventHandler<ActionEvent> onFinished) {
        cancelPendingPause();
        currentPause = new PauseTransition(duration);
        currentPause.setOnFinished(onFinished);
        currentPause.play();
    }

    /**
     * Shows the notes window.
     */
    public void show() {
        logger.fine("Showing notes window.");
        Stage stage = getRoot();

        if (!stage.isShowing()) {
            stage.show();
        }

        stage.setIconified(false);
        stage.toFront();
        stage.requestFocus();
    }

    /**
     * Returns true if the notes window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the notes window.
     */
    public void hide() {
        cancelPendingPause();
        getRoot().hide();
    }

    /**
     * Focuses on the notes window.
     */
    public void focus() {
        getRoot().requestFocus();
    }
}

package seedu.address.ui;

import java.util.function.Consumer;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;

/**
 * Controller for the notes window.
 * Supports both read-only viewing and editing of application notes.
 */
public class NotesWindow extends UiPart<Stage> {

    private static final Logger logger = LogsCenter.getLogger(NotesWindow.class);
    private static final String FXML = "NotesWindow.fxml";

    private Consumer<String> saveCallback;

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
        root.setMinWidth(500);
        root.setMinHeight(400);
        root.setWidth(500);
        root.setHeight(400);
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
    public void setViewMode(String notes) {
        getRoot().setTitle("Notes (View)");
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
    public void setEditMode(String notes, Consumer<String> saveCallback) {
        this.saveCallback = saveCallback;
        getRoot().setTitle("Notes (Edit)");
        viewPane.setVisible(false);
        viewPane.setManaged(false);
        notesTextArea.setVisible(true);
        notesTextArea.setManaged(true);
        saveButton.setVisible(true);
        saveButton.setManaged(true);

        notesTextArea.setText(notes == null ? "" : notes);
    }

    /**
     * Handles the save button click.
     */
    @FXML
    private void handleSave() {
        if (saveCallback != null) {
            saveCallback.accept(notesTextArea.getText());
            logger.fine("Notes saved.");
        }
    }

    /**
     * Shows the notes window.
     */
    public void show() {
        logger.fine("Showing notes window.");
        getRoot().show();
        getRoot().centerOnScreen();
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
        getRoot().hide();
    }

    /**
     * Focuses on the notes window.
     */
    public void focus() {
        getRoot().requestFocus();
    }
}

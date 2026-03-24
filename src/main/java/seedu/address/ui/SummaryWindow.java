package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;

/**
 * Controller for the summary window.
 */
public class SummaryWindow extends UiPart<Stage> {

    private static final Logger logger = LogsCenter.getLogger(SummaryWindow.class);
    private static final String FXML = "SummaryWindow.fxml";

    @FXML
    private TextFlow summaryMessage;

    /**
     * Creates a new SummaryWindow.
     *
     * @param root Stage to use as the root of the SummaryWindow.
     */
    public SummaryWindow(Stage root) {
        super(FXML, root);
    }

    /**
     * Creates a new SummaryWindow.
     */
    public SummaryWindow() {
        this(new Stage());
    }

    /**
     * Updates the content displayed in the summary window.
     */
    public void setContent(String content) {
        summaryMessage.getChildren().setAll(new Text(content));
    }

    /**
     * Shows the summary window.
     */
    public void show() {
        logger.fine("Showing summary window.");
        getRoot().setWidth(400);
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the summary window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the summary window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the summary window.
     */
    public void focus() {
        getRoot().requestFocus();
    }
}

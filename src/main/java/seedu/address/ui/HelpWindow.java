package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;

/**
 * Controller for a help page
 */
public class HelpWindow extends UiPart<Stage> {

    public static final String HELP_MESSAGE = "HireME – Command Help\n\n"
            + "Track your internship applications using the commands below.\n\n"
            + "add: Add a new application.\n"
            + "  Format: add n/COMPANY_NAME r/ROLE d/DATE s/STATUS [e/EMAIL] [w/WEBSITE] [a/ADDRESS] [t/TAG]...\n"
            + "  Status must be Pending, Offered, or Rejected. Date must be DD-MM-YYYY.\n"
            + "  Example: add n/Google r/Software Engineer d/19-02-2026 s/Pending e/hr@gmail.com\n\n"
            + "edit: Update an application by its list number.\n"
            + "  Format: edit INDEX [n/COMPANY_NAME] [r/ROLE] [e/EMAIL] [w/WEBSITE] [a/ADDRESS] [d/DATE] "
            + "[s/STATUS] [t/TAG]...\n"
            + "  At least one field must be provided.\n"
            + "  Example: edit 1 r/Backend Developer Intern e/johndoe@gmail.com\n\n"
            + "delete: Remove an application by its list number.\n"
            + "  Format: delete INDEX\n"
            + "  Example: delete 1\n\n"
            + "find: Find applications by field (case-insensitive, partial match).\n"
            + "  Format: find [n/NAME] [r/ROLE] [e/EMAIL] [w/WEBSITE] [a/ADDRESS] [d/DATE] [s/STATUS] [t/TAG]...\n"
            + "  At least one search field must be provided.\n"
            + "  Example: find n/Google r/Backend Developer s/Pending\n\n"
            + "archive: Archive an application from the current list.\n"
            + "  Format: archive INDEX\n"
            + "  Archived applications are hidden from the normal list.\n"
            + "  Example: archive 1\n\n"
            + "unarchive: Restore an application from the archived list.\n"
            + "  Format: unarchive INDEX\n"
            + "  Use command 'list archived' first, then unarchive the shown index.\n"
            + "  Example: unarchive 1\n\n"
            + "open: Open the notes for an application.\n"
            + "  Format: open INDEX [m/CHOICE_OF_EDIT]\n"
            + "  m/ must be true or false. Defaults to false (view only).\n"
            + "  Example: open 1 m/true\n\n"
            + "summary: Show a summary of application statistics.\n\n"
            + "list: Display unarchived applications.\n"
            + "  Format: list [archived]\n"
            + "  Use list archived to show only archived applications.\n"
            + "  Example: list archived\n\n"
            + "clear: Remove all applications.\n\n"
            + "help: Show this help message.\n\n"
            + "exit: Exit the program.";

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "HelpWindow.fxml";

    @FXML
    private TextFlow helpMessage;

    /**
     * Creates a new HelpWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public HelpWindow(Stage root) {
        super(FXML, root);
        helpMessage.getChildren().setAll(new Text(HELP_MESSAGE));
    }

    /**
     * Creates a new HelpWindow.
     */
    public HelpWindow() {
        this(new Stage());
    }

    /**
     * Shows the help window.
     * @throws IllegalStateException
     *     <ul>
     *         <li>
     *             if this method is called on a thread other than the JavaFX Application Thread.
     *         </li>
     *         <li>
     *             if this method is called during animation or layout processing.
     *         </li>
     *         <li>
     *             if this method is called on the primary stage.
     *         </li>
     *         <li>
     *             if {@code dialogStage} is already showing.
     *         </li>
     *     </ul>
     */
    public void show() {
        logger.fine("Showing help page about the application.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the help window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the help window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the help window.
     */
    public void focus() {
        getRoot().requestFocus();
    }
}

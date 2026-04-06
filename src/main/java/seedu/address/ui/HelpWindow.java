package seedu.address.ui;

import java.util.ArrayList;
import java.util.List;
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

    public static final String HELP_MESSAGE = """
            HireME – Command Help
            
            Track your internship applications using the commands below.
            
            add: Add a new application.
              Format: add n/COMPANY_NAME r/ROLE d/DATE s/STATUS [e/EMAIL] [w/WEBSITE] [a/ADDRESS] [t/TAG]...
              Status must be Pending, Offered, or Rejected. Date must be DD-MM-YYYY.
              Example: add n/Google r/Software Engineer d/19-02-2026 s/Pending e/hr@gmail.com
            
            edit: Update an application by its list number.
              Format: edit INDEX [n/COMPANY_NAME] [r/ROLE] [e/EMAIL] [w/WEBSITE] [a/ADDRESS] [d/DATE] \
            [s/STATUS] [t/TAG]...
              At least one field must be provided.
              Example: edit 1 r/Backend Developer Intern e/johndoe@gmail.com
            
            delete: Remove an application by its list number.
              Format: delete INDEX
              Example: delete 1
            
            find: Find applications by field (case-insensitive, partial match).
              Format: find [n/NAME] [r/ROLE] [e/EMAIL] [w/WEBSITE] [a/ADDRESS] [d/DATE] [s/STATUS] [t/TAG]...
              At least one search field must be provided.
              Example: find n/Google r/Backend Developer s/Pending
            
            archive: Archive an application from the current list.
              Format: archive INDEX
              Archived applications are hidden from the normal list.
              Example: archive 1
            
            unarchive: Restore an application from the archived list.
              Format: unarchive INDEX
              Use command 'list archived' first, then unarchive the shown index.
              Example: unarchive 1
            
            open: Open the notes for an application.
              Format: open INDEX [m/CHOICE_OF_EDIT]
              m/ must be true or false. Defaults to false (view only).
              Example: open 1 m/true
            
            summary: Show a summary of application statistics.
            
            list: Display unarchived applications.
              Format: list [archived]
              Use list archived to show only archived applications.
              Example: list archived
            
            clear: Remove all applications.
            
            help: Show this help message.

            exit: Exit the program.""";

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
        //helpMessage.getChildren().setAll(new Text(HELP_MESSAGE));
    }

    /**
     * Creates a new HelpWindow.
     */
    public HelpWindow() {
        this(new Stage());
    }

    @FXML
    public void initialize() {
        helpMessage.getChildren().clear();

        helpMessage.getChildren().addAll(
                title(),
                body("Track your internship applications using the commands below.\n\n")
        );

        helpMessage.getChildren().addAll(section("add",
                "Add a new application.",
                "add n/COMPANY_NAME r/ROLE d/DATE s/STATUS [e/EMAIL] [w/WEBSITE] [a/ADDRESS] [t/TAG]...",
                "Status must be Pending, Offered, or Rejected. Date must be DD-MM-YYYY.",
                "add n/Google r/Software Engineer d/19-02-2026 s/Pending e/hr@gmail.com"
        ));

        helpMessage.getChildren().addAll(section("edit",
                "Update an application by its list number.",
                "edit INDEX [n/COMPANY_NAME] [r/ROLE] [e/EMAIL] [w/WEBSITE] [a/ADDRESS] [d/DATE] [s/STATUS] [t/TAG]...",
                "At least one field must be provided.",
                "edit 1 r/Backend Developer Intern e/johndoe@gmail.com"
        ));

        helpMessage.getChildren().addAll(section("delete",
                "Remove an application by its list number.",
                "delete INDEX",
                null,
                "delete 1"
        ));

        helpMessage.getChildren().addAll(section("find",
                "Find applications by field (case-insensitive, partial match).",
                "find [n/NAME] [r/ROLE] [e/EMAIL] [w/WEBSITE] [a/ADDRESS] [d/DATE] [s/STATUS] [t/TAG]...",
                "At least one search field must be provided.",
                "find n/Google r/Backend Developer s/Pending"
        ));

        helpMessage.getChildren().addAll(section("archive",
                "Archive an application from the current list.",
                "archive INDEX",
                "Archived applications are hidden from the normal list.",
                "archive 1"
        ));

        helpMessage.getChildren().addAll(section("unarchive",
                "Restore an application from the archived list.",
                "unarchive INDEX",
                "Use command 'list archived' first, then unarchive the shown index.",
                "unarchive 1"
        ));

        helpMessage.getChildren().addAll(section("open",
                "Open the notes for an application.",
                "open INDEX [m/CHOICE_OF_EDIT]",
                "m/ must be true or false. Defaults to false (view only).",
                "open 1 m/true"
        ));

        helpMessage.getChildren().addAll(section("summary",
                "Show a summary of application statistics.",
                null, null, null
        ));

        helpMessage.getChildren().addAll(section("list",
                "Display unarchived applications.",
                "list [archived]",
                "Use list archived to show only archived applications.",
                "list archived"
        ));

        helpMessage.getChildren().addAll(section("clear",
                "Remove all applications.",
                null, null, null
        ));

        helpMessage.getChildren().addAll(section("help",
                "Show this help message.",
                null, null, null
        ));

        helpMessage.getChildren().addAll(section("exit",
                "Exit the program.",
                null, null, null
        ));
    }

    private Text[] section(String name, String desc, String format, String note, String example) {
        List<Text> texts = new ArrayList<>();

        texts.add(header(name));
        texts.add(body(": " + desc + "\n"));

        if (format != null) {
            texts.add(code("Format: " + format + "\n"));
        }

        if (note != null) {
            texts.add(note("Notes: " + note + "\n"));
        }

        if (example != null) {
            texts.add(example("Example: " + example));
        }

        texts.add(body("\n\n"));

        return texts.toArray(new Text[0]);
    }

    private Text title() {
        Text t = new Text("HireME – Command Help\n\n");
        t.setStyle("-fx-font-size: 30px; -fx-font-weight: bold;");
        return t;
    }
    private Text header(String s) {
        Text t = new Text(s);
        t.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        return t;
    }

    private Text body(String s) {
        return new Text(s);
    }

    private Text code(String s) {
        Text t = new Text(s);
        t.setStyle("-fx-font-family: monospace; -fx-fill: #bbbbbb;");
        return t;
    }

    private Text example(String s) {
        Text t = new Text(s);
        t.setStyle("-fx-fill: #888888;");
        return t;
    }

    private Text note(String s) {
        Text t = new Text("  " + s + "\n");
        t.setStyle("-fx-fill: #aaaaaa;");
        return t;
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

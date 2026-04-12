package seedu.address.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;

/**
 * Controller for a help page
 */
public class HelpWindow extends UiPart<Stage> {
    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "HelpWindow.fxml";
    private static final String TITLE = "#4FC3F7"; // accent blue
    private static final String SUB_TITLE = "#E6E6E6"; // main text
    private static final String TEXT = "#A0A0A0"; // secondary text
    private static final String CODE = "#2A2A2A"; // code background

    @FXML
    private TextFlow helpMessage;

    @FXML
    private ScrollPane scrollPane;

    /**
     * Creates a new HelpWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public HelpWindow(Stage root) {
        super(FXML, root);
        root.setResizable(true);
    }

    /**
     * Creates a new HelpWindow.
     */
    public HelpWindow() {
        this(new Stage());
    }

    /**
     * Initializes the UI components of the help window.
     * This method is automatically called after FXML loading,
     * when all @FXML fields have been injected.
     */
    @FXML
    public void initialize() {
        helpMessage.getChildren().clear();
        TextField userGuideLink = new TextField("https://ay2526s2-cs2103t-w11-3.github.io/tp/UserGuide.html");
        userGuideLink.setEditable(false);
        userGuideLink.setFocusTraversable(false);
        userGuideLink.setStyle("-fx-background-color: transparent; -fx-text-fill: white;");
        userGuideLink.prefWidthProperty().bind(scrollPane.widthProperty().subtract(40));
        helpMessage.getChildren().addAll(
                title(),
                body("For detailed info, check the user guide: \n"), userGuideLink,
                body("Track your internship applications using the commands below.\n\n")
        );
        helpMessage.getChildren().addAll(header("Command format"),
                body(
                        "\n- Words in UPPER_CASE are inputs you provide (e.g. n/Google)\n"
                                + "- [ ] indicates optional fields\n"
                                + "- ... means the field can be repeated\n"
                                + "- Parameters can be in any order\n"
                                + "- Leave a space between each Parameter\n"
                                + "- Duplicate fields (except tags) are not allowed\n"
                                + "- Special characters (including emojis and invisible spaces such as zero-width "
                                + "spaces) are allowed, but they may cause parsing issues and should be used with"
                                + " caution.\n\n"
                ));

        helpMessage.getChildren().addAll(section("add",
                "Add a new application.",
                "add n/COMPANY_NAME r/ROLE d/DATE s/STATUS [e/EMAIL] [w/WEBSITE] [a/ADDRESS] [t/TAG]...",
                "Status must be Pending, Offered, or Rejected. Date must be DD-MM-YYYY.",
                "add n/Google r/Software Engineer d/19-02-2026 s/Pending e/hr@gmail.com t/round1 t/incoming"
        ));

        helpMessage.getChildren().addAll(section("edit",
                "Update an application by its list number.",
                "edit INDEX [n/COMPANY_NAME] [r/ROLE] [e/EMAIL] [w/WEBSITE] "
                        + "[a/ADDRESS] [d/DATE] [s/STATUS] [t/TAG]...",
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
                "m/true opens edit mode. m/false opens view-only mode. Defaults to false (view only)",
                "open 1 m/true"
        ));

        helpMessage.getChildren().addAll(section("list",
                "Display unarchived applications.",
                "list [archived]",
                "Use list archived to show only archived applications.",
                "list archived"
        ));

        helpMessage.getChildren().addAll(section("summary",
                "Show a summary of application statistics.",
                null, null, null
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
        texts.add(body(desc + "\n"));

        if (format != null) {
            texts.add(code(format));
        }

        if (note != null) {
            texts.add(note(note));
        }

        if (example != null) {
            texts.add(example(example));
        }

        texts.add(body("\n\n"));

        return texts.toArray(new Text[0]);
    }

    private Text title() {
        Text t = new Text("HireME – Command Help\n");
        t.setStyle(
                "-fx-font-size: 28px;"
                + "-fx-font-weight: bold;"
                + "-fx-fill: " + TITLE + ";"
        );
        return t;
    }

    private Text header(String s) {
        Text t = new Text(s + ": ");
        t.setStyle(
                "-fx-font-size: 18px;"
                + "-fx-font-weight: bold;"
                + "-fx-fill: " + TITLE + ";"
        );
        return t;
    }

    private Text body(String s) {
        Text t = new Text(s);
        t.setStyle(
                "-fx-font-size: 14px;"
                + "-fx-fill: " + SUB_TITLE + ";"
        );
        return t;
    }

    private Text code(String s) {
        Text t = new Text("Format: " + s + "\n");
        t.setStyle(
                "-fx-font-size: 14px;"
                + "-fx-font-family: 'Consolas';"
                + "-fx-fill: #81C784;"
                + "-fx-background-color: " + CODE + ";"
        );
        return t;
    }

    private Text example(String s) {
        Text t = new Text("Example: " + s);
        t.setStyle(
                "-fx-font-size: 14px;"
                + "-fx-fill: " + TEXT + ";"
        );
        return t;
    }

    private Text note(String s) {
        Text t = new Text("Notes: " + s + "\n");
        t.setStyle(
                "-fx-font-size: 14px;"
                + "-fx-fill: " + TEXT + ";"
        );
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
        getRoot().setWidth(800);
        getRoot().setHeight(750);
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

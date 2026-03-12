package systemtests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;

import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.model.person.Person;
import seedu.address.testutil.TestApp;


/**
 * GUI test for the delete command using TestFX (JUnit5 ApplicationExtension).
 */
@ExtendWith(ApplicationExtension.class)
public class DeleteCommandGuiTest {

    private int initialListSize;
    private Person personToDelete;
    @Start
    public void start(Stage stage) throws Exception {
        // Start a lightweight TestApp which sets up the UI with typical data in temp storage
        new TestApp().start(stage);
    }

    @BeforeEach
    public void setUp(FxRobot robot) {
        // wait for FX events
        WaitForAsyncUtils.waitForFxEvents();

        @SuppressWarnings("unchecked")
        ListView<Person> personListView = robot.lookup("#personListView").queryAs(ListView.class);
        initialListSize = personListView.getItems().size();
        assertTrue(initialListSize > 0, "Typical persons should be present");
        personToDelete = personListView.getItems().get(0);
    }

    @Test
    public void delete_firstPerson_success(FxRobot robot) {
        // Set the command text and fire the action on the FX thread to ensure the CommandBox handles it.
        robot.clickOn("#commandTextField");
        robot.write(DeleteCommand.COMMAND_WORD + " 1");
        robot.push(KeyCode.ENTER);

        // ensure UI updated
        WaitForAsyncUtils.waitForFxEvents();

        TextArea resultDisplay = robot.lookup("#resultDisplay").queryAs(TextArea.class);
        String resultText = resultDisplay.getText().trim();
        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(personToDelete));
        assertEquals(expectedMessage, resultText, "Result display should contain delete success message");

        @SuppressWarnings("unchecked")
        ListView<Person> personListView = robot.lookup("#personListView").queryAs(ListView.class);
        assertEquals(initialListSize - 1, personListView.getItems().size(),
                "Person list size should decrease by 1");
    }

    @Test
    public void delete_invalidIndex_fail(FxRobot robot) {
        int invalidIndex = initialListSize + 1; // out of bounds
        robot.clickOn("#commandTextField");
        robot.write(DeleteCommand.COMMAND_WORD + " " + invalidIndex);
        robot.push(KeyCode.ENTER);

        WaitForAsyncUtils.waitForFxEvents();

        TextArea resultDisplay = robot.lookup("#resultDisplay").queryAs(TextArea.class);
        String resultText = resultDisplay.getText().trim();
        assertEquals(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, resultText,
                "Result display should show invalid index message");
    }
}

package guitests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfSystemProperty;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;

import javafx.event.ActionEvent;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.model.application.Application;
import seedu.address.testutil.TestApp;


/**
 * GUI test for the delete command using TestFX (JUnit5 ApplicationExtension).
 */
@ExtendWith(ApplicationExtension.class)
@DisabledIfSystemProperty(named = "ci", matches = "true")
public class DeleteCommandGuiTest {

    private int initialListSize;
    private Application applicationToDelete;
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
        ListView<Application> applicationListView = robot.lookup("#applicationListView").queryAs(ListView.class);
        initialListSize = applicationListView.getItems().size();
        assertTrue(initialListSize > 0, "Typical applications should be present");
        applicationToDelete = applicationListView.getItems().get(0);
    }

    @Test
    public void delete_firstApplication_success(FxRobot robot) {
        // Set the command text and fire the action on the FX thread to ensure the CommandBox handles it.
        robot.interact(() -> {
            TextField commandField = robot.lookup("#commandTextField").queryAs(TextField.class);
            commandField.setText(DeleteCommand.COMMAND_WORD + " 1");
            commandField.fireEvent(new ActionEvent());
        });

        // ensure UI updated
        WaitForAsyncUtils.waitForFxEvents();

        TextArea resultDisplay = robot.lookup("#resultDisplay").queryAs(TextArea.class);
        String resultText = resultDisplay.getText().trim();
        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_APPLICATION_SUCCESS,
                Messages.format(applicationToDelete));
        assertEquals(expectedMessage, resultText, "Result display should contain delete success message");

        @SuppressWarnings("unchecked")
        ListView<Application> applicationListView = robot.lookup("#applicationListView").queryAs(ListView.class);
        assertEquals(initialListSize - 1, applicationListView.getItems().size(),
                "Application list size should decrease by 1");
    }

    @Test
    public void delete_invalidIndex_fail(FxRobot robot) {
        int invalidIndex = initialListSize + 1; // out of bounds
        robot.interact(() -> {
            TextField commandField = robot.lookup("#commandTextField").queryAs(TextField.class);
            commandField.setText(DeleteCommand.COMMAND_WORD + " " + invalidIndex);
            commandField.fireEvent(new ActionEvent());
        });

        WaitForAsyncUtils.waitForFxEvents();

        TextArea resultDisplay = robot.lookup("#resultDisplay").queryAs(TextArea.class);
        String resultText = resultDisplay.getText().trim();
        assertEquals(Messages.MESSAGE_INVALID_APPLICATION_DISPLAYED_INDEX, resultText,
                "Result display should show invalid index message");
    }
}

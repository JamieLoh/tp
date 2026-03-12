package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.util.concurrent.CountDownLatch;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javafx.application.Platform;
import javafx.stage.Stage;

/**
 * Contains tests for {@code HelpWindow}.
 */
public class HelpWindowTest {

    private static boolean toolkitAvailable;

    @BeforeAll
    public static void initToolkit() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        try {
            Platform.startup(latch::countDown);
            toolkitAvailable = true;
        } catch (IllegalStateException e) {
            toolkitAvailable = true;
            latch.countDown();
        } catch (UnsupportedOperationException e) {
            toolkitAvailable = false;
            latch.countDown();
        }
        latch.await();
    }

    @Test
    public void classInitializes() {
        assertDoesNotThrow(() -> Class.forName("seedu.address.ui.HelpWindow"));
    }

    @Test
    public void constructorSuccess() throws Exception {
        assumeTrue(toolkitAvailable, "JavaFX is not available in headless environment");
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            try {
                HelpWindow helpWindow = new HelpWindow(new Stage());

                helpWindow.show();
                assertTrue(helpWindow.isShowing());

                helpWindow.focus();
                assertTrue(helpWindow.getRoot().isFocused());

                helpWindow.hide();
                assertFalse(helpWindow.isShowing());
            } finally {
                latch.countDown();
            }
        });

        latch.await();
    }
}

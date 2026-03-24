package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalApplications.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class SummaryCommandTest {

    @Test
    public void execute_nullModel_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new SummaryCommand().execute(null));
    }

    @Test
    public void execute_emptyModel_showsZeroCounts() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        String expectedText = "Application Summary\n\n"
                + "Total Applications: 0\n"
                + "Pending: 0\n"
                + "Offered: 0\n"
                + "Rejected: 0\n";
        CommandResult expectedCommandResult = new CommandResult(expectedText, false, true, false);

        assertCommandSuccess(new SummaryCommand(), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_countsAreNonNegative() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        CommandResult result = new SummaryCommand().execute(model);
        String feedback = result.getFeedbackToUser();
        assertTrue(feedback.contains("Total Applications: "));
        for (String line : feedback.split("\n")) {
            if (line.contains(": ")) {
                String value = line.split(": ")[1].trim();
                assertTrue(Long.parseLong(value) >= 0, "Count should be non-negative: " + line);
            }
        }
    }

    @Test
    public void execute_typicalModel_showsCorrectCounts() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        long total = model.getAddressBook().getApplicationList().size();
        long pending = model.getAddressBook().getApplicationList().stream()
                .filter(a -> a.getStatus().toString().equalsIgnoreCase("Pending"))
                .count();
        long offered = model.getAddressBook().getApplicationList().stream()
                .filter(a -> a.getStatus().toString().equalsIgnoreCase("Offered"))
                .count();
        long rejected = model.getAddressBook().getApplicationList().stream()
                .filter(a -> a.getStatus().toString().equalsIgnoreCase("Rejected"))
                .count();

        String expectedText = "Application Summary\n\n"
                + "Total Applications: " + total + "\n"
                + "Pending: " + pending + "\n"
                + "Offered: " + offered + "\n"
                + "Rejected: " + rejected + "\n";
        CommandResult expectedCommandResult = new CommandResult(expectedText, false, true, false);

        assertCommandSuccess(new SummaryCommand(), model, expectedCommandResult, expectedModel);
    }
}

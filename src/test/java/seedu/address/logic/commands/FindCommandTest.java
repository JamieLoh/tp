package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_APPLICATIONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalApplications.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.application.ApplicationMatchesPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        ApplicationMatchesPredicate firstPredicate =
                new ApplicationMatchesPredicate("Grab", null, null, null,
                        null, null, null, Collections.emptyList());
        ApplicationMatchesPredicate secondPredicate =
                new ApplicationMatchesPredicate("Google", null, null, null,
                        null, null, null, Collections.emptyList());

        FindCommand findFirstCommand = new FindCommand(firstPredicate);
        FindCommand findSecondCommand = new FindCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different predicate -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    // =========================
    // No filter
    // =========================

    @Test
    public void execute_zeroKeywords_allApplicationsReturned() {
        // No filters applied → predicate returns true for all applications

        ApplicationMatchesPredicate predicate =
                new ApplicationMatchesPredicate(null, null, null,
                        null, null, null, null, Collections.emptyList());

        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredApplicationList(predicate);

        String expectedMessage = String.format(MESSAGE_APPLICATIONS_LISTED_OVERVIEW,
                expectedModel.getFilteredApplicationList().size());

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(expectedModel.getFilteredApplicationList(), model.getFilteredApplicationList());
    }

    // =========================
    // Core fields
    // =========================

    @Test
    public void execute_nameKeyword_filtered() {
        ApplicationMatchesPredicate predicate =
                new ApplicationMatchesPredicate("e", null, null, null, null, null, null,
                        Collections.emptyList());

        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredApplicationList(predicate);

        String expectedMessage = String.format(MESSAGE_APPLICATIONS_LISTED_OVERVIEW,
                expectedModel.getFilteredApplicationList().size());

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(expectedModel.getFilteredApplicationList(),
                model.getFilteredApplicationList());
    }

    @Test
    public void execute_roleKeyword_filtered() {
        ApplicationMatchesPredicate predicate =
                new ApplicationMatchesPredicate(null, "engineer", null, null, null, null, null,
                        Collections.emptyList());

        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredApplicationList(predicate);

        assertCommandSuccess(command, model,
                String.format(MESSAGE_APPLICATIONS_LISTED_OVERVIEW,
                        expectedModel.getFilteredApplicationList().size()),
                expectedModel);
    }

    @Test
    public void execute_dateKeyword_filtered() {
        ApplicationMatchesPredicate predicate =
                new ApplicationMatchesPredicate(null, null, null, null, null,
                        "01-01-2026", null, Collections.emptyList());

        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredApplicationList(predicate);

        assertCommandSuccess(command, model,
                String.format(MESSAGE_APPLICATIONS_LISTED_OVERVIEW,
                        expectedModel.getFilteredApplicationList().size()),
                expectedModel);
    }

    @Test
    public void execute_dateExactMatch_filtered() {
        ApplicationMatchesPredicate predicate =
                new ApplicationMatchesPredicate(null, null, null, null,
                        null, "01-03-2026", null,
                        Collections.emptyList());

        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredApplicationList(predicate);

        assertCommandSuccess(command, model,
                String.format(MESSAGE_APPLICATIONS_LISTED_OVERVIEW,
                        expectedModel.getFilteredApplicationList().size()),
                expectedModel);
    }

    @Test
    public void execute_dateNoMatch_noApplicationsFound() {
        ApplicationMatchesPredicate predicate =
                new ApplicationMatchesPredicate(null, null, null, null,
                        null, "31-12-2099", null,
                        Collections.emptyList());

        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredApplicationList(predicate);

        assertCommandSuccess(command, model,
                String.format(MESSAGE_APPLICATIONS_LISTED_OVERVIEW, 0),
                expectedModel);
    }

    @Test
    public void execute_statusKeyword_filtered() {
        ApplicationMatchesPredicate predicate =
                new ApplicationMatchesPredicate(null, null, null, null, null, null, "pending",
                        Collections.emptyList());

        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredApplicationList(predicate);

        assertCommandSuccess(command, model,
                String.format(MESSAGE_APPLICATIONS_LISTED_OVERVIEW,
                        expectedModel.getFilteredApplicationList().size()),
                expectedModel);
    }

    @Test
    public void execute_statusNoMatch_noApplicationsFound() {
        ApplicationMatchesPredicate predicate =
                new ApplicationMatchesPredicate(null, null, null, null,
                        null, null, "offered", Collections.emptyList());

        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredApplicationList(predicate);

        assertCommandSuccess(command, model,
                String.format(MESSAGE_APPLICATIONS_LISTED_OVERVIEW, 0),
                expectedModel);
    }

    // =========================
    // Optional fields
    // =========================
    @Test
    public void execute_emailKeyword_filtered() {
        ApplicationMatchesPredicate predicate =
                new ApplicationMatchesPredicate(null, null, "example", null,
                        null, null, null, Collections.emptyList());

        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredApplicationList(predicate);

        assertCommandSuccess(command, model,
                String.format(MESSAGE_APPLICATIONS_LISTED_OVERVIEW,
                        expectedModel.getFilteredApplicationList().size()),
                expectedModel);
    }

    @Test
    public void execute_emailEmptyKeyword() {
        ApplicationMatchesPredicate predicate =
                new ApplicationMatchesPredicate(null, null, "", null, null, null, null,
                        Collections.emptyList());

        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredApplicationList(predicate);

        assertCommandSuccess(command, model,
                String.format(MESSAGE_APPLICATIONS_LISTED_OVERVIEW,
                        expectedModel.getFilteredApplicationList().size()),
                expectedModel);
    }

    @Test
    public void execute_websiteKeyword_filtered() {
        ApplicationMatchesPredicate predicate =
                new ApplicationMatchesPredicate(null, null, null, "https", null, null, null,
                        Collections.emptyList());

        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredApplicationList(predicate);

        assertCommandSuccess(command, model,
                String.format(MESSAGE_APPLICATIONS_LISTED_OVERVIEW,
                        expectedModel.getFilteredApplicationList().size()),
                expectedModel);
    }

    @Test
    public void execute_websiteEmptyKeyword() {
        ApplicationMatchesPredicate predicate =
                new ApplicationMatchesPredicate(null, null, null, "", null, null, null,
                        Collections.emptyList());

        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredApplicationList(predicate);

        assertCommandSuccess(command, model,
                String.format(MESSAGE_APPLICATIONS_LISTED_OVERVIEW,
                        expectedModel.getFilteredApplicationList().size()),
                expectedModel);
    }

    @Test
    public void execute_addressKeyword_filtered() {
        ApplicationMatchesPredicate predicate =
                new ApplicationMatchesPredicate(null, null, null, null, "clementi", null, null,
                        Collections.emptyList());

        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredApplicationList(predicate);

        assertCommandSuccess(command, model,
                String.format(MESSAGE_APPLICATIONS_LISTED_OVERVIEW,
                        expectedModel.getFilteredApplicationList().size()),
                expectedModel);
    }

    @Test
    public void execute_addressEmptyKeyword() {
        ApplicationMatchesPredicate predicate =
                new ApplicationMatchesPredicate(null, null, null, null, "", null, null,
                        Collections.emptyList());

        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredApplicationList(predicate);

        assertCommandSuccess(command, model,
                String.format(MESSAGE_APPLICATIONS_LISTED_OVERVIEW,
                        expectedModel.getFilteredApplicationList().size()),
                expectedModel);
    }



    @Test
    public void execute_tagKeyword_filtered() {
        ApplicationMatchesPredicate predicate =
                new ApplicationMatchesPredicate(null, null, null, null, null, null, null,
                        Collections.singletonList("friend"));

        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredApplicationList(predicate);

        assertCommandSuccess(command, model,
                String.format(MESSAGE_APPLICATIONS_LISTED_OVERVIEW,
                        expectedModel.getFilteredApplicationList().size()),
                expectedModel);
    }

    @Test
    public void execute_tagEmptyKeyword() {
        ApplicationMatchesPredicate predicate =
                new ApplicationMatchesPredicate(null, null, null, null, null, null, null,
                        Collections.singletonList(""));

        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredApplicationList(predicate);

        assertCommandSuccess(command, model,
                String.format(MESSAGE_APPLICATIONS_LISTED_OVERVIEW,
                        expectedModel.getFilteredApplicationList().size()),
                expectedModel);
    }

    @Test
    public void execute_tagMultipleEmptyKeywords() {
        ApplicationMatchesPredicate predicate =
                new ApplicationMatchesPredicate(null, null, null, null, null, null, null,
                        Arrays.asList("", ""));

        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredApplicationList(predicate);

        assertCommandSuccess(command, model,
                String.format(MESSAGE_APPLICATIONS_LISTED_OVERVIEW,
                        expectedModel.getFilteredApplicationList().size()),
                expectedModel);
    }

    @Test
    public void execute_tagOrLogic() {
        ApplicationMatchesPredicate predicate =
                new ApplicationMatchesPredicate(null, null, null, null, null, null, null,
                        Arrays.asList("friend", "colleague"));

        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredApplicationList(predicate);

        assertCommandSuccess(command, model,
                String.format(MESSAGE_APPLICATIONS_LISTED_OVERVIEW,
                        expectedModel.getFilteredApplicationList().size()),
                expectedModel);
    }

    @Test
    public void execute_tagEmptyAndNormalKeyword_ignoresEmpty() {
        ApplicationMatchesPredicate predicate =
                new ApplicationMatchesPredicate(null, null, null, null, null, null, null,
                        Arrays.asList("", "friend"));

        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredApplicationList(predicate);

        assertCommandSuccess(command, model,
                String.format(MESSAGE_APPLICATIONS_LISTED_OVERVIEW,
                        expectedModel.getFilteredApplicationList().size()),
                expectedModel);
    }

    @Test
    public void execute_nameAndStatus_filtered() {
        ApplicationMatchesPredicate predicate =
                new ApplicationMatchesPredicate("Google", null, null, null,
                        null, null, "pending",
                        Collections.emptyList());

        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredApplicationList(predicate);

        assertCommandSuccess(command, model,
                String.format(MESSAGE_APPLICATIONS_LISTED_OVERVIEW,
                        expectedModel.getFilteredApplicationList().size()),
                expectedModel);
    }

    @Test
    public void execute_multipleKeywords_multipleApplicationsFound() {
        ApplicationMatchesPredicate predicate =
                new ApplicationMatchesPredicate("Google", null, null, null, null, null, null,
                        Arrays.asList("AI"));

        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredApplicationList(predicate);

        String expectedMessage = String.format(MESSAGE_APPLICATIONS_LISTED_OVERVIEW,
                expectedModel.getFilteredApplicationList().size());

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(expectedModel.getFilteredApplicationList(), model.getFilteredApplicationList());
    }

    @Test
    public void toStringMethod() {
        ApplicationMatchesPredicate predicate =
                new ApplicationMatchesPredicate("Grab", null, null,
                        null, null, null, null, Collections.emptyList());

        FindCommand findCommand = new FindCommand(predicate);
        String expected = FindCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, findCommand.toString());
    }
}

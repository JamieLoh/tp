package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_APPLICATION;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ArchiveCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.OpenCommand;
import seedu.address.logic.commands.SummaryCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.application.Application;
import seedu.address.model.application.ApplicationMatchesPredicate;
import seedu.address.testutil.ApplicationBuilder;
import seedu.address.testutil.ApplicationUtil;
import seedu.address.testutil.EditApplicationDescriptorBuilder;

public class AddressBookParserTest {

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        Application application = new ApplicationBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(ApplicationUtil.getAddCommand(application));
        assertEquals(new AddCommand(application), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_APPLICATION.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_APPLICATION), command);
    }

    @Test
    public void parseCommand_archive() throws Exception {
        ArchiveCommand command = (ArchiveCommand) parser.parseCommand(
                ArchiveCommand.COMMAND_WORD + " " + INDEX_FIRST_APPLICATION.getOneBased());
        assertEquals(new ArchiveCommand(INDEX_FIRST_APPLICATION), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Application application = new ApplicationBuilder().build();
        EditCommand.EditApplicationDescriptor descriptor = new EditApplicationDescriptorBuilder(application).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_APPLICATION.getOneBased() + " "
                + ApplicationUtil.getEditApplicationDescriptorDetails(descriptor));
        assertEquals(new EditCommand(INDEX_FIRST_APPLICATION, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        FindCommand command = (FindCommand) parser.parseCommand("find n/Google");

        ApplicationMatchesPredicate expectedPredicate = new ApplicationMatchesPredicate(
                "Google", null, null, null,
                null, null, null, Collections.emptyList());

        assertEquals(new FindCommand(expectedPredicate), command);
    }

    @Test
    public void parseCommand_find_multiplePrefixes() throws Exception {
        FindCommand command = (FindCommand) parser.parseCommand(
                "find n/Google r/Engineer s/Pending");

        ApplicationMatchesPredicate expectedPredicate =
                new ApplicationMatchesPredicate(
                        "Google", "Engineer", null, null,
                        null, null, "Pending", Collections.emptyList());

        assertEquals(new FindCommand(expectedPredicate), command);
    }

    @Test
    public void parseCommand_find_tagPrefix() throws Exception {
        FindCommand command = (FindCommand) parser.parseCommand(
                "find t/AI t/Python");

        ApplicationMatchesPredicate expectedPredicate =
                new ApplicationMatchesPredicate(
                        null, null, null, null,
                        null, null, null, Arrays.asList("AI", "Python"));

        assertEquals(new FindCommand(expectedPredicate), command);
    }

    @Test
    public void parseCommand_find_emptyPrefix() throws Exception {
        FindCommand command = (FindCommand) parser.parseCommand("find e/");

        ApplicationMatchesPredicate expectedPredicate =
                new ApplicationMatchesPredicate(
                        null, null, "", null,
                        null, null, null, Collections.emptyList());

        assertEquals(new FindCommand(expectedPredicate), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_summary() throws Exception {
        assertTrue(parser.parseCommand(SummaryCommand.COMMAND_WORD) instanceof SummaryCommand);
    }

    @Test
    public void parseCommand_open() throws Exception {
        OpenCommand command = (OpenCommand) parser.parseCommand(
                OpenCommand.COMMAND_WORD + " " + INDEX_FIRST_APPLICATION.getOneBased());
        assertEquals(new OpenCommand(INDEX_FIRST_APPLICATION, false), command);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
}

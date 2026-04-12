package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEBSITE;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.application.Application;

/**
 * Adds an application to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an application to HireME. "
            + "Parameters: "
            + PREFIX_NAME + "COMPANY_NAME "
            + PREFIX_ROLE + "ROLE "
            + PREFIX_DATE + "DATE "
            + PREFIX_STATUS + "STATUS "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_WEBSITE + "WEBSITE] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Grab "
            + PREFIX_ROLE + "Backend Developer Intern "
            + PREFIX_DATE + "01-03-2026 "
            + PREFIX_STATUS + "Pending "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_WEBSITE + "https://johndoe.example.com "
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
            + PREFIX_TAG + "InterviewRound2 "
            + PREFIX_TAG + "BigTech";

    public static final String MESSAGE_SUCCESS = "New application added: %1$s";
    public static final String MESSAGE_DUPLICATE_APPLICATION = "This application already exists in the HireME";

    private final Application toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Application}
     */
    public AddCommand(Application application) {
        requireNonNull(application);
        toAdd = application;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasApplication(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_APPLICATION);
        }

        model.addApplication(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAdd)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddCommand)) {
            return false;
        }

        AddCommand otherAddCommand = (AddCommand) other;
        return toAdd.equals(otherAddCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}

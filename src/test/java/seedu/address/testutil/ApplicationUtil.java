package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEBSITE;

import java.util.Set;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.model.application.Application;
import seedu.address.model.tag.Tag;

/**
 * A utility class for Application.
 */
public class ApplicationUtil {

    /**
     * Returns an add command string for adding the {@code application}.
     */
    public static String getAddCommand(Application application) {
        return AddCommand.COMMAND_WORD + " " + getApplicationDetails(application);
    }

    /**
     * Returns the part of command string for the given {@code application}'s details.
     */
    public static String getApplicationDetails(Application application) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + application.getCompanyName().fullCompanyName + " ");
        sb.append(PREFIX_ROLE + application.getRole().value + " ");
        sb.append(PREFIX_EMAIL + application.getEmail().value + " ");
        sb.append(PREFIX_WEBSITE + application.getWebsite().websiteName + " ");
        sb.append(PREFIX_ADDRESS + application.getAddress().value + " ");
        sb.append(PREFIX_DATE + application.getDate().value + " ");
        sb.append(PREFIX_STATUS + application.getStatus().toString() + " ");
        application.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditApplicationDescriptor}'s details.
     */
    public static String getEditApplicationDescriptorDetails(EditCommand.EditApplicationDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getCompanyName().ifPresent(
                name -> sb.append(PREFIX_NAME).append(name.fullCompanyName).append(" ")
        );
        descriptor.getRole().ifPresent(role -> sb.append(PREFIX_ROLE).append(role.value).append(" "));
        descriptor.getEmail().ifPresent(email -> sb.append(PREFIX_EMAIL).append(email.value).append(" "));
        descriptor.getWebsite().ifPresent(website -> sb.append(PREFIX_WEBSITE)
                .append(website.websiteName).append(" "));
        descriptor.getAddress().ifPresent(address -> sb.append(PREFIX_ADDRESS)
                .append(address.value).append(" "));
        descriptor.getDate().ifPresent(date -> sb.append(PREFIX_DATE).append(date.value).append(" "));
        descriptor.getStatus().ifPresent(
                status -> sb.append(PREFIX_STATUS).append(status.toString()).append(" ")
        );
        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(PREFIX_TAG);
            } else {
                tags.forEach(s -> sb.append(PREFIX_TAG).append(s.tagName).append(" "));
            }
        }
        return sb.toString();
    }
}

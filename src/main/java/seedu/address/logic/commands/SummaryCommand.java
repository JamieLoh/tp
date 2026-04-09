package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.model.Model;
import seedu.address.model.application.Application;

/**
 * Displays a summary of the application statistics.
 */
public class SummaryCommand extends Command {

    public static final String COMMAND_WORD = "summary";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows a summary of your applications.\n"
            + "Example: " + COMMAND_WORD;

    private static final String STATUS_PENDING = "PENDING";
    private static final String STATUS_OFFERED = "OFFERED";
    private static final String STATUS_REJECTED = "REJECTED";

    private static final Logger logger = LogsCenter.getLogger(SummaryCommand.class);

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        logger.info("Executing SummaryCommand");

        List<Application> applications = model.getAddressBook().getApplicationList();

        long archived = applications.stream()
                .filter(Application::isArchived)
                .count();
        long total = applications.size() - archived;
        long pending = countStatus(applications, STATUS_PENDING);
        long offered = countStatus(applications, STATUS_OFFERED);
        long rejected = countStatus(applications, STATUS_REJECTED);

        assert pending + offered + rejected == total
                : "Status counts should sum to total: " + pending + "+" + offered + "+" + rejected + " != " + total;

        logger.fine("Summary computed: total=" + total
                + ", pending=" + pending + ", offered=" + offered + ", rejected=" + rejected
                + ", archived=" + archived);

        String summaryText = buildSummaryText(total, pending, offered, rejected, archived);

        return new CommandResult(summaryText, UiAction.SHOW_SUMMARY);
    }

    /**
     * Counts the number of non-archived applications with the given status.
     *
     * @param applications List of all applications.
     * @param status Status to filter by.
     * @return Number of non-archived applications matching the status.
     */
    private long countStatus(List<Application> applications, String status) {
        return applications.stream()
                .filter(a -> !a.isArchived())
                .filter(a -> a.getStatus().toString().equalsIgnoreCase(status))
                .count();
    }

    /**
     * Builds the summary text displaying application statistics.
     *
     * @param total Total number of non-archived applications.
     * @param pending Number of pending applications.
     * @param offered Number of offered applications.
     * @param rejected Number of rejected applications.
     * @param archived Number of archived applications.
     * @return Formatted summary text.
     */
    private String buildSummaryText(long total, long pending, long offered, long rejected, long archived) {
        String successRate = (offered + rejected) > 0
                ? String.format("%.1f%%", (offered * 100.0) / (offered + rejected))
                : "N/A";

        return "Application Summary\n\n"
                + "Total Applications: " + total + "\n"
                + "Pending: " + pending + "\n"
                + "Offered: " + offered + "\n"
                + "Rejected: " + rejected + "\n"
                + "Success Rate: " + successRate + "\n"
                + "\n"
                + "Archived: " + archived + "\n";
    }
}

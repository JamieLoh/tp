package seedu.address.model.application;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents an Application's application status in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidStatus(String)}
 */
public class Status {

    enum StatusType {
        OFFERED,
        PENDING,
        REJECTED
    }

    public static final String VALIDATION_REGEX = "(?i)Offered|Pending|Rejected";
    public static final String MESSAGE_CONSTRAINTS =
            "Status must be one of: Offered, Pending, or Rejected.";
    public final StatusType value;

    /**
     * Constructs an {@code Status}.
     *
     * @param status Current application status for the role.
     */
    public Status(String status) {
        requireNonNull(status);
        checkArgument(isValidStatus(status), MESSAGE_CONSTRAINTS);
        switch (status.toLowerCase()) {
        case "offered":
            this.value = StatusType.OFFERED;
            break;
        case "pending":
            this.value = StatusType.PENDING;
            break;
        case "rejected":
            this.value = StatusType.REJECTED;
            break;
        default:
            throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
        }
    }

    /**
     * Returns true if the given status is valid.
     */
    public static boolean isValidStatus(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value.name().charAt(0) + value.name().substring(1).toLowerCase();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Status)) {
            return false;
        }

        Status otherStatus = (Status) other;
        return value.equals(otherStatus.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}

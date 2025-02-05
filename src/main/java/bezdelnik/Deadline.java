package bezdelnik;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a task with a deadline.
 * A Deadline task contains a description and a due date/time.
 */
class Deadline extends Task {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm");
    private LocalDateTime by;

    /**
     * Constructs a new Deadline with the given description and due date/time as a string.
     *
     * @param description The task description.
     * @param byString    The due date/time in the format dd/MM/yyyy HHmm.
     */
    Deadline(String description, String byString) {
        this(description, false, byString);
    }

    /**
     * Constructs a new Deadline with the given description and due date/time.
     *
     * @param description The task description.
     * @param by          The due date/time.
     */
    Deadline(String description, LocalDateTime by) {
        this(description, false, by);
    }

    /**
     * Constructs a new Deadline with the given description, status, and due date/time as a string.
     *
     * @param description The task description.
     * @param isDone      The completion status.
     * @param byString    The due date/time in the format dd/MM/yyyy HHmm.
     */
    Deadline(String description, boolean isDone, String byString) {
        super(description, isDone);
        this.by = LocalDateTime.parse(byString, formatter);
    }

    /**
     * Constructs a new Deadline with the given description, status, and due date/time.
     *
     * @param description The task description.
     * @param isDone      The completion status.
     * @param by          The due date/time.
     */
    Deadline(String description, boolean isDone, LocalDateTime by) {
        super(description, isDone);
        this.by = by;
    }

    public Task markAsDone() {
        return new Deadline(description, true, by);
    }

    public Task markAsUndone() {
        return new Deadline(description, by);
    }

    public String returnCommand() {
        return String.format("deadline %s /by %s", description, by.format(formatter));
    }

    @Override
    public String toString() {
        return String.format("[D] %s (by: %s)", super.toString(), this.by);
    }
}

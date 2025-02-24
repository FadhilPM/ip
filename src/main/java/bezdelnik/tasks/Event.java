package bezdelnik.tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents an event task.
 * An Event task contains a description and a start and end date/time.
 */
public class Event extends Task {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm");
    private LocalDateTime from;
    private LocalDateTime to;

    /**
     * Constructs a new Event with the given description, start, and end times as strings.
     *
     * @param description The task description.
     * @param from        The start date/time in the format dd/MM/yyyy HHmm.
     * @param to          The end date/time in the format dd/MM/yyyy HHmm.
     */
    public Event(String description, String from, String to) {
        this(description, false, from, to);
    }

    /**
     * Constructs a new Event with the given description, start, and end times.
     *
     * @param description The task description.
     * @param from        The start date/time.
     * @param to          The end date/time.
     */
    public Event(String description, LocalDateTime from, LocalDateTime to) {
        this(description, false, from, to);
    }

    /**
     * Constructs a new Event with the given description, status, and start/end times as strings.
     *
     * @param description The task description.
     * @param isDone      The completion status.
     * @param from        The start date/time in the format dd/MM/yyyy HHmm.
     * @param to          The end date/time in the format dd/MM/yyyy HHmm.
     */
    public Event(String description, boolean isDone, String from, String to) {
        super(description, isDone);
        this.from = LocalDateTime.parse(from, formatter);
        this.to = LocalDateTime.parse(to, formatter);
    }

    /**
     * Constructs a new Event with the given description, status, and start/end times.
     *
     * @param description The task description.
     * @param isDone      The completion status.
     * @param from        The start date/time.
     * @param to          The end date/time.
     */
    public Event(String description, boolean isDone, LocalDateTime from, LocalDateTime to) {
        super(description, isDone);
        this.from = from;
        this.to = to;
    }

    public Task markAsDone() {
        return new Event(description, true, from, to);
    }

    public Task markAsUndone() {
        return new Event(description, from, to);
    }

    public LocalDateTime getStartTime() {
        return this.from;
    }

    public String returnCommand() {
        return String.format("event %s /from %s /to %s", description, from.format(formatter), to.format(formatter));
    }

    @Override
    public String toString() {
        return String.format("[E] %s (from: %s to: %s)", super.toString(), this.from, this.to);
    }
}

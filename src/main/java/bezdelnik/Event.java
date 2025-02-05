package bezdelnik;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class Event extends Task {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm");
    private LocalDateTime from;
    private LocalDateTime to;

    Event(String description, String from, String to) {
        this(description, false, from, to);
    }

    Event(String description, LocalDateTime from, LocalDateTime to) {
        this(description, false, from, to);
    }

    Event(String description, boolean isDone, String from, String to) {
        super(description, isDone);
        this.from = LocalDateTime.parse(from, formatter);
        this.to = LocalDateTime.parse(to, formatter);
    }

    Event(String description, boolean isDone, LocalDateTime from, LocalDateTime to) {
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

    public String returnCommand() {
        return String.format("event %s /from %s /to %s", description, from.format(formatter), to.format(formatter));
    }

    @Override
    public String toString() {
        return String.format("[E] %s (from: %s to: %s)", super.toString(), this.from, this.to);
    }
}

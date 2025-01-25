import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class Deadline extends Task {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm");
    private LocalDateTime by;

    Deadline(String description, String byString) {
        this(description, false, byString);
    }

    Deadline(String description, LocalDateTime by) {
        this(description, false, by);
    }

    Deadline(String description, boolean isDone, String byString) {
        super(description, isDone);
        this.by = LocalDateTime.parse(byString, formatter);
    }

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

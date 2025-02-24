package bezdelnik.tasks;

import java.time.LocalDateTime;

/**
 * Represents a simple Todo task.
 */
public class Todo extends Task {
    /**
     * Constructs a new Todo with the given description.
     *
     * @param description The task description.
     */
    public Todo(String description) {
        this(description, false);
    }

    /**
     * Constructs a new Todo with the given description and completion status.
     *
     * @param description The task description.
     * @param isDone      The completion status.
     */
    public Todo(String description, boolean isDone) {
        super(description, isDone);
    }

    public Task markAsDone() {
        return new Todo(description, true);
    }

    public Task markAsUndone() {
        return new Todo(description);
    }

    public LocalDateTime getStartTime() {
        return LocalDateTime.MAX;
    }

    public String returnCommand() {
        return String.format("todo %s", description);
    }

    @Override
    public String toString() {
        return String.format("[T] %s", super.toString());
    }
}

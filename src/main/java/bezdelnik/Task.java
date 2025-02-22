package bezdelnik;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Represents an abstract task in the Bezdelnik application.
 * Each task has a description and a completion status.
 */
public abstract class Task implements Comparable<Task> {
    protected final String description;
    protected final boolean isDone;

    /**
     * Constructs a Task with the specified description and completion status.
     *
     * @param description The task description.
     * @param isDone      The completion status.
     */
    Task(String description, boolean isDone) {
        this.description = description;
        this.isDone = isDone;
    }

    /**
     * Returns a new Task instance marked as done.
     *
     * @return A new Task with isDone set to true.
     */
    public abstract Task markAsDone();

    /**
     * Returns a new Task instance marked as undone.
     *
     * @return A new Task with isDone set to false.
     */
    public abstract Task markAsUndone();

    /**
     * Checks if the task is marked as done.
     *
     * @return True if the task is done, otherwise false.
     */
    public boolean isDone() {
        return isDone;
    }

    /**
     * Checks if the task description contains the specified string.
     *
     * @param s The string to search for.
     * @return True if the description contains the string, otherwise false.
     */
    public boolean contains(String s) {
        return description.contains(s);
    }

    /**
     * Returns the command representation of this task.
     *
     * @return A string command to recreate this task.
     */
    public abstract String returnCommand();

    public abstract LocalDateTime getStartTime();

    public int compareTo(Task otherTask) {
        return otherTask.getStartTime().compareTo(this.getStartTime());
    }

    @Override
    public String toString() {
        return String.format("[%s] %s", isDone ? "X" : "", this.description);
    }
}

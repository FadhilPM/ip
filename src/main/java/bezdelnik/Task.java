package bezdelnik;

import java.io.Serializable;

abstract class Task implements Serializable {
    protected final String description;
    protected final boolean isDone;

    Task(String description, boolean isDone) {
        this.description = description;
        this.isDone = isDone;
    }

    public abstract Task markAsDone();

    public abstract Task markAsUndone();

    public boolean isDone() {
        return isDone;
    }

    public boolean contains(String s) {
        return description.contains(s);
    }

    public abstract String returnCommand();

    @Override
    public String toString() {
        return String.format("[%s] %s", isDone ? "X" : "", this.description);
    }
}

abstract class Task {
    protected final String description;
    protected final boolean isDone;

    Task(String description, boolean isDone) {
        this.description = description;
        this.isDone = isDone;
    }

    Task(String description) {
        this(description, false);
    }

    abstract public Task markAsDone();

    abstract public Task markAsUndone();

    @Override
    public String toString() {
        return String.format("[%s] %s", isDone ? "X" : "", this.description);
    }
}

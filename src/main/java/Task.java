abstract class Task {
    protected final String description;
    protected final boolean isDone;

    Task(String description, boolean isDone) {
        this.description = description;
        this.isDone = isDone;
    }

    abstract public Task markAsDone();

    abstract public Task markAsUndone();

    public boolean contains(String s) {
        return description.contains(s);
    }

    @Override
    public String toString() {
        return String.format("[%s] %s", isDone ? "X" : "", this.description);
    }
}

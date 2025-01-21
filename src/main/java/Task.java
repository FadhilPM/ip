class Task {
    private final String description;
    private final boolean isDone;

    Task(String description, boolean isDone) {
        this.description = description;
        this.isDone = isDone;
    }

    Task(String description) {
        this(description, false);
    }

    public Task markAsDone() {
        return new Task(this.description, true);
    }

    public Task markAsUndone() {
        return new Task(this.description, false);
    }

    @Override
    public String toString() {
        return String.format("[%s] %s", isDone ? "X" : "", this.description);
    }
}

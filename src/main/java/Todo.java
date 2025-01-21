class Todo extends Task {

    Todo(String description) {
        this(description, false);
    }

    Todo(String description, boolean isDone) {
        super(description, isDone);
    }

    public Task markAsDone() {
        return new Todo(description, true);
    }

    public Task markAsUndone() {
        return new Todo(description);
    }

    @Override
    public String toString() {
        return String.format("[T] %s", super.toString());
    }
}

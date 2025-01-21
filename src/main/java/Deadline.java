class Deadline extends Task {
    private String by;

    Deadline(String description, String by) {
        this(description, false, by);
    }

    Deadline(String description, boolean isDone, String by) {
        super(description, isDone);
        this.by = by;
    }

    public Task markAsDone() {
        return new Deadline(description, true, by);
    }

    public Task markAsUndone() {
        return new Deadline(description, by);
    }

    @Override
    public String toString() {
        return String.format("[D] %s (by: %s)", super.toString(), this.by);
    }
}

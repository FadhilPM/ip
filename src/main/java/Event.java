class Event extends Task {
    private String from;
    private String to;

    Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    Event(String description, boolean isDone, String from, String to) {
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

    @Override
    public String toString() {
        return String.format("[E] %s (from: %s to: %s)", super.toString(), this.from, this.to);
    }
}

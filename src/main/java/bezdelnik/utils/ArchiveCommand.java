package bezdelnik.utils;

class ArchiveCommand implements Command {
    private final Taskman taskman;
    private final String path;

    ArchiveCommand(Taskman taskman, String path) {
        this.taskman = taskman;
        this.path = path;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Pair<String, Taskman> execute() throws BezdelnikException {
        try {
            WriteStorage.writeTaskmanToFile(this.taskman, this.path);

            String output = String.format("\tTask list archived to: %s. You have turned over a new leaf.", this.path);
            return new Pair<String, Taskman>(output, new Taskman());
        } catch (Throwable t) {
            throw new BezdelnikException(String.format("Unknown error accessing path: %s.", this.path));
        }
    }
}

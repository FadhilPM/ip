package bezdelnik.utils;

/**
 * Command implementation for listing all tasks in the task list.
 * <p>
 * This class handles retrieving and displaying all tasks currently
 * stored in the Bezdelnik system's taskman collection.
 * </p>
 */
public class ListCommand implements Command {
    private final Taskman taskman;

    ListCommand(Taskman taskman) {
        this.taskman = taskman;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Pair<String, Taskman> execute() throws BezdelnikException {
        String returnString = this.taskman.listString();
        return new Pair<String, Taskman>(returnString, this.taskman);
    }
}

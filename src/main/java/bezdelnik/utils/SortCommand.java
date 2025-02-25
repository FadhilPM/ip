package bezdelnik.utils;

/**
 * Command implementation for sorting all tasks in the task list.
 * <p>
 * This class handles sorting, retrieving and displaying all tasks
 * currently stored in the Bezdelnik system's taskman collection.
 * </p>
 */
public class SortCommand implements Command {
    private final Taskman taskman;

    SortCommand(Taskman taskman) {
        this.taskman = taskman;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Pair<String, Taskman> execute() throws BezdelnikException {
        if (taskman.size() == 0) {
            throw new BezdelnikException("No tasks to sort!");
        }

        Taskman sortedTaskman = this.taskman.sorted();
        String output = String.format(
            "\t%d task(s) sorted by time:\n%s", sortedTaskman.size(), sortedTaskman.listString());
        return new Pair<String, Taskman>(output, sortedTaskman);
    }
}

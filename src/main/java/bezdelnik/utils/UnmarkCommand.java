package bezdelnik.utils;

/**
 * Command implementation for unmarking a task in the task list.
 * <p>
 * This class handles the unmarking of a task from the provided Taskman
 * based on the provided task index.
 * </p>
 */
public class UnmarkCommand implements Command {
    private final Taskman taskman;
    private final int idx;

    UnmarkCommand(Taskman taskman, int idx) {
        this.taskman = taskman;
        this.idx = idx;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Pair<String, Taskman> execute() throws BezdelnikException {
        try {
            Taskman newTaskman = taskman.operate(idx, x -> x.markAsUndone());
            String output = String.format("\tI have marked this task as undone.\n\t%s", newTaskman.get(idx));
            return new Pair<String, Taskman>(output, newTaskman);
        } catch (IndexOutOfBoundsException iobe) {
            throw BezdelnikExceptionCreator.createOutOfRangeException(taskman);
        }
    }
}

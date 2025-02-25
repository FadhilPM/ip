package bezdelnik.utils;

import bezdelnik.tasks.Task;

/**
 * Command implementation for removing a task from the task list.
 * <p>
 * This class handles the deletion of tasks from the provided Taskman
 * based on the provided task index.
 * </p>
 */
public class RemoveCommand implements Command {
    private final Taskman taskman;
    private final int idx;

    RemoveCommand(Taskman taskman, int idx) {
        this.taskman = taskman;
        this.idx = idx;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Pair<String, Taskman> execute() throws BezdelnikException {
        try {
            Task toDelete = taskman.get(idx);
            Taskman newTaskman = taskman.remove(idx);

            String output = String.format("\tI have deleted this task.\n\t%s", toDelete);
            return new Pair<String, Taskman>(output, newTaskman);
        } catch (NumberFormatException nfe) {
            throw BezdelnikExceptionCreator.createOutOfRangeException(taskman);
        } catch (IndexOutOfBoundsException iobe) {
            throw BezdelnikExceptionCreator.createOutOfRangeException(taskman);
        }
    }
}

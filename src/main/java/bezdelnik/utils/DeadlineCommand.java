package bezdelnik.utils;

import bezdelnik.tasks.Deadline;
import bezdelnik.tasks.Task;

/**
 * Command implementation for creating and adding a new Deadline task.
 * <p>
 * This class handles the creation of Deadline tasks in the Bezdelnik system.
 * It parses input data and creates a properly formatted Deadline task,
 * then adds it to the Taskman.
 * </p>
 */
public class DeadlineCommand implements Command {
    private final Taskman taskman;
    private final String[] array;

    DeadlineCommand(Taskman taskman, String[] array) {
        this.taskman = taskman;
        this.array = array;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Pair<String, Taskman> execute() throws BezdelnikException {
        try {
            Task toAdd = new Deadline(array[0], array[1]);
            Taskman newTaskman = taskman.add(toAdd);
            String output = String.format("\tadded:\n\t%s\n\tYou currently have %d task(s)", toAdd, newTaskman.size());
            return new Pair<String, Taskman>(output, newTaskman);
        } catch (java.time.format.DateTimeParseException e) {
            throw BezdelnikExceptionCreator.createDeadlineFormatException();
        }
    }
}

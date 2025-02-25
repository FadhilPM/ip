package bezdelnik.utils;
import bezdelnik.tasks.Event;
import bezdelnik.tasks.Task;

/**
 * Command implementation for creating and adding a new Event task.
 * <p>
 * This class handles the creation of Event tasks in the Bezdelnik system.
 * It parses input data and creates a properly formatted Event task,
 * then adds it to the Taskman.
 * </p>
 */
public class EventCommand implements Command {
    private final Taskman taskman;
    private final String[] array;

    EventCommand(Taskman taskman, String[] array) {
        this.taskman = taskman;
        this.array = array;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Pair<String, Taskman> execute() throws BezdelnikException {
        try {
            Task toAdd = new Event(array[0], array[1].substring(5), array[2].substring(3));
            Taskman newTaskman = taskman.add(toAdd);
            String output = String.format("\tadded:\n\t%s\n\tYou currently have %d task(s)", toAdd, newTaskman.size());
            return new Pair<String, Taskman>(output, newTaskman);
        } catch (java.time.format.DateTimeParseException e) {
            throw BezdelnikExceptionCreator.createEventFormatException();
        }
    }
}

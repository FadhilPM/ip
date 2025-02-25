package bezdelnik.utils;

import bezdelnik.tasks.Task;
import bezdelnik.tasks.Todo;

/**
 * Command implementation for creating and adding a new Todo task.
 * <p>
 * This class handles the creation of Todo tasks in the Bezdelnik system.
 * It parses input data and creates a properly formatted Todo task,
 * then adds it to the Taskman.
 * </p>
 */
public class TodoCommand implements Command {
    private final Taskman taskman;
    private final String todoInput;

    TodoCommand(Taskman taskman, String todoInput) {
        this.taskman = taskman;
        this.todoInput = todoInput;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Pair<String, Taskman> execute() throws BezdelnikException {
        try {
            Task toAdd = new Todo(todoInput);
            Taskman newTaskman = taskman.add(toAdd);

            String output = String.format("\tadded:\n\t%s\n\tYou currently have %d task(s)", toAdd, newTaskman.size());
            return new Pair<String, Taskman>(output, newTaskman);
        } catch (Throwable t) {
            throw new BezdelnikException("Error creating todo task: " + t.getMessage());
        }
    }
}

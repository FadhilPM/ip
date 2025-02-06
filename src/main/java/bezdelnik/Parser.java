package bezdelnik;

/**
 * Provides public static method to parse user input commands and update the task manager.
 */
public class Parser {
    /**
     * Parses the given input string and performs the corresponding task management operation.
     *
     * @param input   The user input command.
     * @param taskman The current task manager state.
     * @return A Pair containing a response message and the updated task manager.
     */
    public static Pair<String, Taskman> parse(String input, Taskman taskman) {
        try {
            String command = input.split(" ")[0];
            switch (command) {
            case "ls":
            case "list":
                return handleList(taskman);
            case "m":
            case "mark":
                return handleMark(input, taskman);
            case "u":
            case "unmark":
                return handleUnmark(input, taskman);
            case "rem":
            case "remove":
            case "del":
            case "delete":
                return handleRemove(input, taskman);
            case "todo":
                return handleTodo(input, taskman);
            case "deadline":
                return handleDeadline(input, taskman);
            case "event":
                return handleEvent(input, taskman);
            case "find":
                return handleFind(input, taskman);
            default:
                return handleDefault(input, taskman);
            }
        } catch (NumberFormatException n) {
            return new Pair<String, Taskman>("\tInvalid integer!", taskman);
        } catch (IndexOutOfBoundsException i) {
            return new Pair<String, Taskman>("\tInvalid command parameters!", taskman);
        } catch (BezdelnikException be) {
            return new Pair<String, Taskman>(be.toString(), taskman);
        }
    }

    /**
     * Returns a Pair containing the list of tasks and the unchanged task manager.
     *
     * @param taskman The current task manager state.
     * @return A Pair with the task list as a formatted string and the task manager.
     */
    private static Pair<String, Taskman> handleList(Taskman taskman) {
        String output = taskman.listString();
        return new Pair<String, Taskman>(output, taskman);
    }

    /**
     * Marks a task as done based on the input command.
     *
     * @param input   The user input command.
     * @param taskman The current task manager state.
     * @return A Pair with a confirmation message and the updated task manager.
     * @throws BezdelnikException If an error occurs accessing the task.
     */
    private static Pair<String, Taskman> handleMark(String input, Taskman taskman) throws BezdelnikException {
        int idx = Integer.parseUnsignedInt(input.split(" ")[1]) - 1;
        taskman = taskman.operate(idx, x -> x.markAsDone());
        String output = String.format("\tI have marked this task as done.\n\t%s", taskman.get(idx));
        return new Pair<String, Taskman>(output, taskman);
    }

    /**
     * Marks a task as undone based on the input command.
     *
     * @param input   The user input command.
     * @param taskman The current task manager state.
     * @return A Pair with a confirmation message and the updated task manager.
     * @throws BezdelnikException If an error occurs accessing the task.
     */
    private static Pair<String, Taskman> handleUnmark(String input, Taskman taskman) throws BezdelnikException {
        int idx = Integer.parseUnsignedInt(input.split(" ")[1]) - 1;
        taskman = taskman.operate(idx, x -> x.markAsUndone());
        String output = String.format("\tI have marked this task as undone.\n\t%s", taskman.get(idx));
        return new Pair<String, Taskman>(output, taskman);
    }

    /**
     * Removes a task based on the input command.
     *
     * @param input   The user input command.
     * @param taskman The current task manager state.
     * @return A Pair with a confirmation message and the updated task manager.
     * @throws BezdelnikException If an error occurs accessing the task.
     */
    private static Pair<String, Taskman> handleRemove(String input, Taskman taskman) throws BezdelnikException {
        int idx = Integer.parseUnsignedInt(input.split(" ")[1]) - 1;
        Task toDelete = taskman.get(idx);
        taskman = taskman.remove(idx);
        String output = String.format("\tI have deleted this task.\n\t%s", toDelete);
        return new Pair<String, Taskman>(output, taskman);
    }

    /**
     * Adds a new Todo task based on the input command.
     *
     * @param input   The user input command.
     * @param taskman The current task manager state.
     * @return A Pair with a confirmation message and the updated task manager.
     */
    private static Pair<String, Taskman> handleTodo(String input, Taskman taskman) {
        String todoInput = input.substring(5);
        if (todoInput.isEmpty()) {
            return new Pair<String, Taskman>("\ttodo must be followed with something to do!", taskman);
        } else {
            Task toAdd = new Todo(todoInput);
            taskman = taskman.add(toAdd);
            String output = String.format("\tadded:\n\t%s\n\tYou currently have %d task(s)", toAdd, taskman.size());
            return new Pair<String, Taskman>(output, taskman);
        }
    }

    /**
     * Adds a new Deadline task based on the input command.
     *
     * @param input   The user input command.
     * @param taskman The current task manager state.
     * @return A Pair with a confirmation message and the updated task manager.
     */
    private static Pair<String, Taskman> handleDeadline(String input, Taskman taskman) {
        String deadlineInput = input.substring(9);
        String[] array = deadlineInput.split(" /by ");
        Task toAdd = new Deadline(array[0], array[1]);
        taskman = taskman.add(toAdd);
        String output = String.format("\tadded:\n\t%s\n\tYou currently have %d task(s)", toAdd, taskman.size());
        return new Pair<String, Taskman>(output, taskman);
    }

    /**
     * Adds a new Event task based on the input command.
     *
     * @param input   The user input command.
     * @param taskman The current task manager state.
     * @return A Pair with a confirmation message and the updated task manager.
     */
    private static Pair<String, Taskman> handleEvent(String input, Taskman taskman) {
        String eventInput = input.substring(6);
        String[] array = eventInput.split(" /");
        // Assumes array[1] starts with "at " and array[2] starts with "on "
        Task toAdd = new Event(array[0], array[1].substring(5), array[2].substring(3));
        taskman = taskman.add(toAdd);
        String output = String.format("\tadded:\n\t%s\n\tYou currently have %d task(s)", toAdd, taskman.size());
        return new Pair<String, Taskman>(output, taskman);
    }

    /**
     * Filters tasks based on the input search command.
     *
     * @param input   The user input command.
     * @param taskman The current task manager state.
     * @return A Pair with the filtered task list as a formatted string and the unchanged task manager.
     */
    private static Pair<String, Taskman> handleFind(String input, Taskman taskman) {
        String toSearchFor = input.substring(5);
        String output = taskman.filter(x -> x.contains(toSearchFor)).listString();
        return new Pair<String, Taskman>(output, taskman);
    }

    /**
     * Returns a default response for unsupported commands.
     *
     * @param input   The unsupported user input command.
     * @param taskman The current task manager state.
     * @return A Pair with an error message and the unchanged task manager.
     */
    private static Pair<String, Taskman> handleDefault(String input, Taskman taskman) {
        String output = String.format("\tUnsupported command: %s", input);
        return new Pair<String, Taskman>(output, taskman);
    }
}

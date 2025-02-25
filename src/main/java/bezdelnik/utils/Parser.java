package bezdelnik.utils;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Provides public static method to parse user input commands and update the task manager.
 */
public class Parser {
    enum CommandType {
        LIST,
        MARK,
        UNMARK,
        REMOVE,
        TODO,
        DEADLINE,
        EVENT,
        FIND,
        SORT,
        ARCHIVE,
        UNKNOWN
    }

    private static CommandType determineCommandType(String input) {
        String command = input.split(" ")[0].toLowerCase();
        return switch (command) {
        case "l", "ls", "list" -> CommandType.LIST;
        case "m", "mark" -> CommandType.MARK;
        case "u", "unmark" -> CommandType.UNMARK;
        case "rm", "rem", "remove", "del", "delete" -> CommandType.REMOVE;
        case "todo" -> CommandType.TODO;
        case "ded", "dead", "deadline" -> CommandType.DEADLINE;
        case "e", "ev", "event" -> CommandType.EVENT;
        case "f", "find" -> CommandType.FIND;
        case "s", "sort" -> CommandType.SORT;
        case "a", "archive" -> CommandType.ARCHIVE;
        default -> CommandType.UNKNOWN;
        };
    }

    private static String removeFirstWord(String input) {
        assert input != null;
        return Arrays.stream(input.split(" "))
            .skip(1)
            .collect(Collectors.joining(" "));
    }

    /**
     * Parses the given input string and performs the corresponding task management operation.
     *
     * @param input   The user input command.
     * @param taskman The current task manager state.
     * @return A Pair containing a response message and the updated task manager.
     */
    public static Pair<String, Taskman> parse(String input, Taskman taskman) {
        assert input != null;
        assert taskman != null;

        CommandType commandType = determineCommandType(input);
        try {
            return switch (commandType) {
            case LIST -> handleList(taskman);
            case MARK -> handleMark(input, taskman);
            case UNMARK -> handleUnmark(input, taskman);
            case REMOVE -> handleRemove(input, taskman);
            case TODO -> handleTodo(input, taskman);
            case DEADLINE -> handleDeadline(input, taskman);
            case EVENT -> handleEvent(input, taskman);
            case FIND -> handleFind(input, taskman);
            case SORT -> handleSort(taskman);
            case ARCHIVE -> handleArchive(input, taskman);
            case UNKNOWN -> handleDefault(input, taskman);
            };
        } catch (BezdelnikException be) {
            return new Pair<String, Taskman>(be.getMessage(), taskman);
        }
    }

    /**
     * Returns a Pair containing the list of tasks and the unchanged task manager.
     *
     * @param taskman The current task manager state.
     * @return A Pair with the task list as a formatted string and the task manager.
     */
    private static Pair<String, Taskman> handleList(Taskman taskman) throws BezdelnikException {
        Command toReturn = new ListCommand(taskman);
        return toReturn.execute();
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
        String[] parts = input.split(" ");
        if (parts.length != 2) {
            throw BezdelnikExceptionCreator.createOutOfRangeException(taskman);
        }

        try {
            int idx = Integer.parseUnsignedInt(parts[1]) - 1;
            Command toReturn = new MarkCommand(taskman, idx);
            return toReturn.execute();
        } catch (NumberFormatException nfe) {
            throw BezdelnikExceptionCreator.createOutOfRangeException(taskman);
        }
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
        String[] parts = input.split(" ");
        if (parts.length != 2) {
            throw BezdelnikExceptionCreator.createOutOfRangeException(taskman);
        }

        try {
            int idx = Integer.parseUnsignedInt(parts[1]) - 1;
            Command toReturn = new UnmarkCommand(taskman, idx);
            return toReturn.execute();
        } catch (NumberFormatException nfe) {
            throw BezdelnikExceptionCreator.createOutOfRangeException(taskman);
        }
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
        String[] parts = input.split(" ");
        if (parts.length != 2) {
            throw BezdelnikExceptionCreator.createOutOfRangeException(taskman);
        }
        try {
            int idx = Integer.parseUnsignedInt(parts[1]) - 1;
            Command toReturn = new RemoveCommand(taskman, idx);
            return toReturn.execute();
        } catch (NumberFormatException nfe) {
            throw BezdelnikExceptionCreator.createOutOfRangeException(taskman);
        }
    }

    /**
     * Adds a new Todo task based on the input command.
     *
     * @param input   The user input command.
     * @param taskman The current task manager state.
     * @return A Pair with a confirmation message and the updated task manager.
     */
    private static Pair<String, Taskman> handleTodo(String input, Taskman taskman) throws BezdelnikException {
        String todoInput = removeFirstWord(input);
        if (todoInput.isEmpty()) {
            throw BezdelnikExceptionCreator.createTodoFormatException();
        }

        Command toReturn = new TodoCommand(taskman, todoInput);
        return toReturn.execute();
    }

    /**
     * Adds a new Deadline task based on the input command.
     *
     * @param input   The user input command.
     * @param taskman The current task manager state.
     * @return A Pair with a confirmation message and the updated task manager.
     */
    private static Pair<String, Taskman> handleDeadline(String input, Taskman taskman) throws BezdelnikException {
        String deadlineInput = removeFirstWord(input);
        if (deadlineInput.isEmpty()) {
            throw BezdelnikExceptionCreator.createDeadlineFormatException();
        }

        String[] array = deadlineInput.split(" /by ");
        if (array.length != 2 || array[0].trim().isEmpty()) {
            throw BezdelnikExceptionCreator.createDeadlineFormatException();
        }
        Command toReturn = new DeadlineCommand(taskman, array);
        return toReturn.execute();
    }

    /**
     * Adds a new Event task based on the input command.
     *
     * @param input   The user input command.
     * @param taskman The current task manager state.
     * @return A Pair with a confirmation message and the updated task manager.
     */
    private static Pair<String, Taskman> handleEvent(String input, Taskman taskman) throws BezdelnikException {
        String eventInput = removeFirstWord(input);
        if (eventInput.isEmpty()) {
            throw BezdelnikExceptionCreator.createEventFormatException();
        }

        String[] array = eventInput.split(" /");
        if (array.length != 3 || array[0].trim().isEmpty()
            || !array[1].startsWith("from ") || !array[2].startsWith("to ")) {
            throw BezdelnikExceptionCreator.createEventFormatException();
        }

        Command toReturn = new EventCommand(taskman, array);
        return toReturn.execute();
    }

    /**
     * Filters tasks based on the input search command.
     *
     * @param input   The user input command.
     * @param taskman The current task manager state.
     * @return A Pair with the filtered task list as a formatted string and the unchanged task manager.
     */
    private static Pair<String, Taskman> handleFind(String input, Taskman taskman) throws BezdelnikException {
        String toSearchFor = removeFirstWord(input);
        if (toSearchFor.isEmpty()) {
            throw new BezdelnikException("Please specify a search term");
        }
        Command toReturn = new FindCommand(taskman, toSearchFor);
        return toReturn.execute();

    }

    /**
     * Returns a Pair containing the list of tasks and the sorted task manager.
     *
     * @param taskman The current task manager state.
     * @return A Pair with a confirmation message and the task list as a formatted string and the sorted task manager.
     */
    private static Pair<String, Taskman> handleSort(Taskman taskman) throws BezdelnikException {
        Command toReturn = new SortCommand(taskman);
        return toReturn.execute();

    }

    private static Pair<String, Taskman> handleArchive(String input, Taskman taskman) throws BezdelnikException {
        String path = "./data/" + removeFirstWord(input);
        Command toReturn = new ArchiveCommand(taskman, path);
        return toReturn.execute();
    }

    /**
     * Returns a default response for unsupported commands.
     *
     * @param input   The unsupported user input command.
     * @param taskman The current task manager state.
     * @return A Pair with an error message and the unchanged task manager.
     */
    private static Pair<String, Taskman> handleDefault(String input, Taskman taskman) throws BezdelnikException {
        Command toReturn = new DefaultCommand(taskman, input);
        return toReturn.execute();
    }
}

package bezdelnik.utils;

import java.util.Arrays;
import java.util.stream.Collectors;
import bezdelnik.tasks.Task;
import bezdelnik.tasks.Todo;
import bezdelnik.tasks.Deadline;
import bezdelnik.tasks.Event;

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
        case "rem", "remove", "del", "delete" -> CommandType.REMOVE;
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
        try {
            String[] parts = input.split(" ");
            if (parts.length != 2) {
                throw createOutOfRangeException(taskman);
            }
            
            int idx = Integer.parseUnsignedInt(parts[1]) - 1;

            taskman = taskman.operate(idx, x -> x.markAsDone());
            String output = String.format("\tI have marked this task as done.\n\t%s", taskman.get(idx));
            return new Pair<String, Taskman>(output, taskman);
        } catch (NumberFormatException e) {
            throw createOutOfRangeException(taskman);
        } catch (IndexOutOfBoundsException iobe) {
            throw createOutOfRangeException(taskman);
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
        try {
            String[] parts = input.split(" ");
            if (parts.length != 2) {
                throw createOutOfRangeException(taskman);
            }

            int idx = Integer.parseUnsignedInt(parts[1]) - 1;

            taskman = taskman.operate(idx, x -> x.markAsUndone());
            String output = String.format("\tI have marked this task as undone.\n\t%s", taskman.get(idx));
            return new Pair<String, Taskman>(output, taskman);
        } catch (NumberFormatException e) {
            throw createOutOfRangeException(taskman);
        } catch (IndexOutOfBoundsException iobe) {
            throw createOutOfRangeException(taskman);
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
        try {
            String[] parts = input.split(" ");
            if (parts.length != 2) {
                throw createOutOfRangeException(taskman);
            }
            
            int idx = Integer.parseUnsignedInt(parts[1]) - 1;

            Task toDelete = taskman.get(idx);
            taskman = taskman.remove(idx);

            String output = String.format("\tI have deleted this task.\n\t%s", toDelete);
            return new Pair<String, Taskman>(output, taskman);
        } catch (NumberFormatException e) {
            throw createOutOfRangeException(taskman);
        } catch (IndexOutOfBoundsException iobe) {
            throw createOutOfRangeException(taskman);
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
            throw createTodoFormatException();
        }
        
        try {
            Task toAdd = new Todo(todoInput);
            taskman = taskman.add(toAdd);

            String output = String.format("\tadded:\n\t%s\n\tYou currently have %d task(s)", toAdd, taskman.size());
            return new Pair<String, Taskman>(output, taskman);
        } catch (Throwable t) {
            throw new BezdelnikException("Error creating todo task: " + t.getMessage());
        }
    }

    /**
     * Adds a new Deadline task based on the input command.
     *
     * @param input   The user input command.
     * @param taskman The current task manager state.
     * @return A Pair with a confirmation message and the updated task manager.
     */
    private static Pair<String, Taskman> handleDeadline(String input, Taskman taskman) throws BezdelnikException {
        try {
            String deadlineInput = removeFirstWord(input);
            if (deadlineInput.isEmpty()) {
                throw createDeadlineFormatException();
            }

            String[] array = deadlineInput.split(" /by ");
            if (array.length != 2 || array[0].trim().isEmpty()) {
                throw createDeadlineFormatException();
            }

            try {
                Task toAdd = new Deadline(array[0], array[1]);
                taskman = taskman.add(toAdd);
                String output = String.format("\tadded:\n\t%s\n\tYou currently have %d task(s)", toAdd, taskman.size());
                return new Pair<String, Taskman>(output, taskman);
            } catch (java.time.format.DateTimeParseException e) {
                throw createDeadlineFormatException();
            }
        } catch (Throwable t) {
            throw new BezdelnikException("Error creating deadline task: " + t.getMessage());
        }
    }

    /**
     * Adds a new Event task based on the input command.
     *
     * @param input   The user input command.
     * @param taskman The current task manager state.
     * @return A Pair with a confirmation message and the updated task manager.
     */
    private static Pair<String, Taskman> handleEvent(String input, Taskman taskman) throws BezdelnikException {
        try {
            String eventInput = removeFirstWord(input);
            if (eventInput.isEmpty()) {
                throw createEventFormatException();
            }

            String[] array = eventInput.split(" /");
            if (array.length != 3 || array[0].trim().isEmpty() || !array[1].startsWith("from ") || !array[2].startsWith("to ")) {
                throw createEventFormatException();
            }

            try {
                Task toAdd = new Event(array[0], array[1].substring(5), array[2].substring(3));
                taskman = taskman.add(toAdd);
                String output = String.format("\tadded:\n\t%s\n\tYou currently have %d task(s)", toAdd, taskman.size());
                return new Pair<String, Taskman>(output, taskman);
            } catch (java.time.format.DateTimeParseException e) {
                throw createEventFormatException();
            }
        } catch (Throwable t) {
            throw new BezdelnikException("Error creating event task: " + t.getMessage());
        }
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

        Taskman filtered = taskman.filter(x -> x.contains(toSearchFor));
        String matchCount = filtered.size() == 0 ? "No matches found" : 
                           String.format("Found %d matching task(s)", filtered.size());
        
        String output = String.format("\t%s:\n%s", matchCount, filtered.listString());
        return new Pair<String, Taskman>(output, taskman);
    }

    /**
     * Returns a Pair containing the list of tasks and the sorted task manager.
     *
     * @param taskman The current task manager state.
     * @return A Pair with a confirmation message and the task list as a formatted string and the sorted task manager.
     */
    private static Pair<String, Taskman> handleSort(Taskman taskman) throws BezdelnikException {
        if (taskman.size() == 0) {
            throw new BezdelnikException("No tasks to sort!");
        }
        
        taskman = taskman.sorted();
        String output = String.format("\t%d task(s) sorted by time:\n%s", taskman.size(), taskman.listString());
        return new Pair<String, Taskman>(output, taskman);
    }

    private static Pair<String, Taskman> handleArchive(String input, Taskman taskman) throws BezdelnikException {
        String path = "./data/" + removeFirstWord(input);
        try {
            Storage.writeTaskmanToFile(taskman, path);

            String output = String.format("\tTask list archived to: %s. You have turned over a new leaf.", path);
            return new Pair<String, Taskman>(output, new Taskman());
        } catch (Throwable t) {
            throw new BezdelnikException(String.format("Unknown error accessing path: %s.", path));
        }
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

    private static BezdelnikException createOutOfRangeException(Taskman taskman) {
        String errorMessage;
        if (taskman.size() == 0) {
            errorMessage = "No tasks to operate on!";
        } else {
            errorMessage = String.format(
                "Please provide a valid task number in the following range [1, %d]", taskman.size());
        }
        return new BezdelnikException(errorMessage);
    }

    private static BezdelnikException createEventFormatException() {
        return new BezdelnikException("Please use the format: event <description> /from dd/MM/yyyy HHmm /to dd/MM/yyyy HHmm");
    }

    private static BezdelnikException createTodoFormatException() {
        return new BezdelnikException("Please use the format: todo <description>");
    }

    private static BezdelnikException createDeadlineFormatException() {
        return new BezdelnikException("Please use the format: deadline <description> /by dd/MM/yyyy HHmm");
    }
}

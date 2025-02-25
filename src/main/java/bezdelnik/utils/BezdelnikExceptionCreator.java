package bezdelnik.utils;

/**
 * Utility class for creating standardized BezdelnikException instances.
 * <p>
 * This class provides factory methods to create exception objects with
 * for common error scenarios in the Bezdelnik application.
 * </p>
 */
public class BezdelnikExceptionCreator {

    /**
     * Creates an exception for when a task index is out of valid range.
     * <p>
     * Returns a tailored message based on whether the task list is empty
     * or the provided index is outside the valid range.
     * </p>
     *
     * @param taskman The Taskman instance containing the tasks
     * @return A BezdelnikException with an appropriate error message
     */
    public static BezdelnikException createOutOfRangeException(Taskman taskman) {
        String message;
        if (taskman.size() == 0) {
            message = "No tasks to operate on!";
        } else {
            message = String.format(
                "Please provide a valid task number in the following range [1, %d]", taskman.size());
        }
        return new BezdelnikException(message);
    }

    /**
     * Creates an exception for incorrect todo command format.
     *
     * @return A BezdelnikException with a message explaining the correct todo format
     */
    public static BezdelnikException createTodoFormatException() {
        String message = "Please use the format: todo <description>";
        return new BezdelnikException(message);
    }

    /**
     * Creates an exception for incorrect deadline command format.
     *
     * @return A BezdelnikException with a message explaining the correct deadline format
     */
    public static BezdelnikException createDeadlineFormatException() {
        String message = "Please use the format: deadline <description> /by dd/MM/yyyy HHmm";
        return new BezdelnikException(message);
    }

    /**
     * Creates an exception for incorrect event command format.
     *
     * @return A BezdelnikException with a message explaining the correct event format
     */
    public static BezdelnikException createEventFormatException() {
        String message = "Please use the format: event <description> /from dd/MM/yyyy HHmm /to dd/MM/yyyy HHmm";
        return new BezdelnikException(message);
    }
}

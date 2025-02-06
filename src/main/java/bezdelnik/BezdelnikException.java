package bezdelnik;

/**
 * Exception specific to the Bezdelnik application.
 * This exception is thrown when a task management error occurs.
 */
public class BezdelnikException extends Exception {

    /**
     * Constructs a new BezdelnikException with the specified detail message.
     *
     * @param message Detail message.
     */
    public BezdelnikException(String message) {
        super(message);
    }

    /**
     * Constructs a new BezdelnikException with the specified detail message and cause.
     *
     * @param message Detail message.
     * @param cause   The cause of the exception.
     */
    public BezdelnikException(String message, Throwable cause) {
        super(message, cause);
    }
}

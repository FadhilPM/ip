package bezdelnik.utils;

/**
 * Represents a command to be executed in the Bezdelnik system.
 * <p>
 * This interface defines the contract for all command implementations.
 * Each command must provide an implementation of the execute method,
 * which performs the command's logic and returns a result along with
 * a Taskman reference.
 * </p>
 */
public interface Command {

    /**
     * Executes the command and returns the result.
     *
     * @return A Pair containing a String result and a Taskman instance
     * @throws BezdelnikException if an error occurs during command execution
     */
    public Pair<String, Taskman> execute() throws BezdelnikException;
}

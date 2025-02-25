package bezdelnik.utils;

/**
 * Command implementation for denoting bad input
 * <p>
 * This class handles bad input by doing nothing to the task list
 * and informing the user.
 * </p>
 */
public class DefaultCommand implements Command {
    private final Taskman taskman;
    private final String badInput;

    DefaultCommand(Taskman taskman, String badInput) {
        this.taskman = taskman;
        this.badInput = badInput;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Pair<String, Taskman> execute() throws BezdelnikException {
        String output = String.format("\tUnsupported command: %s", badInput);
        return new Pair<String, Taskman>(output, taskman);
    }
}

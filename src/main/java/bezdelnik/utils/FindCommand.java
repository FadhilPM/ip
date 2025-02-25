package bezdelnik.utils;

/**
 * Command implementation for carrying out a find operation on Taskman
 * <p>
 * This class handles the creation of Event tasks in the Bezdelnik system.
 * It parses input data and creates a properly formatted Event task,
 * then adds it to the taskman collection.
 * </p>
 */
public class FindCommand implements Command {
    private final Taskman taskman;
    private final String toSearchFor;

    FindCommand(Taskman taskman, String toSearchFor) {
        this.taskman = taskman;
        this.toSearchFor = toSearchFor;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Pair<String, Taskman> execute() throws BezdelnikException {
        Taskman filtered = taskman.filter(x -> x.contains(this.toSearchFor));
        String matchCount;

        if (filtered.size() == 0) {
            matchCount = "No matches found";
        } else {
            matchCount = String.format("Found %d matching task(s)", filtered.size());
        }

        String output = String.format("\t%s:\n%s", matchCount, filtered.listString());
        return new Pair<String, Taskman>(output, taskman);
    }
}

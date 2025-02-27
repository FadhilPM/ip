package bezdelnik.utils;

import java.util.stream.Stream;

/**
 * Main controller for the Bezdelnik task management application.
 * <p>
 * Coordinates between UI, storage, and task management functionality.
 * Follows immutable design - all operations return new instances.
 * </p>
 */
public class Bezdelnik {
    private final Taskman taskman;
    private final String saveLocation;

    /**
     * Constructs a Bezdelnik with default task manager and save location.
     */
    public Bezdelnik() {
        this(new Taskman(), "./data/output.dat");
    }

    /**
     * Constructs a Bezdelnik with specified task manager and save location.
     *
     * @param taskman The task manager containing current tasks
     * @param saveLocation The file path for task storage
     */
    public Bezdelnik(Taskman taskman, String saveLocation) {
        this.taskman = taskman;
        this.saveLocation = saveLocation;
    }

    /**
     * Attempts to load task data from save location.
     *
     * @return A Pair containing status message and new Bezdelnik instance
     */
    public Pair<String, Bezdelnik> initialise() {
        Pair<String, Taskman> readAttempt;

        try {
            Stream<String> rawRead = ReadStorage.readTaskmanFromFile(saveLocation);
            readAttempt = streamToTaskman(rawRead, saveLocation);

        } catch (Throwable e) {
            readAttempt = new Pair<String, Taskman>("No prior data found, creating new session", new Taskman());
        }

        Taskman newTaskman = readAttempt.second();
        return new Pair<String, Bezdelnik>(readAttempt.first(), new Bezdelnik(newTaskman, saveLocation));
    }

    /**
     * Processes user input command and returns response.
     *
     * @param input The user input command
     * @return A Pair containing response message and updated Bezdelnik instance
     */
    public Pair<String, Bezdelnik> getResponse(String input) {
        String response;
        Taskman newTaskman;
        try {
            Command parserOutput = Parser.parse(input, taskman);
            Pair<String, Taskman> executionOutput = parserOutput.execute();

            response = executionOutput.first();
            newTaskman = executionOutput.second();
        } catch (BezdelnikException be) {
            newTaskman = this.taskman;
            response = be.getMessage();
        }

        try {
            WriteStorage.writeTaskmanToFile(newTaskman, saveLocation);
        } catch (Throwable e) {
            System.out.println(String.format("Unknown exception when saving data.", e.toString()));
        }

        return new Pair<String, Bezdelnik>(response, new Bezdelnik(newTaskman, saveLocation));
    }

    /**
     * Converts command string stream into a Taskman instance.
     *
     * @param st Stream of command strings
     * @param saveLocation Save location for error reporting
     * @return A Pair containing status message and resulting Taskman
     */
    private Pair<String, Taskman> streamToTaskman(Stream<String> st, String saveLocation) {
        Taskman toReturn = st
            .reduce(new Taskman(), (x, y) -> {
                try {
                    return Parser.parse(y, x).execute().second();
                } catch (BezdelnikException be) {
                    return new Taskman();
                }
            }, (a, b) -> a.concat(b));

        String status = String.format("Success: %d tasks successfully loaded from %s\n%s",
                                      toReturn.size(), saveLocation, toReturn.listString());

        return new Pair<String, Taskman>(status, toReturn);
    }
}

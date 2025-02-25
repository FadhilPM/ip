package bezdelnik.utils;

import java.util.stream.Stream;

public class Bezdelnik {
    private final Taskman taskman;
    private final String saveLocation;

    public Bezdelnik() {
        this(new Taskman(), "./data/output.dat");
    }

    public Bezdelnik(Taskman taskman, String saveLocation) {
        this.taskman = taskman;
        this.saveLocation = saveLocation;
    }

    /**
     * Attempts to load data from save location.
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
            WriteStorage.writeTaskmanToFile(taskman, saveLocation);
        } catch (Throwable e) {
            System.out.println(String.format("Unknown exception when saving data.", e.toString()));
        }

        return new Pair<String, Bezdelnik>(response, new Bezdelnik(newTaskman, saveLocation));
    }

    private Pair<String, Taskman> streamToTaskman(Stream<String> st, String saveLocation) {
        Taskman toReturn = st
            .reduce(new Taskman(),
                    (x, y) -> {
                        try {
                            return Parser.parse(y, x).execute().second();
                        } catch (BezdelnikException be) {
                            return new Taskman();
                        }
                    },
                    (a, b) -> a.concat(b));

            String status = String.format("Success: %d tasks successfully loaded from %s\n%s",
                                          toReturn.size(), saveLocation, toReturn.listString());
        return new Pair<String, Taskman>(status, toReturn);
    }
}

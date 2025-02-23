package bezdelnik;

public class Bezdelnik {
    private final Taskman taskman;
    private final String saveLocation;

    Bezdelnik() {
        this(new Taskman(), "./data/output.dat");
    }

    Bezdelnik(Taskman taskman, String saveLocation) {
        this.taskman = taskman;
        this.saveLocation = saveLocation;
    }

    /**
     * Attempts to load data from save location.
     */
    public Pair<String, Bezdelnik> initialise() {
        Pair<String, Taskman> readAttempt;
        try {
            readAttempt = Storage.readTaskmanFromFile(saveLocation);
        } catch (Throwable e) {
            readAttempt = new Pair<String, Taskman>("No prior data found, creating new session", new Taskman());
        }
        Taskman newTaskman = readAttempt.second();
        return new Pair<String, Bezdelnik>(readAttempt.first(), new Bezdelnik(newTaskman, saveLocation));
    }

    public Pair<String, Bezdelnik> getResponse(String input) {
        Pair<String, Taskman> parserOutput = Parser.parse(input, taskman);
        String response = parserOutput.first();
        Taskman newTaskman = parserOutput.second();
        try {
            Storage.writeTaskmanToFile(taskman, saveLocation);
        } catch (Throwable e) {
            System.out.println(String.format("Unknown exception when saving data.", e.toString()));
        }
        return new Pair<String, Bezdelnik>(response, new Bezdelnik(newTaskman, saveLocation));
    }
}

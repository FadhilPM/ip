package bezdelnik;

/**
 * Main entry point for chatbot
 * Initializes task manager, loads prior data if present,
 * handles the user input loop.
 */
public class Bezdelnik {
    private static Taskman taskman = new Taskman();
    private static final String saveLocation = "./data/output.dat";

    /**
     * Main entry point to the application.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        Pair<String, Taskman> readAttempt;
        try {
            readAttempt = Storage.readTaskmanFromFile(saveLocation);
        } catch (Throwable e) {
            readAttempt = new Pair<String, Taskman>("        No prior data found, creating new session", new Taskman());
        }
        inputLoop(readAttempt.first());
    }

    /**
     * Processes the user input loop.
     *
     * @param sessionStatus Initial session status message.
     */
    private static void inputLoop(String sessionStatus) {
        Ui.greet(sessionStatus);
        Ui.takeInput()
            .map(input -> Parser.parse(input, taskman))
            .forEach(pair -> {
                Ui.print(pair.first());
                taskman = pair.second();
                try {
                    Storage.writeTaskmanToFile(taskman, saveLocation);
                } catch (Throwable e) {
                    Ui.print(String.format("Unknown exception when saving data.", e.toString()));
                }
            }
            );
        Ui.bye();
    }
}

package bezdelnik;

/**
 * Main entry point to chatbot
 *
 */
public class Bezdelnik {
    private static Taskman taskman = new Taskman();
    private static final String saveLocation = "./data/output.dat";

    public static void main(String[] args) {
        Pair<String, Taskman> readAttempt;
        try {
            readAttempt = Storage.readTaskmanFromFile(saveLocation);
        } catch (Throwable e) {
            readAttempt = new Pair<String, Taskman>("        No prior data found, creating new session", new Taskman());
        }
        inputLoop(readAttempt.first());
    }

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

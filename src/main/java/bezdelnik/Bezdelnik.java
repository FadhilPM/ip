package bezdelnik;

import java.util.Scanner;

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
        Bezdelnik bezdelnik = new Bezdelnik();
        bezdelnik.run();
    }

    public void run() {
        inputLoop(initialise());
    }

    /**
     * Attempts to load data from save location.
     */
    public String initialise() {
        Pair<String, Taskman> readAttempt;
        try {
            readAttempt = Storage.readTaskmanFromFile(saveLocation);
        } catch (Throwable e) {
            readAttempt = new Pair<String, Taskman>("No prior data found, creating new session", new Taskman());
        }
        taskman = readAttempt.second();
        return readAttempt.first();
    }

    /**
     * Processes the user input loop.
     *
     * @param sessionStatus Initial session status message.
     */
    private void inputLoop(String sessionStatus) {
        Ui.greet(sessionStatus);
        Scanner sc = new Scanner(System.in);
        sc.useDelimiter("\n")
            .tokens()
            .map(input -> input.strip())
            .takeWhile(input -> !input.matches("(bye|(/)?ex(it)?)"))
            .forEach(input -> Ui.print(getResponse(input)));
        sc.close();
        Ui.bye();
    }

    public String getResponse(String input) {
        Pair<String, Taskman> parserOutput = Parser.parse(input, taskman);
        taskman = parserOutput.second();
        try {
            Storage.writeTaskmanToFile(taskman, saveLocation);
        } catch (Throwable e) {
            Ui.print(String.format("Unknown exception when saving data.", e.toString()));
        }
        return parserOutput.first();
    }
}

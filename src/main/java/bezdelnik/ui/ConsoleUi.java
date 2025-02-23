package bezdelnik.ui;

import java.util.Scanner;

import bezdelnik.utils.Bezdelnik;
import bezdelnik.utils.Pair;

/**
 * Handles UI operations in CLI
 */
public class ConsoleUi {
    private static final String divider = "_".repeat(100);
    private static final String logo =
        " _____   _____   _____     _____     _____    ____    _       _   _   _     _   _   _ \n"
        + "|  ___| |  ___| |___  |   |  _  |   |  ___|  |  _ \\  | |     | | | | | |   / | | | / / \n"
        + "| |___  | |___     _| |   | | | |   | |___   | | | | | |___  | |_| | | |  // | | |/ /\n"
        + "|  _  | |  ___|   |_  |  _| |_| |_  |  ___|  | | | | |  _  | |  _  | | | //| | |   (\n"
        + "| |_| | | |___   ___| | |  _____  | | |___   | | | | | |_| | | | | | | |// | | | |\\ \\\n"
        + "|_____| |_____| |_____| |_|     |_| |_____|  /_/ |_| |_____| |_| |_| |_ /  |_| |_| \\_\\ ";
    private static final String greeting = String.format("%s\nHello from\n%s\n\nWhat can I do for you?\n%s",
                                                         "_".repeat(104), logo, "_".repeat(104));
    private static Bezdelnik bezdelnik = new Bezdelnik();

    public static void main(String[] args) {
        Pair<String, Bezdelnik> initialisedBezdelnik = bezdelnik.initialise();
        String response = initialisedBezdelnik.first();
        bezdelnik = initialisedBezdelnik.second();
        inputLoop(response);
    }

    /**
     * Processes the user input loop.
     *
     * @param sessionStatus Initial session status message.
     */
    private static void inputLoop(String sessionStatus) {
        ConsoleUi.greet(sessionStatus);
        Scanner sc = new Scanner(System.in);
        sc.useDelimiter("\n")
            .tokens()
            .map(input -> input.strip())
            .takeWhile(input -> !input.matches("(bye|(/)?ex(it)?)"))
            .forEach(input -> {
                Pair<String, Bezdelnik> response = bezdelnik.getResponse(input);
                ConsoleUi.print(response.first());
                bezdelnik = response.second();
            });
        sc.close();
        ConsoleUi.bye();
    }

    /**
     * Displays a greeting message along with the session status indicating if a data file was read
     *
     * @param sessionStatus The initial session status message.
     */
    private static void greet(String sessionStatus) {
        System.out.println(greeting);
        System.out.println(sessionStatus);
    }

    private static void bye() {
        print("\tBye. Hope to see you again soon!");
    }

    /**
     * Prints the specified output message in a formatted manner.
     *
     * @param output The message to be printed.
     */
    private static void print(String output) {
        System.out.println(responseFormat(output));
    }

    /**
     * Formats the response with dividers.
     *
     * @param input The input message to format.
     * @return A formatted string with dividers.
     */
    private static String responseFormat(String input) {
        return String.format("\t%s\n%s\n\t%s", divider, input, divider);
    }
}

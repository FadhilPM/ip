package bezdelnik;

import java.util.Scanner;
import java.util.stream.Stream;

/**
 * Handles UI operations
 */
class Ui {
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

    /**
     * Displays a greeting message along with the session status indicating if a data file was read
     *
     * @param sessionStatus The initial session status message.
     */
    public static void greet(String sessionStatus) {
        System.out.println(greeting);
        System.out.println(sessionStatus);
    }

    public static void bye() {
        print("\tBye. Hope to see you again soon!");
    }

    /**
     * Prints the specified output message in a formatted manner.
     *
     * @param output The message to be printed.
     */
    public static void print(String output) {
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

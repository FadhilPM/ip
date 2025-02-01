package bezdelnik;

import java.util.Scanner;
import java.util.stream.Stream;

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
    public static void greet(String sessionStatus) {
        System.out.println(greeting);
        System.out.println(sessionStatus);
    }

    public static void bye() {
        print("\tBye. Hope to see you again soon!");
    }

    public static void print(String output) {
        System.out.println(responseFormat(output));
    }

    public static Stream<String> takeInput() {
        // doesn't lead to memory leak because this scanner's closure coincides with the application closing.
        Scanner sc = new Scanner(System.in);
        return sc.useDelimiter("\n")
            .tokens()
            .map(input -> input.strip())
            .takeWhile(input -> !input.matches("(bye|(/)?ex(it)?)"));
    }

    private static String responseFormat(String input) {
        return String.format("\t%s\n%s\n\t%s", divider, input, divider);
    }
}

import java.util.Scanner;

/**
 * Main entry point to chatbot
 *
 */
public class Bezdelnik {
    private static Taskman taskman = new Taskman();
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

    public static void main(String[] args) {
        System.out.println(greeting);
        inputLoop();
        System.out.println(responseFormat("\tBye. Hope to see you again soon!"));
    }

    private static void inputLoop() {
        Scanner sc = new Scanner(System.in);
        sc.useDelimiter("\n")
            .tokens()
            .map(input -> input.strip())
            .takeWhile(input -> !input.matches("(bye|(/)?ex(it)?)"))
            .map(input -> CommandParser.parse(input, taskman))
            .forEach(pair -> {
                        System.out.println(responseFormat(pair.first()));
                        taskman = pair.second();
                    }
            );
        sc.close();
    }

    private static String responseFormat(String input) {
        return String.format("\t%s\n%s\n\t%s", divider, input, divider);
    }

    @Override
    public String toString() {
        return String.format("%s%s", "Bezdelnik\n", taskman.toString());
    }
}

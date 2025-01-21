import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.IntStream;

public class Bezdelnik {
    private static List<Task> taskList = new ArrayList<Task>();
    private static final String divider = "_".repeat(100);
    private static final String logo = " _____   _____   _____     _____     _____    ____    _       _   _   _     _   _   _ \n"
                   + "|  ___| |  ___| |___  |   |  _  |   |  ___|  |  _ \\  | |     | | | | | |   / | | | / / \n"
                  + "| |___  | |___     _| |   | | | |   | |___   | | | | | |___  | |_| | | |  // | | |/ /\n"
                  + "|  _  | |  ___|   |_  |  _| |_| |_  |  ___|  | | | | |  _  | |  _  | | | //| | |   (\n"
                  + "| |_| | | |___   ___| | |  _____  | | |___   | | | | | |_| | | | | | | |// | | | |\\ \\\n"
                  + "|_____| |_____| |_____| |_|     |_| |_____|  /_/ |_| |_____| |_| |_| |_ /  |_| |_| \\_\\ ";

    public static void main(String[] args) {
        String greeting = String.format("%s\nHello from\n%s\n\nWhat can I do for you?\n%s",
                "_".repeat(104), logo, "_".repeat(104));
        System.out.println(greeting);
        inputLoop();
        System.out.println(responseFormat("\tBye. Hope to see you again soon!"));
    }

    private static void inputLoop() {
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        while (stringParser(input)) {
            input = sc.nextLine();
        }
        sc.close();
    }

    private static boolean stringParser(String input) {
        if (input.equals("bye")) {
            return false;
        } else if (input.equals("list")) {
            String output = IntStream.range(0, taskList.size())
                .mapToObj(x -> String.format("\t%d. %s", x + 1, taskList.get(x).toString()))
                .reduce((a, b) -> a + "\n" + b)
                .orElse("No tasks present!");
            System.out.println(responseFormat(output));
            return true;
        }
        Task toAdd = new Task(input);
        taskList.add(toAdd);
        System.out.println(responseFormat("\tadded: " + input));
        return true;
    }

    private static String responseFormat(String input) {
        return String.format("\t%s\n%s\n\t%s", divider, input, divider);
    }
}

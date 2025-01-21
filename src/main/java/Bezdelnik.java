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
        String input; // can get a NoSuchElementException if Ctrl+D is provided as input???? find out why!
        do {
            input = sc.nextLine();
        } while (stringParser(input));
        sc.close();
    }

    private static boolean stringParser(String input) {
        if (input.matches("bye.*") || input.matches("/ex.*")) {
            return false;
        } else if (input.matches("list.*")) {
            String output = IntStream.range(0, taskList.size())
                .mapToObj(x -> String.format("\t%d. %s", x + 1, taskList.get(x).toString()))
                .reduce((a, b) -> a + "\n" + b)
                .orElse("\tNo tasks present!");
            System.out.println(responseFormat(output));
            return true;
        } else if (input.startsWith("mark")) {
            try {
                int x = Integer.parseUnsignedInt(input.split(" ")[1]) - 1;
                Task newTask = taskList.get(x).markAsDone();
                taskList.set(x, newTask);
                System.out.println(responseFormat(
                    String.format("\tI have marked this task as done.\n\t%s", newTask.toString())));
            } catch (NumberFormatException n) {
                System.out.println(responseFormat("\tI'm sorry, that was not a valid integer you specified. Try again!"));
            } catch (IndexOutOfBoundsException i) {
                System.out.println(responseFormat(
                    String.format("\tInvalid index! Use an integer in [1,%d]", taskList.size())));
            }
        } else if (input.startsWith("unmark")) {
            try {
                int x = Integer.parseUnsignedInt(input.split(" ")[1]) - 1;
                Task newTask = taskList.get(x).markAsUndone();
                taskList.set(x, newTask);
                System.out.println(responseFormat(
                    String.format("\tI have marked this task as undone.\n\t%s", newTask.toString())));
            } catch (NumberFormatException n) {
                System.out.println(responseFormat("\tI'm sorry, that was not a valid integer you specified. Try again!"));
            } catch (IndexOutOfBoundsException i) {
                System.out.println(responseFormat(
                    String.format("\tInvalid index! Use an integer in [1,%d]", taskList.size())));
            }
        } else {
            Task toAdd = new Task(input);
            taskList.add(toAdd);
            System.out.println(responseFormat("\tadded: " + input));
        }

        return true;
    }

    private static String responseFormat(String input) {
        return String.format("\t%s\n%s\n\t%s", divider, input, divider);
    }
}

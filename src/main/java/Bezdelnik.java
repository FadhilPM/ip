import java.util.Scanner;
import java.util.stream.Stream;
//import java.util.Optional;
import java.util.NoSuchElementException;

public class Bezdelnik {
    //private static List<Task> taskList = new ArrayList<Task>();
    private static Taskman taskList = new Taskman();
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
        Stream.generate(() -> {
                try {
                    return sc.nextLine().strip();
                } catch (NoSuchElementException e) {
                    return "bye";
                }
            })
            .takeWhile(inpt -> stringParser(inpt)) // stop having your stringParser return a boolean
            .forEach(inpt -> {});
        sc.close();

    }

    //should probably be its own class...
    //incredibly ugly string parsing within
    private static boolean stringParser(String input) {
        if (input.matches("bye") || input.matches("(/)?ex(it)?")) {
            return false;
        } else if (input.matches("(list|ls)")) {
            System.out.println(responseFormat(taskList.toString()));
        } else if (input.matches("mark(\s.*)?")) {
            try {
                int i = Integer.parseUnsignedInt(input.split(" ")[1]) - 1;
                taskList = taskList.operate(i, x -> x.markAsDone());
                System.out.println(responseFormat(
                    String.format("\tI have marked this task as done.\n\t%s", taskList.get(i).toString())));
            } catch (NumberFormatException n) {
                System.out.println(responseFormat("\tI'm sorry, that was not a valid integer you specified. Try again!"));
            } catch (IndexOutOfBoundsException i) {
                System.out.println(responseFormat(
                    String.format("\tInvalid index! Use an integer in [1,%d]", taskList.size())));
            }
        } else if (input.matches("unmark(\s.*)?")) {
            try {
                int i = Integer.parseUnsignedInt(input.split(" ")[1]) - 1;
                taskList = taskList.operate(i, x -> x.markAsUndone());
                System.out.println(responseFormat(
                    String.format("\tI have marked this task as undone.\n\t%s", taskList.get(i).toString())));
            } catch (NumberFormatException n) {
                System.out.println(responseFormat("\tI'm sorry, that was not a valid integer you specified. Try again!"));
            } catch (IndexOutOfBoundsException i) {
                System.out.println(responseFormat(
                    String.format("\tInvalid index! Use an integer in [1,%d]", taskList.size())));
            }
        } else if (input.matches("delete(\s.*)?")) {
            try {
                int i = Integer.parseUnsignedInt(input.split(" ")[1]) - 1;
                Task toDelete = taskList.get(i);
                taskList = taskList.remove(i);
                // the following allows for conditional deletion of tasks with minimal code additions later down the line
                //taskList = taskList.operateOptional(i, x -> Optional.<Task>empty());
                System.out.println(responseFormat(
                    String.format("\tI have deleted this task.\n\t%s", toDelete.toString())));
            } catch (NumberFormatException n) {
                System.out.println(responseFormat("\tI'm sorry, that was not a valid integer you specified. Try again!"));
            } catch (IndexOutOfBoundsException i) {
                System.out.println(responseFormat(
                    String.format("\tInvalid index! Use an integer in [1,%d]", taskList.size())));
            }
        } else if (input.matches("todo(\s.*)?")) {
            input = input.substring(5, input.length());
            if (input.equals("")) {
                System.out.println(responseFormat("\ttodo must be followed with something to do!"));
            } else {
                Task toAdd = new Todo(input);
                taskList = taskList.add(toAdd);
                System.out.println(responseFormat(
                    String.format("\tadded:\n\t%s\n\tYou currently have %d task(s)", toAdd.toString(), taskList.size())));
            }
        } else if (input.matches("deadline(\s.*)?")) {
            input = input.substring(9, input.length());
            String[] array = input.split(" /by ");
            Task toAdd = new Deadline(array[0], array[1]);
            taskList = taskList.add(toAdd);
            System.out.println(responseFormat(
                String.format("\tadded:\n\t%s\n\tYou currently have %d task(s)", toAdd.toString(), taskList.size())));
        } else if (input.matches("event(\s.*)?")) {
            input = input.substring(6, input.length());
            String[] array = input.split(" /");
            String description = array[0];
            String from = array[1];
            from = from.substring(5, from.length());
            String to = array[2];
            to = to.substring(3, to.length());
            Task toAdd = new Event(description, from, to);
            taskList = taskList.add(toAdd);
            System.out.println(responseFormat(
                String.format("\tadded:\n\t%s\n\tYou currently have %d task(s)", toAdd.toString(), taskList.size())));
        } else {
            System.out.println(responseFormat(String.format("\tUnsupported command: %s", input)));
        }
        return true;
    }

    private static String responseFormat(String input) {
        return String.format("\t%s\n%s\n\t%s", divider, input, divider);
    }
}

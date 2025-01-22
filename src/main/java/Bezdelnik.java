import java.util.Scanner;
import java.util.Optional;

record Pair<T, U>(T first, U second) {};

public class Bezdelnik {
    private static Taskman taskList = new Taskman();
    private static final String divider = "_".repeat(100);
    private static final String logo = " _____   _____   _____     _____     _____    ____    _       _   _   _     _   _   _ \n"
                   + "|  ___| |  ___| |___  |   |  _  |   |  ___|  |  _ \\  | |     | | | | | |   / | | | / / \n"
                  + "| |___  | |___     _| |   | | | |   | |___   | | | | | |___  | |_| | | |  // | | |/ /\n"
                  + "|  _  | |  ___|   |_  |  _| |_| |_  |  ___|  | | | | |  _  | |  _  | | | //| | |   (\n"
                  + "| |_| | | |___   ___| | |  _____  | | |___   | | | | | |_| | | | | | | |// | | | |\\ \\\n"
                  + "|_____| |_____| |_____| |_|     |_| |_____|  /_/ |_| |_____| |_| |_| |_ /  |_| |_| \\_\\ ";
    private static boolean continueLoop;

    public static void main(String[] args) {
        String greeting = String.format("%s\nHello from\n%s\n\nWhat can I do for you?\n%s",
                "_".repeat(104), logo, "_".repeat(104));
        System.out.println(greeting);
        continueLoop = true;
        inputLoop();
        System.out.println(responseFormat("\tBye. Hope to see you again soon!"));
    }

    private static void inputLoop() {
        Scanner sc = new Scanner(System.in);
        while (continueLoop) {
            String input = sc.nextLine().strip();
            stringParser(input, taskList).ifPresentOrElse(
                pair -> {
                    System.out.println(responseFormat(pair.first()));
                    taskList = pair.second();
                },
                () -> {
                     continueLoop = false;
                }
            );
        }
        sc.close();
    }

    //should probably be its own class...
    //incredibly ugly string parsing within
    // input: String, Taskman
    // output: Optional<Pair<String, Taskman>>
    private static Optional<Pair<String, Taskman>> stringParser(String input, Taskman taskman) {
        String toReturn;
        Taskman newTaskman = taskman;
        if (input.matches("bye") || input.matches("(/)?ex(it)?")) {
            return Optional.empty();
        } else if (input.matches("(list|ls)")) {
            toReturn = taskList.toString();
        } else if (input.matches("mark(\s.*)?")) {
            try {
                int i = Integer.parseUnsignedInt(input.split(" ")[1]) - 1;
                newTaskman = newTaskman.operate(i, x -> x.markAsDone());
                toReturn = String.format("\tI have marked this task as done.\n\t%s", newTaskman.get(i).toString());
            } catch (NumberFormatException n) {
                toReturn = "\tI'm sorry, that was not a valid integer you specified. Try again!";
            } catch (IndexOutOfBoundsException i) {
                toReturn = String.format("\tInvalid index! Use an integer in [1,%d]", newTaskman.size());
            }
        } else if (input.matches("unmark(\s.*)?")) {
            try {
                int i = Integer.parseUnsignedInt(input.split(" ")[1]) - 1;
                newTaskman = newTaskman.operate(i, x -> x.markAsUndone());
                toReturn = String.format("\tI have marked this task as undone.\n\t%s", newTaskman.get(i).toString());
            } catch (NumberFormatException n) {
                toReturn = "\tI'm sorry, that was not a valid integer you specified. Try again!";
            } catch (IndexOutOfBoundsException i) {
                toReturn = String.format("\tInvalid index! Use an integer in [1,%d]", newTaskman.size());
            }
        } else if (input.matches("delete(\s.*)?")) {
            try {
                int i = Integer.parseUnsignedInt(input.split(" ")[1]) - 1;
                Task toDelete = newTaskman.get(i);
                newTaskman = newTaskman.remove(i);
                // the following allows for conditional deletion of tasks with minimal code additions later down the line
                //taskList = taskList.operateOptional(i, x -> Optional.<Task>empty());
                toReturn = String.format("\tI have deleted this task.\n\t%s", toDelete.toString());
            } catch (NumberFormatException n) {
                toReturn = "\tI'm sorry, that was not a valid integer you specified. Try again!";
            } catch (IndexOutOfBoundsException i) {
                toReturn = String.format("\tInvalid index! Use an integer in [1,%d]", newTaskman.size());
            }
        } else if (input.matches("todo(\s.*)?")) {
            input = input.substring(5, input.length());
            if (input.equals("")) {
                toReturn = "\ttodo must be followed with something to do!";
            } else {
                Task toAdd = new Todo(input);
                newTaskman = newTaskman.add(toAdd);
                toReturn = String.format("\tadded:\n\t%s\n\tYou currently have %d task(s)", toAdd.toString(), newTaskman.size());
            }
        } else if (input.matches("deadline(\s.*)?")) {
            input = input.substring(9, input.length());
            String[] array = input.split(" /by ");
            Task toAdd = new Deadline(array[0], array[1]);
            newTaskman = newTaskman.add(toAdd);
            toReturn = String.format("\tadded:\n\t%s\n\tYou currently have %d task(s)", toAdd.toString(), newTaskman.size());
        } else if (input.matches("event(\s.*)?")) {
            input = input.substring(6, input.length());
            String[] array = input.split(" /");
            String description = array[0];
            String from = array[1];
            from = from.substring(5, from.length());
            String to = array[2];
            to = to.substring(3, to.length());
            Task toAdd = new Event(description, from, to);
            newTaskman = newTaskman.add(toAdd);
            toReturn = String.format("\tadded:\n\t%s\n\tYou currently have %d task(s)", toAdd.toString(), newTaskman.size());
        } else {
            toReturn = String.format("\tUnsupported command: %s", input);
        }
        return Optional.of(new Pair<String, Taskman>(toReturn, newTaskman));
    }

    private static String responseFormat(String input) {
        return String.format("\t%s\n%s\n\t%s", divider, input, divider);
    }

    @Override
    public String toString() {
        return String.format("%s%s", "Bezdelnik\n", taskList.toString());
    }
}

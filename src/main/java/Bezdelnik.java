import java.util.Scanner;

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

    public static void main(String[] args) {
        String greeting = String.format("%s\nHello from\n%s\n\nWhat can I do for you?\n%s",
                "_".repeat(104), logo, "_".repeat(104));
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
            .map(input -> stringParser(input, taskList))
            .forEach(pair ->  {
                    System.out.println(responseFormat(pair.first()));
                    taskList = pair.second();
                }
            );
        sc.close();
    }

    //should probably be its own class...
    //incredibly ugly string parsing within
    private static Pair<String, Taskman> stringParser(String input, Taskman taskman) {
        String toReturn;
        Taskman newTaskman = taskman;
        try {
            switch (input.split(" ")[0]) {
                case "list", "ls" -> {
                    toReturn = taskList.toString();
                }
                case "mark" -> {
                    int i = Integer.parseUnsignedInt(input.split(" ")[1]) - 1;
                    newTaskman = newTaskman.operate(i, x -> x.markAsDone());
                    toReturn = String.format("\tI have marked this task as done.\n\t%s", newTaskman.get(i).toString());
                }
                case "unmark" -> {
                    int i = Integer.parseUnsignedInt(input.split(" ")[1]) - 1;
                    newTaskman = newTaskman.operate(i, x -> x.markAsUndone());
                    toReturn = String.format("\tI have marked this task as undone.\n\t%s", newTaskman.get(i).toString());
                }
                case "delete" -> {
                    int i = Integer.parseUnsignedInt(input.split(" ")[1]) - 1;
                    Task toDelete = newTaskman.get(i);
                    newTaskman = newTaskman.remove(i);
                    toReturn = String.format("\tI have deleted this task.\n\t%s", toDelete.toString());
                }
                case "todo" -> {
                    String todoInput = input.substring(5);
                    if (todoInput.isEmpty()) {
                        toReturn = "\ttodo must be followed with something to do!";
                    } else {
                        Task toAdd = new Todo(todoInput);
                        newTaskman = newTaskman.add(toAdd);
                        toReturn = String.format("\tadded:\n\t%s\n\tYou currently have %d task(s)", toAdd.toString(), newTaskman.size());
                    }
                }
                case "deadline" -> {
                    String deadlineInput = input.substring(9);
                    String[] array = deadlineInput.split(" /by ");
                    Task toAdd = new Deadline(array[0], array[1]);
                    newTaskman = newTaskman.add(toAdd);
                    toReturn = String.format("\tadded:\n\t%s\n\tYou currently have %d task(s)", toAdd.toString(), newTaskman.size());
                }
                case "event" -> {
                    String eventInput = input.substring(6);
                    String[] array = eventInput.split(" /");
                    Task toAdd = new Event(array[0], array[1].substring(5), array[2].substring(3));
                    newTaskman = newTaskman.add(toAdd);
                    toReturn = String.format("\tadded:\n\t%s\n\tYou currently have %d task(s)", toAdd.toString(), newTaskman.size());
                }
                default -> {
                    toReturn = String.format("\tUnsupported command: %s", input);
                }
            }
        } catch (NumberFormatException n) {
            toReturn = "\tI'm sorry, that was not a valid integer you specified. Try again!";
        } catch (IndexOutOfBoundsException i) {
            toReturn = String.format("\tInvalid command parameters!");
        }
        return new Pair<String, Taskman>(toReturn, newTaskman);
    }


    private static String responseFormat(String input) {
        return String.format("\t%s\n%s\n\t%s", divider, input, divider);
    }

    @Override
    public String toString() {
        return String.format("%s%s", "Bezdelnik\n", taskList.toString());
    }
}

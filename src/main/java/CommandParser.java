class CommandParser {
    public static Pair<String, Taskman> parse(String input, Taskman taskman) {
        String toReturn;
        Taskman newTaskman = taskman;
        try {
            switch (input.split(" ")[0]) {
                case "list", "ls" -> {
                    toReturn = newTaskman.toString();
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
}

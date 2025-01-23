class CommandParser {
    public static Pair<String, Taskman> parse(String input, Taskman taskman) {
        String toReturn;
        try {
            switch (input.split(" ")[0]) {
                case "list", "ls" -> {
                    toReturn = taskman.listString();
                }
                case "mark" -> {
                    int i = Integer.parseUnsignedInt(input.split(" ")[1]) - 1;
                    taskman = taskman.operate(i, x -> x.markAsDone());
                    toReturn = String.format("\tI have marked this task as done.\n\t%s", taskman.get(i).toString());
                }
                case "unmark" -> {
                    int i = Integer.parseUnsignedInt(input.split(" ")[1]) - 1;
                    taskman = taskman.operate(i, x -> x.markAsUndone());
                    toReturn = String.format("\tI have marked this task as undone.\n\t%s", taskman.get(i).toString());
                }
                case "delete" -> {
                    int i = Integer.parseUnsignedInt(input.split(" ")[1]) - 1;
                    Task toDelete = taskman.get(i);
                    taskman = taskman.remove(i);
                    toReturn = String.format("\tI have deleted this task.\n\t%s", toDelete.toString());
                }
                case "todo" -> {
                    String todoInput = input.substring(5);
                    if (todoInput.isEmpty()) {
                        toReturn = "\ttodo must be followed with something to do!";
                    } else {
                        Task toAdd = new Todo(todoInput);
                        taskman = taskman.add(toAdd);
                        toReturn = String.format("\tadded:\n\t%s\n\tYou currently have %d task(s)", toAdd.toString(), taskman.size());
                    }
                }
                case "deadline" -> {
                    String deadlineInput = input.substring(9);
                    String[] array = deadlineInput.split(" /by ");
                    Task toAdd = new Deadline(array[0], array[1]);
                    taskman = taskman.add(toAdd);
                    toReturn = String.format("\tadded:\n\t%s\n\tYou currently have %d task(s)", toAdd.toString(), taskman.size());
                }
                case "event" -> {
                    String eventInput = input.substring(6);
                    String[] array = eventInput.split(" /");
                    Task toAdd = new Event(array[0], array[1].substring(5), array[2].substring(3));
                    taskman = taskman.add(toAdd);
                    toReturn = String.format("\tadded:\n\t%s\n\tYou currently have %d task(s)", toAdd.toString(), taskman.size());
                }
                default -> {
                    toReturn = String.format("\tUnsupported command: %s", input);
                }
            }
        } catch (NumberFormatException n) {
            toReturn = "\tInvalid integer!";
        } catch (IndexOutOfBoundsException i) {
            toReturn = String.format("\tInvalid command parameters!");
        }
        return new Pair<String, Taskman>(toReturn, taskman);
    }
}

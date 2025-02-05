package bezdelnik;

class Parser {
    public static Pair<String, Taskman> parse(String input, Taskman taskman) {
        try {
            String command = input.split(" ")[0];
            switch (command) {
            case "ls":
            case "list":
                return handleList(taskman);
            case "m":
            case "mark":
                return handleMark(input, taskman);
            case "u":
            case "unmark":
                return handleUnmark(input, taskman);
            case "rem":
            case "remove":
            case "del":
            case "delete":
                return handleRemove(input, taskman);
            case "t":
            case "todo":
                return handleTodo(input, taskman);
            case "d":
            case "deadline":
                return handleDeadline(input, taskman);
            case "event":
                return handleEvent(input, taskman);
            case "f":
            case "find":
                return handleFind(input, taskman);
            default:
                return handleDefault(input, taskman);
            }
        } catch (NumberFormatException n) {
            return new Pair<String, Taskman>("\tInvalid integer!", taskman);
        } catch (IndexOutOfBoundsException i) {
            return new Pair<String, Taskman>(String.format("\tInvalid command parameters!"), taskman);
        } catch (BezdelnikException be) {
            return new Pair<String, Taskman>(be.toString(), taskman);
        }
    }

    private static Pair<String, Taskman> handleList(Taskman taskman) {
        String output = taskman.listString();
        return new Pair<String, Taskman>(output, taskman);
    }

    private static Pair<String, Taskman> handleMark(String input, Taskman taskman) throws BezdelnikException {
        int idx = Integer.parseUnsignedInt(input.split(" ")[1]) - 1;
        taskman = taskman.operate(idx, x -> x.markAsDone());
        String output = String.format("\tI have marked this task as done.\n\t%s", taskman.get(idx));
        return new Pair<String, Taskman>(output, taskman);
    }

    private static Pair<String, Taskman> handleUnmark(String input, Taskman taskman) throws BezdelnikException {
        int idx = Integer.parseUnsignedInt(input.split(" ")[1]) - 1;
        taskman = taskman.operate(idx, x -> x.markAsUndone());
        String output = String.format("\tI have marked this task as undone.\n\t%s", taskman.get(idx));
        return new Pair<String, Taskman>(output, taskman);
    }

    private static Pair<String, Taskman> handleRemove(String input, Taskman taskman) throws BezdelnikException {
        int idx = Integer.parseUnsignedInt(input.split(" ")[1]) - 1;
        Task toDelete = taskman.get(idx);
        taskman = taskman.remove(idx);
        String output = String.format("\tI have deleted this task.\n\t%s", toDelete);
        return new Pair<String, Taskman>(output, taskman);
    }

    private static Pair<String, Taskman> handleTodo(String input, Taskman taskman) {
        String todoInput = input.substring(5); // assumes "todo " or "t " prefix
        if (todoInput.isEmpty()) {
            return new Pair<String, Taskman>("\ttodo must be followed with something to do!", taskman);
        } else {
            Task toAdd = new Todo(todoInput);
            taskman = taskman.add(toAdd);
            String output = String.format("\tadded:\n\t%s\n\tYou currently have %d task(s)", toAdd, taskman.size());
            return new Pair<String, Taskman>(output, taskman);
        }
    }

    private static Pair<String, Taskman> handleDeadline(String input, Taskman taskman) {
        String deadlineInput = input.substring(9); // assumes "deadline " prefix
        String[] array = deadlineInput.split(" /by ");
        Task toAdd = new Deadline(array[0], array[1]);
        taskman = taskman.add(toAdd);
        String output = String.format("\tadded:\n\t%s\n\tYou currently have %d task(s)", toAdd, taskman.size());
        return new Pair<String, Taskman>(output, taskman);
    }

    private static Pair<String, Taskman> handleEvent(String input, Taskman taskman) {
        String eventInput = input.substring(6); // assumes "event " prefix
        String[] array = eventInput.split(" /");
        // Assumes array[1] starts with "at " and array[2] starts with "on "
        Task toAdd = new Event(array[0], array[1].substring(5), array[2].substring(3));
        taskman = taskman.add(toAdd);
        String output = String.format("\tadded:\n\t%s\n\tYou currently have %d task(s)", toAdd, taskman.size());
        return new Pair<String, Taskman>(output, taskman);
    }

    private static Pair<String, Taskman> handleFind(String input, Taskman taskman) {
        String toSearchFor = input.substring(5); // assumes "find " or "f " prefix
        String output = taskman.filter(x -> x.contains(toSearchFor)).listString();
        return new Pair<String, Taskman>(output, taskman);
    }

    private static Pair<String, Taskman> handleDefault(String input, Taskman taskman) {
        String output = String.format("\tUnsupported command: %s", input);
        return new Pair<String, Taskman>(output, taskman);
    }
}

package bezdelnik;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Immutable Task list manager
 */
public class Taskman {
    private final List<Task> taskList;

    /**
     * Constructs an empty Taskman.
     */
    Taskman() {
        this.taskList = List.<Task>of();
    }

    /**
     * Constructs a Taskman with tasks from the provided stream.
     *
     * @param taskStream A stream of tasks.
     */
    private Taskman(Stream<Task> taskStream) {
        assert taskStream != null;

        this.taskList = taskStream.toList();
    }

    /**
     * Returns a new Taskman with the specified task added.
     *
     * @param t The task to add.
     * @return A new Taskman instance with the task added.
     */
    public Taskman add(Task task) {
        assert task != null;

        return new Taskman(Stream.concat(taskList.stream(), Stream.<Task>of(task)));
    }

    /**
     * Returns a new Taskman with the task at the specified index replaced.
     *
     * @param i The index of the task to replace.
     * @param t The new task.
     * @return A new Taskman instance with the updated task.
     */
    public Taskman set(int i, Task task) {
        assert task != null;

        return new Taskman(IntStream.range(0, taskList.size())
        .mapToObj(x -> {
            if (x == i) {
                return t;
            } else {
                return taskList.get(x);
            }
        }));
    }

    /**
     * Returns a new Taskman with the task at the specified index removed.
     *
     * @param i The index of the task to remove.
     * @return A new Taskman instance with the task removed.
     * @throws BezdelnikException If the index is out of bounds.
     */
    public Taskman remove(int i) throws BezdelnikException {
        try {
            return new Taskman(IntStream.range(0, taskList.size())
            .filter(x -> x != i)
            .mapToObj(x -> taskList.get(x)));
        } catch (Throwable t) {
            throw new BezdelnikException(t.toString());
        }
    }

    /**
     * Concatenates another Taskman with this one.
     *
     * @param otherTaskman The other Taskman to concatenate.
     * @return A new Taskman instance containing tasks from both.
     */
    public Taskman concat(Taskman otherTaskman) {
        return new Taskman(Stream.concat(taskList.stream(), otherTaskman.taskList.stream()));
    }

    /**
     * Applies an operation to the task at the specified index.
     *
     * @param i  The index of the task.
     * @param fn The function to apply to the task.
     * @return A new Taskman instance with the modified task.
     * @throws BezdelnikException If the index is out of bounds.
     */
    public Taskman operate(int i, Function<? super Task, ? extends Task> fn) throws BezdelnikException {
        assert fn != null;

        return this.set(i, fn.apply(get(i)));
    }

    /**
     * Optionally applies an operation to the task at the specified index.
     *
     * @param i  The index of the task.
     * @param fn The function to apply to the task.
     * @return A new Taskman instance with the modified task or the task removed.
     * @throws BezdelnikException If the index is out of bounds.
     */
    public Taskman operateOptional(int i, Function<? super Task, ? extends Optional<? extends Task>> fn)
            throws BezdelnikException {
        assert fn != null;

        return fn.apply(taskList.get(i))
        .map(x -> this.set(i, x))
        .orElse(this.remove(i));
    }

    /**
     * Returns a new Taskman containing only tasks that match the given predicate.
     *
     * @param pt The predicate to filter tasks.
     * @return A new Taskman instance with filtered tasks.
     */
    public Taskman filter(Predicate<? super Task> pt) {
        assert pt != null;

        return new Taskman(taskList.stream().filter(pt));
    }

    /**
     * Returns a new Taskman with a sorted task list
     *
     * @return A new Taskman instance with a {@code List<Task>}
     */
    public Taskman sorted() {
        return new Taskman(taskList.stream().sorted());
    }

    /**
     * Retrieves the task at the specified index.
     *
     * @param i The index of the task.
     * @return The task at the specified index.
     * @throws BezdelnikException If the index is out of bounds.
     */
    public Task get(int i) throws BezdelnikException {
        assert i > -1;
        try {
            return taskList.get(i);
        } catch (Throwable t) {
            throw new BezdelnikException(t.toString());
        }
    }

    /**
     * Returns the number of tasks.
     *
     * @return The size of the task list.
     */
    public int size() {
        return taskList.size();
    }

    /**
     * Returns a formatted string listing all tasks.
     *
     * @return A string representation of the task list.
     */
    public String listString() {
        if (taskList.isEmpty()) {
            return "\tNo tasks present!";
        } else {
            return IntStream.range(0, taskList.size())
            .mapToObj(x -> String.format("\t%d. %s", x + 1, taskList.get(x).toString()))
            .collect(Collectors.joining("\n"));
        }
    }

    /**
     * Returns a command representation of all tasks for storage.
     *
     * @return A string containing commands to recreate all tasks.
     */
    public String listCommand() {
        return IntStream.range(0, taskList.size())
        .mapToObj(x -> {
            Task t = taskList.get(x);
            String command = t.returnCommand();
            String markString = "";
            if (t.isDone()) {
                markString = String.format("\nmark %d", x + 1);
            }
            return command + markString;
        })
        .collect(Collectors.joining("\n"));
    }

    /**
     * Checks if this Taskman is equal to another object.
     *
     * @param obj The object to compare.
     * @return True if both are Taskman instances with equal task lists, otherwise false.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Taskman taskman) {
            return taskList.equals(taskman.taskList);
        }
        return false;
    }

    @Override
    public String toString() {
        return taskList.toString();
    }
}

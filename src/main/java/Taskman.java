import java.util.List;
import java.util.stream.Stream;
import java.util.stream.IntStream;
import java.util.Optional;
import java.util.function.UnaryOperator;
import java.util.function.Function;

class Taskman {
    private final List<Task> taskList;

    Taskman() {
        this.taskList = List.<Task>of();
    }

    private Taskman(List<Task> taskList) {
        this.taskList = taskList;
    }

    private Taskman(Stream<Task> taskStream) {
        this(taskStream.toList());
    }

    public Taskman add(Task t) {
        return new Taskman(Stream.concat(taskList.stream(), Stream.<Task>of(t)));
    }

    public Taskman set(int i, Task t) {
        return new Taskman(IntStream.range(0, taskList.size())
            .mapToObj(x -> x == i ? t : taskList.get(x)));
    }

    public Taskman remove(int i) {
        return new Taskman(IntStream.range(0, taskList.size())
            .filter(x -> x != i)
            .mapToObj(x -> taskList.get(x)));
    }

    public Taskman operate(int i, UnaryOperator<Task> fn) {
        return this.set(i, fn.apply(get(i)));
    }

    public Taskman operateOptional(int i, Function<Task, Optional<Task>> fn) {
        return fn.apply(taskList.get(i))
            .map(x -> this.set(i, x))
            .orElse(this.remove(i));
    }

    public Task get(int i) {
        return taskList.get(i);
    }

    public int size() {
        return taskList.size();
    }

    @Override
    public String toString() {
        String output = IntStream.range(0, taskList.size())
        .mapToObj(x -> String.format("\t%d. %s", x + 1, taskList.get(x).toString()))
        .reduce((a, b) -> a + "\n" + b)
        .orElse("\tNo tasks present!");
        return output;
    }

    public String listString() {
        return taskList.toString();
    }
}

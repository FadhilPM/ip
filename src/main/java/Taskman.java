import java.util.List;
import java.util.stream.Stream;
import java.util.stream.IntStream;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

class Taskman {
    private final List<Task> taskList;

    Taskman() {
        this.taskList = List.<Task>of();
    }

    private Taskman(Stream<Task> taskStream) {
        this.taskList = taskStream.toList();
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

    public Taskman operate(int i, Function<? super Task, ? extends Task> fn) {
        return this.set(i, fn.apply(get(i)));
    }

    public Taskman operateOptional(int i, Function<? super Task, ? extends Optional<? extends Task>> fn) {
        return fn.apply(taskList.get(i))
            .map(x -> this.set(i, x))
            .orElse(this.remove(i));
    }

    public Taskman filter(Predicate<Task> pt) {
        return new Taskman(taskList.stream().filter(pt));
    }

    public Task get(int i) {
        return taskList.get(i);
    }

    public int size() {
        return taskList.size();
    }

    public String listString() {
        return IntStream.range(0, taskList.size())
            .mapToObj(x -> String.format("\t%d. %s", x + 1, taskList.get(x).toString()))
            .reduce((a, b) -> a + "\n" + b)
            .orElse("\tNo tasks present!");
    }

    @Override
    public String toString() {
        return taskList.toString();
    }
}

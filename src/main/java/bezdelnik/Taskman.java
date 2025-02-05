package bezdelnik;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

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

    public Taskman remove(int i) throws BezdelnikException {
        try {
            return new Taskman(IntStream.range(0, taskList.size())
                .filter(x -> x != i)
                .mapToObj(x -> taskList.get(x)));
        } catch (Throwable t) {
            throw new BezdelnikException(t.toString());
        }
    }

    public Taskman concat(Taskman otherTaskman) {
        return new Taskman(Stream.concat(taskList.stream(), otherTaskman.taskList.stream()));
    }

    public Taskman operate(int i, Function<? super Task, ? extends Task> fn) throws BezdelnikException {
        return this.set(i, fn.apply(get(i)));
    }

    public Taskman operateOptional(int i, Function<? super Task, ? extends Optional<? extends Task>> fn)
            throws BezdelnikException {
        return fn.apply(taskList.get(i))
            .map(x -> this.set(i, x))
            .orElse(this.remove(i));
    }

    public Taskman filter(Predicate<Task> pt) {
        return new Taskman(taskList.stream().filter(pt));
    }

    public Task get(int i) throws BezdelnikException {
        try {
            return taskList.get(i);
        } catch (Throwable t) {
            throw new BezdelnikException(t.toString());
        }
    }

    public int size() {
        return taskList.size();
    }

    public String listString() {
        return taskList.isEmpty() ? "\tNo tasks present!" : IntStream.range(0, taskList.size())
            .mapToObj(x -> String.format("\t%d. %s", x + 1, taskList.get(x).toString()))
            .collect(Collectors.joining("\n"));
    }

    public String listCommand() {
        return IntStream.range(0, taskList.size())
            .mapToObj(x -> {
                Task t = taskList.get(x);
                return String.format("%s%s", t.returnCommand(), t.isDone() ? String.format("\nmark %d", x + 1) : "");
            })
            .collect(Collectors.joining("\n"));
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj ? true : (obj instanceof Taskman taskman) ? taskList.equals(taskman.taskList) : false;
    }

    @Override
    public String toString() {
        return taskList.toString();
    }
}

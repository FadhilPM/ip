package bezdelnik.utils;

import bezdelnik.tasks.Task;
import bezdelnik.tasks.Todo;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TaskmanTest {

    @Test
    void operate_ModifyTaskAndMaintainImmutability() throws BezdelnikException {
        Todo initialTask = new Todo("Test task");
        Taskman taskman = new Taskman().add(initialTask);

        // Test marking task as done
        Taskman updatedTaskman = taskman.operate(0, Task::markAsDone);
        assertTrue(updatedTaskman.get(0).isDone());
        assertFalse(taskman.get(0).isDone()); // Original should be unchanged due to immutability

        // Test marking task as undone
        Taskman revertedTaskman = updatedTaskman.operate(0, Task::markAsUndone);
        assertFalse(revertedTaskman.get(0).isDone());

        // Test operation on invalid index
        assertThrows(ArrayIndexOutOfBoundsException.class, () ->
            taskman.operate(1, Task::markAsDone)
        );
    }

    @Test
    void filter_ReturnMatchingTasks() {
        Taskman taskman = new Taskman()
            .add(new Todo("Buy groceries"))
            .add(new Todo("Do laundry"))
            .add(new Todo("Buy milk"));

        // Test filtering tasks containing "Buy"
        Taskman filtered = taskman.filter(task -> task.contains("Buy"));
        assertEquals(2, filtered.size());

        // Test filtering tasks containing "laundry"
        Taskman laundryTasks = taskman.filter(task -> task.contains("laundry"));
        assertEquals(1, laundryTasks.size());

        // Test filtering with no matches
        Taskman noMatches = taskman.filter(task -> task.contains("nonexistent"));
        assertEquals(0, noMatches.size());
    }

    @Test
    void concat_ShouldCombineTaskmansCorrectly() throws BezdelnikException {
        Taskman taskman1 = new Taskman()
            .add(new Todo("Task 1"))
            .add(new Todo("Task 2"));

        Taskman taskman2 = new Taskman()
            .add(new Todo("Task 3"))
            .add(new Todo("Task 4"));

        // Test concatenation
        Taskman combined = taskman1.concat(taskman2);
        assertEquals(4, combined.size());
        assertEquals(String.format("[T] [] %s", "Task 1"), combined.get(0).toString());
        assertEquals(String.format("[T] [] %s", "Task 4"), combined.get(3).toString());

        // Test concatenation with empty Taskman
        Taskman emptyTaskman = new Taskman();
        Taskman combinedWithEmpty = taskman1.concat(emptyTaskman);
        assertEquals(taskman1.size(), combinedWithEmpty.size());
    }
}

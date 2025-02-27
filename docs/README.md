# Bezdelnik User Guide

Bezdelnik is a versatile task management application that helps you keep track of your todos, deadlines, and events. It features both a command-line interface and a graphical user interface, allowing you to manage your tasks efficiently.

## Getting Started

To start Bezdelnik:
- With GUI: Run `java -jar bezdelnik.jar`
- Without GUI: Run `java -jar bezdelnik.jar nogui`

Your tasks are automatically saved to `./data/output.dat` and loaded when you restart the application.

## Command Guide

### Creating Tasks

#### Adding Todos
Todos are simple tasks without a specific deadline.

```
todo Buy groceries
```

Expected output:
```
added:
[T] [] Buy groceries
You currently have 1 task(s)
```

#### Adding Deadlines
Deadlines are tasks with a specific due date and time.

```
deadline Submit assignment /by 25/12/2025 1430
```

Expected output:
```
added:
[D] [] Submit assignment (by: 2025-12-25T14:30)
You currently have 2 task(s)
```

#### Adding Events
Events are tasks with a start and end time.

```
event Team meeting /from 20/11/2025 0900 /to 20/11/2025 1000
```

Expected output:
```
added:
[E] [] Team meeting (from: 2025-11-20T09:00 to: 2025-11-20T10:00)
You currently have 3 task(s)
```

### Managing Tasks

#### Listing Tasks
View all your current tasks.

```
list
```
Aliases: `l`, `ls`

Expected output:
```
1. [T] [] Buy groceries
2. [D] [] Submit assignment (by: 2025-12-25T14:30)
3. [E] [] Team meeting (from: 2025-11-20T09:00 to: 2025-11-20T10:00)
```

#### Marking Tasks as Done
Mark a task as completed.

```
mark 1
```

Expected output:
```
I have marked this task as done.
[T] [X] Buy groceries
```

#### Unmarking Tasks
Mark a completed task as not done.

```
unmark 1
```
Aliases: `u`

Expected output:
```
I have marked this task as undone.
[T] [] Buy groceries
```

#### Removing Tasks
Delete a task from your list.

```
remove 3
```
Aliases: `rm`, `rem`, `del`, `delete`

Expected output:
```
I have deleted this task.
[E] [] Team meeting (from: 2025-11-20T09:00 to: 2025-11-20T10:00)
```

#### Finding Tasks
Search for tasks containing specific text.

```
find assignment
```
Aliases: `f`

Expected output:
```
Found 1 matching task(s):
1. [D] [] Submit assignment (by: 2025-12-25T14:30)
```

#### Sorting Tasks
Sort your tasks by date/time.

```
sort
```
Aliases: `s`

Expected output:
```
3 task(s) sorted by time:
1. [D] [] Submit assignment (by: 2025-12-25T14:30)
2. [E] [] Team meeting (from: 2025-11-20T09:00 to: 2025-11-20T10:00)
3. [T] [] Buy groceries
```

#### Archiving Tasks
Save your current tasks to a file and start fresh.

```
archive my_completed_tasks.dat
```
Aliases: `a`

Expected output:
```
Task list archived to: ./data/my_completed_tasks.dat. You have turned over a new leaf.
```

### Exiting the Application

To exit Bezdelnik:

```
bye
```
Aliases: `exit`

## Date and Time Format

All date and time inputs must follow this format:
- Date: `dd/MM/yyyy` (day/month/year)
- Time: `HHmm` (24-hour format without colon)

Example: `25/12/2025 1430` for December 25, 2025, at 2:30 PM

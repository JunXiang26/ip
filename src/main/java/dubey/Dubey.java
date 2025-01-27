package dubey;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Scanner;

class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " "); // Mark done task with X
    }

    public void setStatus(boolean status) {
        isDone = status;
    }

    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }
}

class Todo extends Task {
    public Todo(String description) {
        super(description);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}

class Deadline extends Task {
    protected LocalDate by;
    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy");

    public Deadline(String description, String by) {
        super(description);
        this.by = LocalDate.parse(by, INPUT_FORMAT);
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by.format(OUTPUT_FORMAT) + ")";
    }
}

class Event extends Task {
    protected String from;
    protected String to;

    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from + " to: " + to + ")";
    }
}

class Ui {
    public void showWelcomeMessage() {
        System.out.println("     ____________________________________________________________\n"
                + "     Hello! I'm Dubey!\n"
                + "     What can I do for you?\n"
                + "     ____________________________________________________________\n");
    }

    public void showGoodbyeMessage() {
        System.out.println("     ____________________________________________________________\n"
                + "      Bye. Hope to see you again soon!\n"
                + "     ____________________________________________________________\n");
    }

    public void showError(String message) {
        System.out.println("     ____________________________________________________________\n"
                + "      Error: " + message + "\n"
                + "     ____________________________________________________________\n");
    }

    public void showTaskList(ArrayList<Task> taskList) {
        System.out.println("     ____________________________________________________________\n"
                + "      Here are the tasks in your list:");
        for (int i = 0; i < taskList.size(); i++) {
            System.out.println("      " + (i + 1) + ". " + taskList.get(i));
        }
        System.out.println("     ____________________________________________________________\n");
    }

    public void showTaskAdded(Task task, int size) {
        System.out.println("     ____________________________________________________________\n"
                + "      Got it. I've added this task:\n"
                + "        " + task + "\n"
                + "      Now you have " + size + " tasks in the list.\n"
                + "     ____________________________________________________________\n");
    }

    public void showTaskDeleted(Task task, int size) {
        System.out.println("     ____________________________________________________________\n"
                + "      Noted. I've removed this task:\n"
                + "        " + task + "\n"
                + "      Now you have " + size + " tasks in the list.\n"
                + "     ____________________________________________________________\n");
    }

    public void showTaskMarked(Task task) {
        System.out.println("     ____________________________________________________________\n"
                + "      Nice! I've marked this task as done:\n"
                + "        " + task + "\n"
                + "     ____________________________________________________________\n");
    }

    public void showTaskUnmarked(Task task) {
        System.out.println("     ____________________________________________________________\n"
                + "      OK, I've marked this task as not done yet:\n"
                + "        " + task + "\n"
                + "     ____________________________________________________________\n");
    }
}

class Storage {
    private final String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    public ArrayList<Task> load() {
        ArrayList<Task> taskList = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(filePath))) {
            while (scanner.hasNextLine()) {
                String[] parts = scanner.nextLine().split("\\|", -1);
                String type = parts[0];
                boolean isDone = parts[1].equals("1");
                String description = parts[2];

                switch (type) {
                case "T":
                    Task todo = new Todo(description);
                    todo.setStatus(isDone);
                    taskList.add(todo);
                    break;
                case "D":
                    LocalDate by = LocalDate.parse(parts[3]);
                    Task deadline = new Deadline(description, by.toString());
                    deadline.setStatus(isDone);
                    taskList.add(deadline);
                    break;
                case "E":
                    String from = parts[3];
                    String to = parts[4];
                    Task event = new Event(description, from, to);
                    event.setStatus(isDone);
                    taskList.add(event);
                    break;
                default:
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading from file: " + e.getMessage());
        }
        return taskList;
    }

    public void save(ArrayList<Task> taskList) {
        try (FileWriter writer = new FileWriter(filePath)) {
            for (Task task : taskList) {
                if (task instanceof Todo) {
                    writer.write(String.format("T|%d|%s%n", task.isDone ? 1 : 0, task.description));
                } else if (task instanceof Deadline deadline) {
                    writer.write(String.format("D|%d|%s|%s%n", task.isDone ? 1 : 0, task.description, deadline.by));
                } else if (task instanceof Event event) {
                    writer.write(String.format("E|%d|%s|%s|%s%n", task.isDone ? 1 : 0, task.description, event.from,
                            event.to));
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while writing to file: " + e.getMessage());
        }
    }
}

class Parser {
    public String[] parse(String input) {
        return input.split(" ", 2);
    }
}

class TaskList {
    private final ArrayList<Task> tasks;

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public void add(Task task) {
        tasks.add(task);
    }

    public Task get(int index) {
        return tasks.get(index);
    }

    public void delete(int index) {
        tasks.remove(index);
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }
}

public class Dubey {
    private final Storage storage;
    private final TaskList taskList;
    private final Ui ui;

    public Dubey(String filePath) {
        this.ui = new Ui();
        this.storage = new Storage(filePath);
        this.taskList = new TaskList(storage.load());
    }

    public void run() {
        ui.showWelcomeMessage();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String input = scanner.nextLine();
            if (input.equals("bye")) {
                ui.showGoodbyeMessage();
                break;
            }

            try {
                processCommand(input);
                storage.save(taskList.getTasks());
            } catch (Exception e) {
                ui.showError(e.getMessage());
            }
        }
    }

    public void processCommand(String input) {
        String[] parts = new Parser().parse(input);
        String command = parts[0];
        switch (command) {
        case "list":
            ui.showTaskList(taskList.getTasks());
            break;
        case "todo":
            Task todo = new Todo(parts[1]);
            taskList.add(todo);
            ui.showTaskAdded(todo, taskList.getTasks().size());
            break;
        case "deadline":
            String[] deadlineParts = parts[1].split(" /by ");
            Task deadline = new Deadline(deadlineParts[0], deadlineParts[1]);
            taskList.add(deadline);
            ui.showTaskAdded(deadline, taskList.getTasks().size());
            break;
        case "event":
            String[] eventParts = parts[1].split(" /from | /to ");
            Task event = new Event(eventParts[0], eventParts[1], eventParts[2]);
            taskList.add(event);
            ui.showTaskAdded(event, taskList.getTasks().size());
            break;
        case "delete":
            int index = Integer.parseInt(parts[1]) - 1;
            Task deletedTask = taskList.get(index);
            taskList.delete(index);
            ui.showTaskDeleted(deletedTask, taskList.getTasks().size());
            break;
        case "mark":
            int markIndex = Integer.parseInt(parts[1]) - 1;
            Task markedTask = taskList.get(markIndex);
            markedTask.setStatus(true);
            ui.showTaskMarked(markedTask);
            break;
        case "unmark":
            int unmarkIndex = Integer.parseInt(parts[1]) - 1;
            Task unmarkedTask = taskList.get(unmarkIndex);
            unmarkedTask.setStatus(false);
            ui.showTaskUnmarked(unmarkedTask);
            break;
        default:
            throw new IllegalArgumentException("Unknown command: " + command);
        }
    }

    public static void main(String[] args) {
        new Dubey("data/tasks.txt").run();
    }
}

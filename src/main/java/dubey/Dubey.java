package dubey;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Represents a task with a description and a completion status.
 */
class Task {
    protected String description;
    protected boolean isDone;

    /**
     * Constructor for Task Class.
     *
     * @param description Description of the task.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * gets status of Task dependent on if its marked
     *
     * @return "X" if true, " " if false
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    /**
     * Sets the completion status of the task.
     *
     * @param status True if the task is done, false otherwise.
     */
    public void setStatus(boolean status) {
        isDone = status;
    }

    /**
     * Formats the task as a string.
     *
     * @return A string representing the task.
     */
    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }
}

/**
 * Represents a To-Do task.
 */
class Todo extends Task {

    /**
     * Constructor for Todo Class.
     *
     * @param description Description of the to-do task.
     */
    public Todo(String description) {
        super(description);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}

/**
 * Represents a task with a deadline.
 */
class Deadline extends Task {
    protected LocalDate by;
    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy");

    /**
     * Constructor for Deadline Class.
     *
     * @param description Description of the task.
     * @param by          Deadline for the task in yyyy-MM-dd format.
     */
    public Deadline(String description, String by) {
        super(description);
        this.by = LocalDate.parse(by, INPUT_FORMAT);
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by.format(OUTPUT_FORMAT) + ")";
    }
}

/**
 * Represents an event task with a start and end time.
 */
class Event extends Task {
    protected String from;
    protected String to;

    /**
     * Constructor for Event Class.
     *
     * @param description Description of the event.
     * @param from        Start time of the event.
     * @param to          End time of the event.
     */
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

/**
 * Handles interactions with the user.
 */
class Ui {

    /**
     * Displays a welcome message.
     */
    public void showWelcomeMessage() {
        System.out.println("     ____________________________________________________________\n"
                + "     Hello! I'm Dubey!\n"
                + "     What can I do for you?\n"
                + "     ____________________________________________________________\n");
    }

    /**
     * Displays a goodbye message.
     */
    public void showGoodbyeMessage() {
        System.out.println("     ____________________________________________________________\n"
                + "      Bye. Hope to see you again soon!\n"
                + "     ____________________________________________________________\n");
    }

    /**
     * Displays an error message.
     *
     * @param message The error message to display.
     */
    public void showError(String message) {
        System.out.println("     ____________________________________________________________\n"
                + "      Error: " + message + "\n"
                + "     ____________________________________________________________\n");
    }

    /**
     * Displays the list of tasks.
     *
     * @param taskList List of tasks to display.
     */
    public void showTaskList(ArrayList<Task> taskList) {
        System.out.println("     ____________________________________________________________\n"
                + "      Here are the tasks in your list:");
        for (int i = 0; i < taskList.size(); i++) {
            System.out.println("      " + (i + 1) + ". " + taskList.get(i));
        }
        System.out.println("     ____________________________________________________________\n");
    }

    /**
     * Displays a message when a task is added.
     *
     * @param task The task that was added.
     * @param size The total number of tasks in the list.
     */
    public void showTaskAdded(Task task, int size) {
        System.out.println("     ____________________________________________________________\n"
                + "      Got it. I've added this task:\n"
                + "        " + task + "\n"
                + "      Now you have " + size + " tasks in the list.\n"
                + "     ____________________________________________________________\n");
    }

    /**
     * Displays a message when a task is deleted.
     *
     * @param task The task that was deleted.
     * @param size The total number of tasks remaining in the list.
     */
    public void showTaskDeleted(Task task, int size) {
        System.out.println("     ____________________________________________________________\n"
                + "      Noted. I've removed this task:\n"
                + "        " + task + "\n"
                + "      Now you have " + size + " tasks in the list.\n"
                + "     ____________________________________________________________\n");
    }

    /**
     * Displays a message when a task is marked as done.
     *
     * @param task The task that was marked as done.
     */
    public void showTaskMarked(Task task) {
        System.out.println("     ____________________________________________________________\n"
                + "      Nice! I've marked this task as done:\n"
                + "        " + task + "\n"
                + "     ____________________________________________________________\n");
    }

    /**
     * Displays a message when a task is marked as not done.
     *
     * @param task The task that was marked as not done.
     */
    public void showTaskUnmarked(Task task) {
        System.out.println("     ____________________________________________________________\n"
                + "      OK, I've marked this task as not done yet:\n"
                + "        " + task + "\n"
                + "     ____________________________________________________________\n");
    }
}

/**
 * Manages file storage for tasks.
 */
class Storage {
    private final String filePath;

    /**
     * Constructor for Storage Class.
     *
     * @param filePath Path to the file where tasks are stored.
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads tasks from the file.
     *
     * @return A list of tasks loaded from the file.
     */
    public ArrayList<Task> load() {
        ArrayList<Task> taskList = new ArrayList<>();
        File file = new File(filePath);

        try {
            // Create parent directories and the file if they don't exist
            File parentDir = file.getParentFile();
            if (parentDir != null && !parentDir.exists()) {
                parentDir.mkdirs();
            }

            if (!file.exists()) {
                file.createNewFile();
            }

            // Read existing tasks from the file
            try (Scanner scanner = new Scanner(file)) {
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    String[] parts = line.split("\\|", -1);
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
            }
        } catch (IOException e) {
            System.out.println("An error occurred while handling the file: " + e.getMessage());
        }
        return taskList;
    }


    /**
     * Saves tasks to the file.
     *
     * @param taskList List of tasks to save.
     */
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


/**
 * Parses user input into commands and arguments.
 */
class Parser {

    /**
     * Splits the user input into command and arguments.
     *
     * @param input The user input.
     * @return An array containing the command and arguments.
     */
    public String[] parse(String input) {
        return input.split(" ", 2);
    }
}

/**
 * Manages the list of tasks.
 */
class TaskList {
    private final ArrayList<Task> tasks;

    /**
     * Constructor for TaskList Class.
     *
     * @param tasks A list of tasks to initialize with.
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Constructor for TaskList Class with no initial tasks.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Adds a task to the list.
     *
     * @param task The task to add.
     */
    public void add(Task task) {
        tasks.add(task);
    }

    /**
     * Gets a task from the list by index.
     *
     * @param index Index of the task to get.
     * @return The task at the specified index.
     */
    public Task get(int index) {
        return tasks.get(index);
    }

    /**
     * Deletes a task from the list by index.
     *
     * @param index Index of the task to delete.
     */
    public void delete(int index) {
        tasks.remove(index);
    }

    /**
     * Gets the list of all tasks.
     *
     * @return The list of tasks.
     */
    public ArrayList<Task> getTasks() {
        return tasks;
    }
}

/**
 * Main application class for Dubey.
 */
public class Dubey {
    private final Storage storage;
    private final TaskList taskList;
    private final Ui ui;

    /**
     * Constructor for Dubey Class.
     *
     * @param filePath Path to the file where tasks are stored.
     */
    public Dubey(String filePath) {
        this.ui = new Ui();
        this.storage = new Storage(filePath);
        this.taskList = new TaskList(storage.load());
    }

    /**
     * Runs the Dubey application.
     */
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

    /**
     * Processes user commands.
     *
     * @param input The user input command.
     */
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

    /**
     * Main method for Dubey application.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        new Dubey("data/tasks.txt").run();
    }
}

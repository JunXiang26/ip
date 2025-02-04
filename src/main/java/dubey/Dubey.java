package dubey;

import java.util.Scanner;

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
        case "find":
            String keyword = parts[1];
            ui.showTaskFind(taskList.findAll(keyword));
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
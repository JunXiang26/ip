package dubey;

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
     * Processes user commands and returns respective Dubey bot output.
     *
     * @param input The user input command.
     * @return respective String output from input command
     */
    public String processCommand(String input) {
        String[] parts = new Parser().parse(input);
        String command = parts[0];
        switch (command) {
        case "list":
            assert command.equals("list") : "command should be list";
            return ui.showTaskList(taskList.getTasks());
        case "todo":
            Task todo = new Todo(parts[1]);
            taskList.add(todo);
            return ui.showTaskAdded(todo, taskList.getTasks().size());
        case "deadline":
            String[] deadlineParts = parts[1].split(" /by ");
            Task deadline = new Deadline(deadlineParts[0], deadlineParts[1]);
            taskList.add(deadline);
            return ui.showTaskAdded(deadline, taskList.getTasks().size());
        case "event":
            String[] eventParts = parts[1].split(" /from | /to ");
            Task event = new Event(eventParts[0], eventParts[1], eventParts[2]);
            taskList.add(event);
            return ui.showTaskAdded(event, taskList.getTasks().size());
        case "delete":
            int index = Integer.parseInt(parts[1]) - 1;
            Task deletedTask = taskList.get(index);
            taskList.delete(index);
            return ui.showTaskDeleted(deletedTask, taskList.getTasks().size());
        case "mark":
            int markIndex = Integer.parseInt(parts[1]) - 1;
            Task markedTask = taskList.get(markIndex);
            markedTask.setStatus(true);
            return ui.showTaskMarked(markedTask);
        case "unmark":
            int unmarkIndex = Integer.parseInt(parts[1]) - 1;
            Task unmarkedTask = taskList.get(unmarkIndex);
            unmarkedTask.setStatus(false);
            return ui.showTaskUnmarked(unmarkedTask);
        case "find":
            String keyword = parts[1];
            return ui.showTaskFind(taskList.findAll(keyword));
        default:
            throw new IllegalArgumentException("Unknown command: " + command);
        }
    }

    /**
     * Saves task to taskList and returns respective formatted String output.
     *
     * @param input The user input command.
     * @return formatted respective String output from processCoammand()
     */
    public String getResponse(String input) {
        try {
            String response = processCommand(input); // Process command
            storage.save(taskList.getTasks());
            return "Processed: " + input + "\n" + response; // Modify this to return a meaningful response
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}

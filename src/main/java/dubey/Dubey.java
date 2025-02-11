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
        assert input != null : "User input should never be null";
        assert !input.trim().isEmpty() : "User input should not be empty after trimming";
        Parser parsedInput = new Parser(input);
        String command = parsedInput.getCommand();
        switch (command) {
        case "list":
            return processList();
        case "todo":
            return processTodo(parsedInput);
        case "deadline":
            return processDeadline(parsedInput);
        case "event":
            return processEvent(parsedInput);
        case "delete":
            return processDelete(parsedInput);
        case "mark":
            return processMark(parsedInput);
        case "unmark":
            return processUnmark(parsedInput);
        case "find":
            return processFind(parsedInput);
        default:
            throw new IllegalArgumentException("Unknown command: " + command);
        }
    }

    private String processList() {
        return ui.showTaskList(taskList.getTasks());
    }

    private String processTodo(Parser parsedInput) {
        Task todo = new Todo(parsedInput.getDescription());
        taskList.add(todo);
        return ui.showTaskAdded(todo, taskList.getTasks().size());
    }

    private String processDeadline(Parser parsedInput) {
        System.out.println(parsedInput.getDeadlineDate());
        Task deadline = new Deadline(parsedInput.getDeadlineDescription(), parsedInput.getDeadlineDate());
        taskList.add(deadline);
        return ui.showTaskAdded(deadline, taskList.getTasks().size());
    }

    private String processEvent(Parser parsedInput) {
        Task event = new Event(parsedInput.getEventDescription(), parsedInput.getEventFromDate(),
                parsedInput.getEventToDate());
        taskList.add(event);
        return ui.showTaskAdded(event, taskList.getTasks().size());
    }

    private String processDelete(Parser parsedInput) {
        int index = parsedInput.getIndexNumber() - 1;
        Task deletedTask = taskList.get(index);
        taskList.delete(index);
        return ui.showTaskDeleted(deletedTask, taskList.getTasks().size());
    }

    private String processMark(Parser parsedInput) {
        int markIndex = parsedInput.getIndexNumber() - 1;
        Task markedTask = taskList.get(markIndex);
        markedTask.setStatus(true);
        return ui.showTaskMarked(markedTask);
    }

    private String processUnmark(Parser parsedInput) {
        int unmarkIndex = parsedInput.getIndexNumber() - 1;
        Task unmarkedTask = taskList.get(unmarkIndex);
        unmarkedTask.setStatus(false);
        return ui.showTaskUnmarked(unmarkedTask);
    }

    private String processFind(Parser parsedInput) {
        String keyword = parsedInput.getDescription();
        return ui.showTaskFind(taskList.findTasks(keyword));
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

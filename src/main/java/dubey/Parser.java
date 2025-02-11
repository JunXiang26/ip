package dubey;

/**
 * Parses user input into commands and arguments.
 */
public class Parser {

    private final String[] commandSplit;
    private final String[] loadLineSplit;

    /**
     * Constructs a Parser object by splitting user input into command and arguments.
     *
     * @param input The raw user input string.
     */
    public Parser(String input) {
        this.commandSplit = input.split(" ", 2);
        this.loadLineSplit = null; // Not used in this case
    }

    /**
     * Constructs a Parser object for parsing saved tasks (from file).
     *
     * @param parts The array of task components (e.g., ["T", "1", "description", "date"]).
     */
    public Parser(String[] parts) {
        this.loadLineSplit = parts;
        this.commandSplit = null; // Not used in this case
    }

    /**
     * Retrieves the command from the user input.
     *
     * @return The command as a string (e.g., "todo", "deadline", "event").
     */
    public String getCommand() {
        return commandSplit[0];
    }

    /**
     * Retrieves the description or arguments associated with the command.
     *
     * @return The command description or arguments as a string.
     */
    public String getDescription() {
        return commandSplit[1];
    }

    /**
     * Extracts the task description from a deadline command.
     *
     * @return The description of the deadline task.
     */
    public String getDeadlineDescription() {
        return commandSplit[1].split(" /by ")[0];
    }

    /**
     * Extracts the deadline date from a deadline command.
     *
     * @return The deadline date as a string.
     */
    public String getDeadlineDate() {
        return commandSplit[1].split(" /by ")[1];
    }

    /**
     * Extracts the task description from an event command.
     *
     * @return The description of the event task.
     */
    public String getEventDescription() {
        return commandSplit[1].split(" /from | /to ")[0];
    }

    /**
     * Extracts the start date of an event from the input.
     *
     * @return The event's start date as a string.
     */
    public String getEventFromDate() {
        return commandSplit[1].split(" /from | /to ")[1];
    }

    /**
     * Extracts the end date of an event from the input.
     *
     * @return The event's end date as a string.
     */
    public String getEventToDate() {
        return commandSplit[1].split(" /from | /to ")[2];
    }

    /**
     * Retrieves the index number from a command (e.g., for delete, mark, unmark).
     *
     * @return The index number as an Integer.
     * @throws NumberFormatException if the extracted index is not a valid number.
     */
    public Integer getIndexNumber() {
        return Integer.parseInt(commandSplit[1]);
    }

    /**
     * Retrieves the type of task (T = Todo, D = Deadline, E = Event).
     *
     * @return The task type.
     */
    public String getTaskType() {
        return loadLineSplit[0];
    }

    /**
     * Checks if the task is marked as done.
     *
     * @return true if the task is done, false otherwise.
     */
    public boolean isTaskDone() {
        return loadLineSplit[1].equals("1");
    }

    /**
     * Retrieves the task description.
     *
     * @return The task description.
     */
    public String loadDescription() {
        return loadLineSplit[2];
    }

    /**
     * Extracts the deadline date from saved task data.
     *
     * @return The deadline date.
     */
    public String loadDeadlineDate() {
        return loadLineSplit[3];
    }

    /**
     * Extracts the event start date from saved task data.
     *
     * @return The event start date.
     */
    public String loadEventFromDate() {
        return loadLineSplit[3];
    }

    /**
     * Extracts the event end date from saved task data.
     *
     * @return The event end date.
     */
    public String loadEventToDate() {
        return loadLineSplit[4];
    }
}

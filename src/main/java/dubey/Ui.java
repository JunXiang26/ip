package dubey;

import java.util.ArrayList;

/**
 * Handles interactions with the user.
 */
class Ui {

    /**
     * Returns a welcome message.
     *
     * @return Welcome message.
     */
    public String showWelcomeMessage() {
        return "Hello! I'm Dubey!\nWhat can I do for you?";
    }

    /**
     * Returns a goodbye message.
     *
     * @return Goodbye message.
     */
    public String showGoodbyeMessage() {
        return "Bye. Hope to see you again soon!";
    }

    /**
     * Returns an error message.
     *
     * @param message The error message.
     * @return Formatted error message.
     */
    public String showError(String message) {
        return "Error: " + message;
    }

    /**
     * Returns the list of tasks.
     *
     * @param taskList List of tasks.
     * @return Formatted list of tasks.
     */
    public String showTaskList(ArrayList<Task> taskList) {
        StringBuilder sb = new StringBuilder();
        sb.append("Here are the tasks in your list:\n");
        for (int i = 0; i < taskList.size(); i++) {
            sb.append(i + 1).append(". ").append(taskList.get(i)).append("\n");
        }
        return sb.toString();
    }

    /**
     * Returns a message when a task is added.
     *
     * @return Formatted task added message.
     */
    public String showTaskAdded(Task task, int size) {
        return "Got it. I've added this task:\n" + task + "\nNow you have " + size + " tasks in the list.";
    }

    /**
     * Returns a message when a task is deleted.
     *
     * @return Formatted task deleted message.
     */
    public String showTaskDeleted(Task task, int size) {
        return "Noted. I've removed this task:\n" + task + "\nNow you have " + size + " tasks in the list.";
    }

    /**
     * Returns a message when a task is marked as done.
     *
     * @return Formatted marked task message.
     */
    public String showTaskMarked(Task task) {
        return "Nice! I've marked this task as done:\n" + task;
    }

    /**
     * Returns a message when a task is marked as not done.
     *
     * @return Formatted unmarked task message.
     */
    public String showTaskUnmarked(Task task) {
        return "OK, I've marked this task as not done yet:\n" + task;
    }

    /**
     * Returns a list of tasks found with input keyword.
     *
     * @return Formatted list of tasks found.
     */
    public String showTaskFind(ArrayList<Task> taskList) {
        StringBuilder sb = new StringBuilder();
        sb.append("Here are the matching tasks in your list:\n");
        for (int i = 0; i < taskList.size(); i++) {
            sb.append(i + 1).append(". ").append(taskList.get(i)).append("\n");
        }
        return sb.toString();
    }
}

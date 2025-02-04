package dubey;

import java.util.ArrayList;

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

    public void showTaskFind(ArrayList<Task> taskList) {
        System.out.println("     ____________________________________________________________\n" +
                "      Here are the matching tasks in your list:");
        for (int i = 0; i < taskList.size(); i++) {
            System.out.println("      " + (i + 1) + ". " + taskList.get(i));
        }
        System.out.println("     ____________________________________________________________\n");
    }
}

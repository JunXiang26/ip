package dubey;

import java.util.ArrayList;
import java.util.List;

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

    /**
     * Finds the list of all tasks with relevant input keyword.
     *
     * @param keyword Keyword user is trying to find.
     * @return The list of tasks.
     */
    public ArrayList<Task> findTasks(String keyword) {

        ArrayList<Task> taskList = new ArrayList<>();
        for (Task task : tasks) {
            if (task.description.contains(keyword)) {
                taskList.add(task);
            }
        }
        return taskList;
    }
}

package dubey;

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

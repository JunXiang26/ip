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
     * gets description of Task
     *
     * @return String description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the completion status of the task.
     *
     * @param status True if the task is done, false otherwise.
     */
    public void setStatus(boolean status) {
        isDone = status;
    }

    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }
}

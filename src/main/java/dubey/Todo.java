package dubey;

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

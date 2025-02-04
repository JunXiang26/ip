package dubey;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents a task with a deadline.
 */
class Deadline extends Task {
    protected LocalDate by;
    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy");

    /**
     * Constructor for Deadline Class.
     *
     * @param description Description of the task.
     * @param by          Deadline for the task in yyyy-MM-dd format.
     */
    public Deadline(String description, String by) {
        super(description);
        this.by = LocalDate.parse(by, INPUT_FORMAT);
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by.format(OUTPUT_FORMAT) + ")";
    }
}

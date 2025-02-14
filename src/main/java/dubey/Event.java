package dubey;

/**
 * Represents an event task with a start and end time.
 */
class Event extends Task {
    protected String from;
    protected String to;

    /**
     * Constructor for Event Class.
     *
     * @param description Description of the event.
     * @param from        Start time of the event.
     * @param to          End time of the event.
     */
    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from + " to: " + to + ")";
    }
}

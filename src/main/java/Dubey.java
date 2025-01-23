import java.util.Scanner;
import java.util.ArrayList;

class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    public void setStatus(boolean status) {
        isDone = status;
    }

    public String toString() {
        return "[" + this.getStatusIcon() + "] " + description;
    }
}

class Todo extends Task {

    public Todo(String description) {
        super(description);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}

class Deadline extends Task {

    protected String by;

    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }
}

class Event extends Task {

    protected String from;
    protected String to;

    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + "(from: " + from + " to: " + to + ")";
    }
}

public class Dubey {

    public static void main(String[] args) {
        ArrayList<Task> taskList = new ArrayList<>(100);

        String intro = "     ____________________________________________________________\n"
                    + "     Hello! I'm Dubey!      \n"
                    + "     What can I do for you? \n"
                    + "     ____________________________________________________________\n";
        System.out.println(intro);

        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        while (!input.equals("bye")) {
            String command[] = input.split(" ");
            switch (command[0]) {
                case "list":
                    System.out.println("     ____________________________________________________________\n"
                                     + "      Here are the tasks in your list:\n");
                    for (Task task : taskList) {
                        int i = taskList.indexOf(task) + 1;
                        System.out.println("      " + i + "." + task.toString());
                    }
                    System.out.println("     ____________________________________________________________\n");
                    break;

                case "mark":
                    int markIndex = Integer.parseInt(command[1]);
                    taskList.get(markIndex - 1).setStatus(true);
                    String marked = "     ____________________________________________________________\n"
                            + "      Nice! I've marked this task as done:\n"
                            + "         " + taskList.get(markIndex - 1).toString() + "\n"
                            + "     ____________________________________________________________\n";
                    System.out.println(marked);
                    break;

                case "unmark":
                    int unmarkIndex = Integer.parseInt(command[1]);
                    taskList.get(unmarkIndex - 1).setStatus(false);
                    String unmarked = "     ____________________________________________________________\n"
                            + "      OK, I've marked this task as not done yet:\n"
                            + "         " + taskList.get(unmarkIndex - 1).toString() + "\n"
                            + "     ____________________________________________________________\n";
                    System.out.println(unmarked);
                    break;
                case "todo":
                    String descriptionTodo = input.substring(4);
                    taskList.add(new Todo(descriptionTodo));
                    System.out.println("     ____________________________________________________________\n" +
                            "      Got it. I've added this task:\n" +
                            "        " + taskList.getLast().toString() + "\n" +
                            "      Now you have " + taskList.size() + " tasks in the list.\n" +
                            "     ____________________________________________________________\n"
                    );
                    break;
                case "deadline":
                    String descriptionDeadline = input.substring(8, input.indexOf("/"));
                    String deadline = input.substring(input.indexOf("/") + 4);
                    taskList.add(new Deadline(descriptionDeadline,  deadline));
                    System.out.println("     ____________________________________________________________\n" +
                            "      Got it. I've added this task:\n" +
                            "        " + taskList.getLast().toString() + "\n" +
                            "      Now you have " + taskList.size() + " tasks in the list.\n" +
                            "     ____________________________________________________________\n"
                    );
                    break;
                case "event":
                    String descriptionEvent = input.substring(5, input.indexOf("/"));
                    String eventFrom = input.substring(input.indexOf("/from") + 6, input.indexOf("/to"));
                    String eventTo = input.substring(input.indexOf("/to") + 4);
                    taskList.add(new Event(descriptionEvent,  eventFrom, eventTo));
                    System.out.println("     ____________________________________________________________\n" +
                            "      Got it. I've added this task:\n" +
                            "        " + taskList.getLast().toString() + "\n" +
                            "      Now you have " + taskList.size() + " tasks in the list.\n" +
                            "     ____________________________________________________________\n"
                    );
                    break;
                default:
                    taskList.add(new Task(input));
                    System.out.println("     ____________________________________________________________\n" +
                            "      added: " + input + "\n" +
                            "     ____________________________________________________________\n"
                    );
            }
            input = scanner.nextLine();
        }

        String bye = "     ____________________________________________________________\n"
                    + "      Bye. Hope to see you again soon!\n"
                    + "     ____________________________________________________________\n";
        System.out.println(bye);
    }
}
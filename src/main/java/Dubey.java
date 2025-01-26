import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

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

class DubeyException extends Exception {
    // Constructor with a custom message
    public DubeyException(String message) {
        super("     ____________________________________________________________\n"
              + "       Error!! " + message + "\n"
              + "     ____________________________________________________________\n");
    }
}

public class Dubey {
    public static void handleCommand(String input) throws DubeyException {
        String[] parts = input.split(" ", 2);
        String command = parts[0];

        switch (command) {
            case "todo":
                if (parts.length < 2 || parts[1].trim().isEmpty()) {
                    throw new DubeyException("Description todo is empty.");
                }
                break;

            default:
                throw new DubeyException("Unknown command: " + command);
        }
    }

    public static void writeTasksToFile(ArrayList<Task> taskList, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            for (Task task : taskList) {
                if (task instanceof Todo) {
                    writer.write(String.format("[T|%d|%s]%n", task.isDone ? 1 : 0, task.description));
                } else if (task instanceof Deadline) {
                    Deadline deadline = (Deadline) task;
                    writer.write(String.format("[D|%d|%s|by:%s]%n", task.isDone ? 1 : 0, task.description, deadline.by));
                } else if (task instanceof Event) {
                    Event event = (Event) task;
                    writer.write(String.format("[E|%d|%s|from:%s|to:%s]%n", task.isDone ? 1 : 0, task.description, event.from, event.to));
                }
            }

        } catch (IOException e) {
            System.out.println("An error occurred while writing to file: " + e.getMessage());
        }
    }

    public static ArrayList<Task> readTasksFromFile(String filePath) {
        ArrayList<Task> taskList = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(filePath))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.substring(1, line.length() - 1).split("\\|");
                String type = parts[0];
                boolean isDone = parts[1].equals("1");
                String description = parts[2];

                switch (type) {
                    case "T":
                        Todo todo = new Todo(description);
                        todo.setStatus(isDone);
                        taskList.add(todo);
                        break;
                    case "D":
                        String by = parts[3].substring(3); // Remove "by:"
                        Deadline deadline = new Deadline(description, by);
                        deadline.setStatus(isDone);
                        taskList.add(deadline);
                        break;
                    case "E":
                        String from = parts[3].substring(5); // Remove "from:"
                        String to = parts[4].substring(3); // Remove "to:"
                        Event event = new Event(description, from, to);
                        event.setStatus(isDone);
                        taskList.add(event);
                        break;
                }
            }
            System.out.println("Tasks successfully loaded from file.");
        } catch (IOException e) {
            System.out.println("An error occurred while reading from file: " + e.getMessage());
        }
        return taskList;
    }


    public static void main(String[] args) {
        // Define the folder and file paths
        String folderName = "data";
        String fileName = "dubey.txt";

        // Create the folder
        File folder = new File(folderName);
        if (!folder.exists()) {
            boolean folderCreated = folder.mkdir();
            if (folderCreated) {
                System.out.println("Folder created: " + folderName);
            } else {
                System.out.println("Failed to create folder: " + folderName);
                return;
            }
        } else {
            System.out.println("Folder already exists: " + folderName);

        }

        // Create the text file inside the folder
        File file = new File(folder, fileName);

        try {
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getPath());
            } else {
                System.out.println("File already exists: " + file.getPath());
            }
        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }

        ArrayList<Task> taskList = readTasksFromFile("data/dubey.txt");

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
                    try {
                        handleCommand(input);
                        String descriptionTodo = input.substring(4);
                        taskList.add(new Todo(descriptionTodo));
                        System.out.println("     ____________________________________________________________\n" +
                                "      Got it. I've added this task:\n" +
                                "        " + taskList.getLast().toString() + "\n" +
                                "      Now you have " + taskList.size() + " tasks in the list.\n" +
                                "     ____________________________________________________________\n"
                        );
                    } catch (DubeyException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case "deadline":
                    String descriptionDeadline = input.substring(8, input.indexOf("/"));
                    String deadline = input.substring(input.indexOf("/") + 4);
                    taskList.add(new Deadline(descriptionDeadline, deadline));
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
                    taskList.add(new Event(descriptionEvent, eventFrom, eventTo));
                    System.out.println("     ____________________________________________________________\n" +
                            "      Got it. I've added this task:\n" +
                            "        " + taskList.getLast().toString() + "\n" +
                            "      Now you have " + taskList.size() + " tasks in the list.\n" +
                            "     ____________________________________________________________\n"
                    );
                    break;

                case "delete":
                    int deleteIndex = Integer.parseInt(command[1]) - 1;
                    System.out.println("     ____________________________________________________________\n" +
                            "      Noted. I've removed this task:\n" +
                            "        " + taskList.get(deleteIndex).toString()
                    );
                    taskList.remove(deleteIndex);
                    System.out.println("      Now you have " + taskList.size() + " tasks in the list.\n" +
                            "     ____________________________________________________________\n"
                    );
                    break;

                default:
                    try {
                        handleCommand(input);
                    } catch (DubeyException e) {
                        System.out.println(e.getMessage());
                    }
            }
            writeTasksToFile(taskList, "data/dubey.txt");
            input = scanner.nextLine();
        }

        String bye = "     ____________________________________________________________\n"
                    + "      Bye. Hope to see you again soon!\n"
                    + "     ____________________________________________________________\n";
        System.out.println(bye);
    }
}
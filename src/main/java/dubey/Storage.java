package dubey;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Manages file storage for tasks.
 */
public class Storage {
    private final String filePath;

    /**
     * Constructor for Storage Class.
     *
     * @param filePath Path to the file where tasks are stored.
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads tasks from the file.
     *
     * @return A list of tasks loaded from the file.
     */
    public ArrayList<Task> load() {
        ArrayList<Task> taskList = new ArrayList<>();
        File file = new File(filePath);

        try {
            // Create parent directories and the file if they don't exist
            File parentDir = file.getParentFile();
            if (parentDir != null && !parentDir.exists()) {
                parentDir.mkdirs();
            }

            if (!file.exists()) {
                file.createNewFile();
            }

            // Read existing tasks from the file
            try (Scanner scanner = new Scanner(file)) {
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    String[] parts = line.split("\\|", -1);
                    String type = parts[0];
                    boolean isDone = parts[1].equals("1");
                    String description = parts[2];

                    switch (type) {
                    case "T":
                        Task todo = new Todo(description);
                        todo.setStatus(isDone);
                        taskList.add(todo);
                        break;
                    case "D":
                        LocalDate by = LocalDate.parse(parts[3]);
                        Task deadline = new Deadline(description, by.toString());
                        deadline.setStatus(isDone);
                        taskList.add(deadline);
                        break;
                    case "E":
                        String from = parts[3];
                        String to = parts[4];
                        Task event = new Event(description, from, to);
                        event.setStatus(isDone);
                        taskList.add(event);
                        break;
                    default:
                        break;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while handling the file: " + e.getMessage());
        }
        return taskList;
    }


    /**
     * Saves tasks to the file.
     *
     * @param taskList List of tasks to save.
     */
    public void save(ArrayList<Task> taskList) {
        try (FileWriter writer = new FileWriter(filePath)) {
            for (Task task : taskList) {
                if (task instanceof Todo) {
                    writer.write(String.format("T|%d|%s%n", task.isDone ? 1 : 0, task.description));
                } else if (task instanceof Deadline deadline) {
                    writer.write(String.format("D|%d|%s|%s%n", task.isDone ? 1 : 0, task.description, deadline.by));
                } else if (task instanceof Event event) {
                    writer.write(String.format("E|%d|%s|%s|%s%n", task.isDone ? 1 : 0, task.description, event.from,
                            event.to));
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while writing to file: " + e.getMessage());
        }
    }
}

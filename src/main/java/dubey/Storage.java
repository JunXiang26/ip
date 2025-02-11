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
            try {
                handleNoFileOrFolder(file);
            } catch (IOException e) {
                e.printStackTrace();
            }

            assert file.exists() : "File should exist before reading";
            assert file.canRead() : "File should be readable";

            // Read existing tasks from the file
            try (Scanner scanner = new Scanner(file)) {
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    String[] parts = line.split("\\|", -1);
                    Parser taskRead = new Parser(parts);
                    String type = taskRead.getTaskType();

                    switch (type) {
                    case "T":
                        handleLoadTodo(taskRead, taskList);
                        break;
                    case "D":
                        handleLoadDeadline(taskRead, taskList);
                        break;
                    case "E":
                        handleLoadEvent(taskRead, taskList);
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

    private void handleNoFileOrFolder(File file) throws IOException {
        // Create parent directories and the file if they don't exist
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }

        if (!file.exists()) {
            file.createNewFile();
        }
    }

    private void handleLoadTodo(Parser taskRead, ArrayList<Task> taskList) {
        Task todo = new Todo(taskRead.loadDescription());
        todo.setStatus(taskRead.isTaskDone());
        taskList.add(todo);
    }

    private void handleLoadDeadline(Parser taskRead, ArrayList<Task> taskList) {
        LocalDate by = LocalDate.parse(taskRead.loadDeadlineDate());
        Task deadline = new Deadline(taskRead.loadDescription(), by.toString());
        deadline.setStatus(taskRead.isTaskDone());
        taskList.add(deadline);
    }

    private void handleLoadEvent(Parser taskRead, ArrayList<Task> taskList) {
        String from = taskRead.loadEventFromDate();
        String to = taskRead.loadEventToDate();
        Task event = new Event(taskRead.loadDescription(), from, to);
        event.setStatus(taskRead.isTaskDone());
        taskList.add(event);
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
                } else {
                    throw new RuntimeException("Unknown task type: " + task.getClass());
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while writing to file: " + e.getMessage());
        }
    }
}

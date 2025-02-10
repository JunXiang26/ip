package dubey;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class DubeyTest {
    @Test
    public void testLoadAndSaveTasks() throws Exception {
        String testFilePath = "test_tasks.txt";
        // Prepare test file with sample tasks
        try (FileWriter writer = new FileWriter(testFilePath)) {
            writer.write("T|1|Test Todo\n");
            writer.write("D|0|Submit report|2025-01-30\n");
            writer.write("E|1|Team meeting|10:00 AM|12:00 PM\n");
        }

        // Load tasks
        Storage storage = new Storage(testFilePath);
        ArrayList<Task> tasks = storage.load();

        // Assert task details
        assertEquals(3, tasks.size());
        assertEquals("[T][X] Test Todo", tasks.get(0).toString());
        assertEquals("[D][ ] Submit report (by: Jan 30 2025)", tasks.get(1).toString());
        assertEquals("[E][X] Team meeting (from: 10:00 AM to: 12:00 PM)", tasks.get(2).toString());

        // Modify tasks and save
        tasks.get(1).setStatus(true); // Mark the deadline task as done
        storage.save(tasks);

        // Reload and assert changes
        ArrayList<Task> reloadedTasks = storage.load();
        assertEquals(3, reloadedTasks.size());
        assertEquals("[D][X] Submit report (by: Jan 30 2025)", reloadedTasks.get(1).toString());

        // Cleanup test file
        new File(testFilePath).delete();
    }

    @Test
    public void testCommandProcessing() {
        TaskList taskList = new TaskList();
        Ui ui = new Ui();
        Storage storage = new Storage("test_tasks.txt"); // No file interaction in this test
        Dubey dubey = new Dubey("test_tasks.txt");

        // Test adding a todo task
        dubey.processCommand("todo Read book");
        taskList.add(new Todo("Read book"));
        ArrayList<Task> tasks = taskList.getTasks();
        assertEquals(1, tasks.size());
        assertEquals("[T][ ] Read book", tasks.get(0).toString());

        // Test adding a deadline task
        dubey.processCommand("deadline Submit assignment /by 2025-01-31");
        taskList.add(new Deadline("Submit assignment", "2025-01-31"));
        assertEquals(2, tasks.size());
        assertEquals("[D][ ] Submit assignment (by: Jan 31 2025)", tasks.get(1).toString());

        // Test marking a task
        dubey.processCommand("mark 1");
        taskList.get(0).setStatus(true);
        assertTrue(tasks.get(0).isDone);
        assertEquals("[T][X] Read book", tasks.get(0).toString());

        // Test deleting a task
        dubey.processCommand("delete 1");
        taskList.delete(0);
        assertEquals(1, tasks.size());
        assertEquals("[D][ ] Submit assignment (by: Jan 31 2025)", tasks.get(0).toString());
    }
}

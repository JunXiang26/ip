import java.util.Scanner;
import java.util.ArrayList;

public class Dubey {
    public static class Task {
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

        public String getDescription() {
            return description;
        }
    }

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
                    int i = 1;
                    for (Task task : taskList) {
                        System.out.println("      " + i + ".[" + task.getStatusIcon() + "] " + task.getDescription());
                        i++;
                    }
                    System.out.println("     ____________________________________________________________\n");
                    break;

                case "mark":
                    int markIndex = Integer.parseInt(command[1]);
                    taskList.get(markIndex - 1).setStatus(true);
                    String marked = "     ____________________________________________________________\n"
                            + "      Nice! I've marked this task as done:\n"
                            + "        [" + taskList.get(markIndex - 1).getStatusIcon() + "] " + taskList.get(markIndex - 1).getDescription()+ "\n"
                            + "     ____________________________________________________________\n";
                    System.out.println(marked);
                    break;

                case "unmark":
                    int unmarkIndex = Integer.parseInt(command[1]);
                    taskList.get(unmarkIndex - 1).setStatus(false);
                    String unmarked = "     ____________________________________________________________\n"
                            + "      OK, I've marked this task as not done yet:\n"
                            + "        [" + taskList.get(unmarkIndex - 1).getStatusIcon() + "] " + taskList.get(unmarkIndex - 1).getDescription()+ "\n"
                            + "     ____________________________________________________________\n";
                    System.out.println(unmarked);
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
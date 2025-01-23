import java.util.Scanner;
import java.util.ArrayList;

public class Dubey {
    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<>(100);

        String intro = "     ____________________________________________________________\n"
                    + "     Hello! I'm Dubey!      \n"
                    + "     What can I do for you? \n"
                    + "     ____________________________________________________________\n";
        System.out.println(intro);

        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        while (!input.equals("bye")) {
            if (input.equals("list")) {
                System.out.println("     ____________________________________________________________");
                int i = 1;
                for (String s : list) {
                    System.out.println("      " +i + ". " + s);
                    i++;
                }
                System.out.println("     ____________________________________________________________");
            } else {
                list.add(input);
                System.out.println( "     ____________________________________________________________\n" +
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

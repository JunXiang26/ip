import java.util.Scanner;

public class Dubey {
    public static void main(String[] args) {
        String intro = "     ____________________________________________________________\n"
                    + "     Hello! I'm Dubey!      \n"
                    + "     What can I do for you? \n"
                    + "     ____________________________________________________________\n";
        System.out.println(intro);

        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        while (!input.equals("bye")) {
            System.out.println( "     ____________________________________________________________\n" +
                                "       " + input + "\n" +
                                "     ____________________________________________________________\n"
            );
            input = scanner.nextLine();
        }

        String bye = "     ____________________________________________________________\n"
                    + "      Bye. Hope to see you again soon!\n"
                    + "     ____________________________________________________________\n";
        System.out.println(bye);
    }
}

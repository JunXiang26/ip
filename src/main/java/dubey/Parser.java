package dubey;

/**
 * Parses user input into commands and arguments.
 */
class Parser {

    /**
     * Splits the user input into command and arguments.
     *
     * @param input The user input.
     * @return An array containing the command and arguments.
     */
    public String[] parse(String input) {
        return input.split(" ", 2);
    }
}

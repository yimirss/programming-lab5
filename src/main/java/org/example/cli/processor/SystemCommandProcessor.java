package org.example.cli.processor;

import java.util.Set;

/**
 * Processor for CLI-scope commands
 */
public class SystemCommandProcessor extends CommandProcessor {
    public static final String HELP_TEXT = """
        help : Display help for available commands
        info : Output information about the collection (type, initialization date, number of elements, etc.)
        show : Output all elements of the collection in string representation
        insert null {element} : Add a new element with the specified key
        update id {element} : Update the value of a collection element whose id is equal to the specified one
        remove_key null : Remove an element from the collection by its key
        clear : Clear the collection
        save : Save the collection to a file
        execute_script file_name : Read and execute a script from the specified file. The script contains commands in the same format as entered by the user in interactive mode
        exit : Terminate the program (without saving to a file)
        remove_greater {element} : Remove all elements from the collection that exceed the specified one
        history : Display the last 11 commands (without their arguments)
        replace_if_greater null {element} : Replace the value by key if the new value is greater than the old one
        average_of_discount : Output the average value of the discount field for all elements in the collection
        print_ascending : Output the elements of the collection in ascending order
        print_field_descending_venue : Output the values of the venue field of all elements in descending order
        """;

    public static final String EXIT_TEXT = "Terminating app...";

    private boolean wasExitCalled;

    public SystemCommandProcessor() {
        super(Set.of("help", "exit"));
        wasExitCalled = false;
    }


    @Override
    public String processCommand(String commandName, String rawArgs, boolean isInteractive) {
        if(!rawArgs.isBlank()) {
            return "This command doesn't accept arguments";
        }

        switch (commandName) {
            case "help" -> {
                return HELP_TEXT;
            }
            case "exit" -> {
                wasExitCalled = true;
                return EXIT_TEXT;
            }
            default -> throw new IllegalArgumentException("Unknown command passed");
        }
    }

    public boolean wasExitCalled() {
        return wasExitCalled;
    }
}

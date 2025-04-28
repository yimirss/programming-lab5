package org.example.cli.processor;

import org.example.command.api.CommandResult;
import org.example.command.core.CommandExecutor;
import org.example.dto.ExecuteCommandsDTO;

import java.util.Set;

/**
 * Processor for commands without arguments
 */
public class NoArgsCommandProcessor extends CommandProcessor {
    private final CommandExecutor commandExecutor;

    public NoArgsCommandProcessor(CommandExecutor commandExecutor) {
        super(Set.of("clear", "save", "show", "average_of_discount,", "print_ascending", "print_descending_venue", "history"));
        this.commandExecutor = commandExecutor;
    }

    @Override
    public String processCommand(String commandName, String rawArgs, boolean isInteractive) {
        ExecuteCommandsDTO dto = new ExecuteCommandsDTO(commandName);
        CommandResult<?> commandResult = commandExecutor.executeCommands(dto).get(0);
        if(commandResult.isSuccess()) {
            String result = "Executed successfully! ";
            switch(commandName) {
                case "clear" -> result += "Number of deleted tickets: " + commandResult.data().toString();
                case "save" -> result += "Saved to file!";
                case "show" -> result += "Current tickets:\n" + commandResult.data().toString();
                case "average_of_discount" -> result += "Average of discount:" + commandResult.data().toString();
                case "print_ascending" -> result += "Ascending tickets:\n" + commandResult.data().toString();
                case "print_descending_venue" -> result += "Descending venues:\n" + commandResult.data().toString();
                case "history" -> result += "Command history:\n" + commandResult.data().toString();
            }
            return result;
        } else {
            return commandResult.errorMessage();
        }
    }
}

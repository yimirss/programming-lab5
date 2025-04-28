package org.example.cli.processor;

import org.example.cli.utils.TypeResolver;
import org.example.command.api.CommandResult;
import org.example.command.core.CommandExecutor;
import org.example.dto.ExecuteCommandsDTO;

import java.util.Set;

/**
 * Processor for commands that accept key as argument
 */
public class KeyCommandProcessor extends CommandProcessor {
    private final CommandExecutor commandExecutor;

    public KeyCommandProcessor(CommandExecutor commandExecutor) {
        super(Set.of("remove_by_key"));
        this.commandExecutor = commandExecutor;
    }

    @Override
    public String processCommand(String commandName, String rawArgs, boolean isInteractive) {
        Integer key;
        try {
            key = TypeResolver.parseValue(rawArgs.trim(), Integer.class);
        } catch(IllegalArgumentException e) {
            return "Bad key value provided";
        }

        ExecuteCommandsDTO dto = new ExecuteCommandsDTO(commandName, key);
        CommandResult<?> commandResult = commandExecutor.executeCommands(dto).get(0);
        if(commandResult.isSuccess()) {
            return "Executed successfully! Removed object:\n" + commandResult.data().toString();
        } else {
            return commandResult.errorMessage();
        }
    }
}
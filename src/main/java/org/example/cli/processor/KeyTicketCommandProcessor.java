package org.example.cli.processor;

import org.example.cli.builder.TicketBuilder;
import org.example.cli.utils.KVArgParser;
import org.example.cli.utils.TypeResolver;
import org.example.command.api.CommandResult;
import org.example.command.core.CommandExecutor;
import org.example.domain.Ticket;
import org.example.dto.ExecuteCommandsDTO;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;

/**
 * Processor for commands that accept key and ticket object as arguments
 */
public class KeyTicketCommandProcessor extends CommandProcessor {
    private final CommandExecutor commandExecutor;
    private final TicketBuilder ticketBuilder;

    public KeyTicketCommandProcessor(CommandExecutor commandExecutor, TicketBuilder ticketBuilder) {
        super(Set.of("insert", "replace_if_greater"));
        this.commandExecutor = commandExecutor;
        this.ticketBuilder = ticketBuilder;
    }

    @Override
    public String processCommand(String commandName, String rawArgs, boolean isInteractive) {
        String[] rawArgsParts = rawArgs.split("\\s+");

        String keyRaw = rawArgsParts[0];
        Integer key;
        try {
            key = TypeResolver.parseValue(keyRaw.trim(), Integer.class);
        } catch(IllegalArgumentException e) {
            return "Bad key value provided";
        }

        Ticket ticket;
        if(isInteractive) {
            ticket = ticketBuilder.buildInteractive();
        } else {
            String ticketArgs = String.join(" ", Arrays.copyOfRange(rawArgsParts, 1, rawArgsParts.length));
            try {
                Map<String, String> argsMap = KVArgParser.parse(ticketArgs);
                ticket = ticketBuilder.build(argsMap);
            } catch (Exception e) {
                return "Error occurred while parsing arguments! " + e.getMessage();
            }
        }

        ExecuteCommandsDTO dto = new ExecuteCommandsDTO(commandName, key, ticket);
        CommandResult<?> commandResult = commandExecutor.executeCommands(dto).get(0);
        if(commandResult.isSuccess()) {
            if(commandName.equals("insert")) {
                return "Executed successfully! Inserted ticket:\n" + commandResult.data().toString();
            } else {
                return "Executed successfully! Ticket replaced: " + commandResult.data().toString();
            }
        } else {
            return commandResult.errorMessage();
        }
    }
}

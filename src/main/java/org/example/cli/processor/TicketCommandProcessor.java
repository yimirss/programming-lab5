package org.example.cli.processor;

import org.example.cli.builder.TicketBuilder;
import org.example.cli.utils.KVArgParser;
import org.example.command.api.CommandResult;
import org.example.command.core.CommandExecutor;
import org.example.domain.Ticket;
import org.example.dto.ExecuteCommandsDTO;

import java.util.Map;
import java.util.Set;

/**
 * Processor for commands that accept only ticket object as argument
 */
public class TicketCommandProcessor extends CommandProcessor {
    private final CommandExecutor commandExecutor;
    private final TicketBuilder ticketBuilder;

    public TicketCommandProcessor(CommandExecutor commandExecutor, TicketBuilder ticketBuilder) {
        super(Set.of("remove_greater"));
        this.commandExecutor = commandExecutor;
        this.ticketBuilder = ticketBuilder;
    }

    @Override
    public String processCommand(String commandName, String rawArgs, boolean isInteractive) {
        Ticket ticket;
        if(isInteractive) {
            ticket = ticketBuilder.buildInteractive();
        } else {
            try {
                Map<String, String> argsMap = KVArgParser.parse(rawArgs);
                ticket = ticketBuilder.build(argsMap);
            } catch (Exception e) {
                return "Error occurred while parsing arguments! " + e.getMessage();
            }
        }
        ExecuteCommandsDTO dto = new ExecuteCommandsDTO(commandName, ticket);
        CommandResult<?> commandResult = commandExecutor.executeCommands(dto).get(0);
        if(commandResult.isSuccess()) {
            return "Executed successfully! Number of removed objects: " + commandResult.data().toString();
        } else {
            return commandResult.errorMessage();
        }
    }
}

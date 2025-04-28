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
 * Processor for commands that accept id and ticket object as arguments
 */
public class IdTicketCommandProcessor extends CommandProcessor {
    private final CommandExecutor commandExecutor;
    private final TicketBuilder ticketBuilder;

    public IdTicketCommandProcessor(CommandExecutor commandExecutor, TicketBuilder ticketBuilder) {
        super(Set.of("update"));
        this.commandExecutor = commandExecutor;
        this.ticketBuilder = ticketBuilder;
    }

    @Override
    public String processCommand(String commandName, String rawArgs, boolean isInteractive) {
        String[] rawArgsParts = rawArgs.split("\\s+");

        String idRaw = rawArgsParts[0];
        Long id;

        try {
            id = TypeResolver.parseValue(idRaw.trim(), Long.class);
        } catch(IllegalArgumentException e) {
            return "Bad id value provided";
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

        ExecuteCommandsDTO dto = new ExecuteCommandsDTO(commandName, id, ticket);
        CommandResult<?> commandResult = commandExecutor.executeCommands(dto).get(0);
        if(commandResult.isSuccess()) {
            return "Ticket successfully updated. New ticket value:\n" + commandResult.data().toString();
        } else {
            return commandResult.errorMessage();
        }
    }
}

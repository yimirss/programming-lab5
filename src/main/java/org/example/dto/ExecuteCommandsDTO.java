package org.example.dto;

import org.example.domain.Ticket;

import java.util.List;

/**
 * DTO for execute commands request
 */
public record ExecuteCommandsDTO(List<CommandWithArgs> commandsWithArgs) {
    public record CommandWithArgs(String commandName, Integer key, Long id, Ticket ticket) {}

    public ExecuteCommandsDTO(String commandName, Integer key, Ticket ticket) {
        this(List.of(new CommandWithArgs(commandName, key, null, ticket)));
    }

    public ExecuteCommandsDTO(String commandName, Long id, Ticket ticket) {
        this(List.of(new CommandWithArgs(commandName, null, id, ticket)));
    }

    public ExecuteCommandsDTO(String commandName, Ticket ticket) {
        this(List.of(new CommandWithArgs(commandName, null, null, ticket)));
    }

    public ExecuteCommandsDTO(String commandName) {
        this(List.of(new CommandWithArgs(commandName, null, null, null)));
    }

    public ExecuteCommandsDTO(String commandName, Integer key) {
        this(List.of(new CommandWithArgs(commandName, key, null, null)));
    }
}

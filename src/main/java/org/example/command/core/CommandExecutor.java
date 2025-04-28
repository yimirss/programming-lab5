package org.example.command.core;

import org.example.command.api.CommandResult;
import org.example.command.api.ICommand;
import org.example.dto.ExecuteCommandsDTO;
import org.example.repository.api.IHistoryRepository;
import org.example.repository.api.ITicketRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that processes command execution requests
 */
public class CommandExecutor {
    private final CommandFactory commandFactory;
    private final CommandValidator commandValidator;
    private final IHistoryRepository historyRepository;
    private final ITicketRepository ticketRepository;

    public CommandExecutor(IHistoryRepository historyRepository, ITicketRepository ticketRepository) {
        this.historyRepository = historyRepository;
        this.ticketRepository = ticketRepository;
        this.commandFactory = new CommandFactory(ticketRepository, historyRepository);
        this.commandValidator = new CommandValidator();
    }

    public List<CommandResult<?>> executeCommands(ExecuteCommandsDTO executeCommandsDTO) {
        List<CommandResult<?>> executionResult = new ArrayList<>();
        for(ExecuteCommandsDTO.CommandWithArgs commandWithArgs : executeCommandsDTO.commandsWithArgs()) {
            if(!commandValidator.validateCommand(commandWithArgs)) {
                executionResult.add(CommandResult.failure("Validation failed"));
                break;
            }
            ICommand<?> command = commandFactory.createCommand(commandWithArgs);
            CommandResult<?> commandResult = command.execute();
            executionResult.add(commandResult);

            historyRepository.addCommand("default", commandWithArgs.commandName());


            if(commandResult.isFailure()) {
                break;
            }

        }
        return executionResult;
    }
}

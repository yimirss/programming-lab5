package org.example.command.core;

import org.example.command.api.ICommand;
import org.example.command.impl.*;
import org.example.dto.ExecuteCommandsDTO;
import org.example.repository.api.IHistoryRepository;
import org.example.repository.api.ITicketRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Helper contract that creates commands
 */
public class CommandFactory {
    private final Map<String, Function<ExecuteCommandsDTO.CommandWithArgs, ICommand<?>>> commandCreators;
    private final ITicketRepository ticketRepository;
    private final IHistoryRepository historyRepository;

    public CommandFactory(ITicketRepository ticketRepository, IHistoryRepository historyRepository) {
        commandCreators = new HashMap<>();
        this.ticketRepository = ticketRepository;
        this.historyRepository = historyRepository;

        commandCreators.put("insert", this::createInsertCommand);
        commandCreators.put("update", this::createUpdateCommand);
        commandCreators.put("remove_by_key", this::createRemoveByKeyCommand);
        commandCreators.put("clear", this::createClearCommand);
        commandCreators.put("save", this::createSaveCommand);
        commandCreators.put("average_of_discount", this::createAverageOfDiscountCommand);
        commandCreators.put("print_ascending", this::createPrintAscendingCommand);
        commandCreators.put("print_descending_venue", this::createPrintDescendingVenueCommand);
        commandCreators.put("remove_greater", this::createRemoveGreaterCommand);
        commandCreators.put("replace_if_greater", this::createReplaceIfGreaterCommand);
        commandCreators.put("show", this::createShowCommand);
        commandCreators.put("history", this::createHistoryCommand);


    }

    public ICommand<?> createCommand(ExecuteCommandsDTO.CommandWithArgs commandWithArgs) {
        String commandName = commandWithArgs.commandName();
        Function<ExecuteCommandsDTO.CommandWithArgs, ICommand<?>> creator = commandCreators.get(commandName);

        if (creator == null) {
            throw new IllegalArgumentException("CommandFactory: unknown command " + commandName);
        }

        return creator.apply(commandWithArgs);
    }

    private ICommand<?> createInsertCommand(ExecuteCommandsDTO.CommandWithArgs commandWithArgs) {
        return new InsertCommand(ticketRepository, commandWithArgs.key(), commandWithArgs.ticket());
    }

    private ICommand<?> createUpdateCommand(ExecuteCommandsDTO.CommandWithArgs commandWithArgs) {
        return new UpdateCommand(ticketRepository, commandWithArgs.id(), commandWithArgs.ticket());
    }

    private ICommand<?> createRemoveByKeyCommand(ExecuteCommandsDTO.CommandWithArgs commandWithArgs) {
        return new RemoveByKeyCommand(ticketRepository, commandWithArgs.key());
    }

    private ICommand<?> createClearCommand(ExecuteCommandsDTO.CommandWithArgs commandWithArgs) {
        return new ClearCommand(ticketRepository);
    }

    private ICommand<?> createSaveCommand(ExecuteCommandsDTO.CommandWithArgs commandWithArgs) {
        return new SaveCommand(ticketRepository);
    }

    private ICommand<?> createAverageOfDiscountCommand(ExecuteCommandsDTO.CommandWithArgs commandWithArgs) {
        return new AverageOfDiscountCommand(ticketRepository);
    }

    private ICommand<?> createPrintAscendingCommand(ExecuteCommandsDTO.CommandWithArgs commandWithArgs) {
        return new PrintAscendingCommand(ticketRepository);
    }

    private ICommand<?> createPrintDescendingVenueCommand(ExecuteCommandsDTO.CommandWithArgs commandWithArgs) {
        return new PrintDescendingVenueCommand(ticketRepository);
    }

    private ICommand<?> createRemoveGreaterCommand(ExecuteCommandsDTO.CommandWithArgs commandWithArgs) {
        return new RemoveGreaterCommand(ticketRepository, commandWithArgs.ticket());
    }

    private ICommand<?> createReplaceIfGreaterCommand(ExecuteCommandsDTO.CommandWithArgs commandWithArgs) {
        return new ReplaceIfGreaterCommand(ticketRepository, commandWithArgs.key(), commandWithArgs.ticket());
    }

    private ICommand<?> createShowCommand(ExecuteCommandsDTO.CommandWithArgs commandWithArgs) {
        return new ShowCommand(ticketRepository);
    }

    private ICommand<?> createHistoryCommand(ExecuteCommandsDTO.CommandWithArgs commandWithArgs) {
        return new HistoryCommand(historyRepository);
    }
}
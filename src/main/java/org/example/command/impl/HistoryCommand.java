package org.example.command.impl;

import org.example.command.api.CommandResult;
import org.example.command.api.ICommand;
import org.example.repository.api.IHistoryRepository;


import java.util.List;

/**
 * Implementation of history command
 */
public class HistoryCommand implements ICommand<List<String>> {
    private final IHistoryRepository historyRepository;


    public HistoryCommand(IHistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

    @Override
    public CommandResult<List<String>> execute() {
        try {
            List<String> commandHistory = historyRepository.getHistory("default", 11);
            return CommandResult.success(commandHistory);
        } catch(Exception e) {
            System.out.println(e.toString());
            return CommandResult.failure("Unexpected error");
        }
    }
}
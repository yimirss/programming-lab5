package org.example.command.impl;

import org.example.command.api.CommandResult;
import org.example.command.api.ICommand;
import org.example.repository.api.ITicketRepository;

/**
 * Implementation of clear command
 */
public class ClearCommand implements ICommand<Integer> {
    private final ITicketRepository ticketRepository;


    public ClearCommand(ITicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Override
    public CommandResult<Integer> execute() {
        try {
            int clearedAmount = ticketRepository.clear();
            return CommandResult.success(clearedAmount);
        } catch(Exception e) {
            System.out.println(e.toString());
            return CommandResult.failure("Unexpected error");
        }
    }
}

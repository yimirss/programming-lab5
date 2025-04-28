package org.example.command.impl;

import org.example.command.api.CommandResult;
import org.example.command.api.ICommand;
import org.example.domain.Ticket;
import org.example.repository.api.ITicketRepository;

import java.util.List;

/**
 * Implementation of print_ascending command
 */
public class PrintAscendingCommand implements ICommand<List<Ticket>> {
    private final ITicketRepository ticketRepository;


    public PrintAscendingCommand(ITicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Override
    public CommandResult<List<Ticket>> execute() {
        try {
            List<Ticket> allTickets = ticketRepository.getAllAscending();
            return CommandResult.success(allTickets);
        } catch(Exception e) {
            System.out.println(e.toString());
            return CommandResult.failure("Unexpected error");
        }
    }
}
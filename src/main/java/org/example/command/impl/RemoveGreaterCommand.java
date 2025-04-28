package org.example.command.impl;

import org.example.command.api.CommandResult;
import org.example.command.api.ICommand;
import org.example.domain.Ticket;
import org.example.repository.api.ITicketRepository;

/**
 * Implementation of remove_greater command
 */
public class RemoveGreaterCommand implements ICommand<Integer> {
    private final ITicketRepository ticketRepository;
    private final Ticket ticket;

    public RemoveGreaterCommand(ITicketRepository ticketRepository, Ticket ticket) {
        this.ticketRepository = ticketRepository;
        this.ticket = ticket;
    }

    @Override
    public CommandResult<Integer> execute() {
        try {
            int ticketsRemoved = ticketRepository.removeGreater(ticket);
            return CommandResult.success(ticketsRemoved);
        } catch(Exception e) {
            System.out.println(e.toString());
            return CommandResult.failure("Unexpected error");
        }
    }
}

package org.example.command.impl;

import org.example.command.api.CommandResult;
import org.example.command.api.ICommand;
import org.example.domain.Ticket;
import org.example.repository.api.ITicketRepository;
import org.example.repository.exception.EntityNotFoundException;

/**
 * Implementation of update command
 */
public class UpdateCommand implements ICommand<Ticket> {
    private final Long id;
    private final Ticket ticket;
    private final ITicketRepository ticketRepository;


    public UpdateCommand(ITicketRepository ticketRepository, Long id, Ticket ticket) {
        this.id = id;
        this.ticket = ticket;
        this.ticketRepository = ticketRepository;
    }

    @Override
    public CommandResult<Ticket> execute() {
        try {
            ticketRepository.update(id, ticket);
            return CommandResult.success(ticket);
        } catch (EntityNotFoundException e) {
            return CommandResult.failure("Ticket with such id doesn't exist");
        } catch(Exception e) {
            System.out.println(e.toString());
            return CommandResult.failure("Unexpected error");
        }
    }
}

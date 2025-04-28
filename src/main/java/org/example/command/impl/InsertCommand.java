package org.example.command.impl;

import org.example.command.api.CommandResult;
import org.example.command.api.ICommand;
import org.example.domain.Ticket;
import org.example.repository.exception.DuplicateKeyException;
import org.example.repository.api.ITicketRepository;

/**
 * Implementation of insert command
 */
public class InsertCommand implements ICommand<Ticket> {
    private final Integer key;
    private final Ticket ticketToInsert;
    private final ITicketRepository ticketRepository;


    public InsertCommand(ITicketRepository ticketRepository, Integer key, Ticket ticketToInsert) {
        this.key = key;
        this.ticketToInsert = ticketToInsert;
        this.ticketRepository = ticketRepository;
    }

    @Override
    public CommandResult<Ticket> execute() {
        try {
            Ticket ticket = ticketRepository.insert(key, ticketToInsert);
            return CommandResult.success(ticket);
        } catch (DuplicateKeyException e) {
            return CommandResult.failure("Object with such key already exists");
        } catch(Exception e) {
            System.out.println(e.toString());
            return CommandResult.failure("Unexpected error");
        }
    }
}

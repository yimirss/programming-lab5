package org.example.command.impl;

import org.example.command.api.CommandResult;
import org.example.command.api.ICommand;
import org.example.domain.Ticket;
import org.example.repository.api.ITicketRepository;
import org.example.repository.exception.EntityNotFoundException;

/**
 * Implementation of remove_by_key command
 */
public class RemoveByKeyCommand implements ICommand<Ticket> {
    private final Integer key;
    private final ITicketRepository ticketRepository;


    public RemoveByKeyCommand(ITicketRepository ticketRepository, Integer key) {
        this.key = key;
        this.ticketRepository = ticketRepository;
    }

    @Override
    public CommandResult<Ticket> execute() {
        try {
            return CommandResult.success(ticketRepository.removeByKey(key));
        } catch (EntityNotFoundException e) {
            return CommandResult.failure("Object with such key doesn't exist");
        } catch(Exception e) {
            System.out.println(e.toString());
            return CommandResult.failure("Unexpected error");
        }
    }
}

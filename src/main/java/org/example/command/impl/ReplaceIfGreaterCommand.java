package org.example.command.impl;

import org.example.command.api.CommandResult;
import org.example.command.api.ICommand;
import org.example.domain.Ticket;
import org.example.repository.api.ITicketRepository;
import org.example.repository.exception.EntityNotFoundException;

/**
 * Implementation of replace_if_greater command
 */
public class ReplaceIfGreaterCommand implements ICommand<Boolean> {
    private final ITicketRepository ticketRepository;
    private final Integer key;
    private final Ticket ticket;

    public ReplaceIfGreaterCommand(ITicketRepository ticketRepository, Integer key, Ticket ticket) {
        this.ticketRepository = ticketRepository;
        this.key = key;
        this.ticket = ticket;
    }

    @Override
    public CommandResult<Boolean> execute() {
        try {
            boolean isReplaced = ticketRepository.replaceIfGreater(key, ticket);
            return CommandResult.success(isReplaced);
        } catch (EntityNotFoundException e) {
            return CommandResult.failure("Object with such key doesn't exist");
        } catch(Exception e) {
            System.out.println(e.toString());
            return CommandResult.failure("Unexpected error");
        }
    }
}

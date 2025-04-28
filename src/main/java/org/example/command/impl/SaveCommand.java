package org.example.command.impl;

import org.example.command.api.CommandResult;
import org.example.command.api.ICommand;
import org.example.repository.api.ITicketRepository;

import java.io.IOException;

/**
 * Implementation of save command
 */
public class SaveCommand implements ICommand<Void> {
    private final ITicketRepository ticketRepository;


    public SaveCommand(ITicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Override
    public CommandResult<Void> execute() {
        try {
            ticketRepository.save();
            return CommandResult.success(null);
        } catch(IOException e) {
            System.out.println(e.toString());
            return CommandResult.failure("Error on saving occurred");
        }
    }
}

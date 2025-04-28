package org.example.command.impl;

import org.example.command.api.CommandResult;
import org.example.command.api.ICommand;
import org.example.domain.Venue;
import org.example.repository.api.ITicketRepository;

import java.util.List;

/**
 * Implementation of print_descending_venue command
 */
public class PrintDescendingVenueCommand implements ICommand<List<Venue>> {
    private final ITicketRepository ticketRepository;


    public PrintDescendingVenueCommand(ITicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Override
    public CommandResult<List<Venue>> execute() {
        try {
            List<Venue> venues = ticketRepository.getAllVenuesDescending();
            return CommandResult.success(venues);
        } catch(Exception e) {
            System.out.println(e.toString());
            return CommandResult.failure("Unexpected error");
        }
    }
}
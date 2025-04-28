package org.example.command.impl;

import org.example.command.api.CommandResult;
import org.example.command.api.ICommand;
import org.example.repository.api.ITicketRepository;

/**
 * Implementation of average_of_discount command
 */
public class AverageOfDiscountCommand implements ICommand<Double> {
    private final ITicketRepository ticketRepository;


    public AverageOfDiscountCommand(ITicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Override
    public CommandResult<Double> execute() {
        try {
            Double averageDiscount = ticketRepository.getAverageDiscount();
            return CommandResult.success(averageDiscount);
        } catch(Exception e) {
            System.out.println(e.toString());
            return CommandResult.failure("Unexpected error");
        }
    }
}
package org.example.cli.builder;

import org.example.cli.utils.TypeResolver;
import org.example.domain.Ticket;
import org.example.domain.enums.TicketType;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.BiConsumer;

/**
 * Utility class for CLI application that helps to construct ticket either interactively or inline
 */
public class TicketBuilder {
    public final List<FieldSpec> fieldSpecs;
    public final Scanner scanner;

    public TicketBuilder(List<FieldSpec> fieldSpecs) {
        this.fieldSpecs = fieldSpecs;
        scanner = new Scanner(System.in);
    }

    public static TicketBuilder createDefault() {
        return new TicketBuilder(List.of(
                new FieldSpec("ticket.name",
                        "Enter ticket name. Can't be null or empty",
                        "Ticket name must be string, can't be null or empty",
                        true, String.class, (Ticket ticket, String name) -> ticket.setName(name)),

                new FieldSpec("ticket.coordinates.x",
                        "Enter x-coordinate for ticket",
                        "X-coordinate must be double",
                        true, Double.class, (Ticket ticket, Double x) -> ticket.getCoordinates().setX(x)),

                new FieldSpec("ticket.coordinates.y",
                        "Enter y-coordinate for ticket. Must be greater than -615, can't be null",
                        "Y-coordinate must be a float, greater than -615, can't be null",
                        true, Float.class, (Ticket ticket, Float y) -> ticket.getCoordinates().setY(y)),

                new FieldSpec("ticket.price",
                        "Enter ticket price. Must be greater than 0",
                        "Ticket price must be an integer, greater than 0",
                        false, Integer.class, (Ticket ticket, Integer price) -> ticket.setPrice(price)),

                new FieldSpec("ticket.discount",
                        "Enter ticket discount. Allowed range is from 0 to 100",
                        "Discount must be a long, allowed range is from 0 to 100",
                        true, Long.class, (Ticket ticket, Long discount) -> ticket.setDiscount(discount)),

                new FieldSpec("ticket.refundable",
                        "Is ticket refundable? If so enter true",
                        "Invalid input provided",
                        true, Boolean.class, (Ticket ticket, Boolean refundable) -> ticket.setRefundable(refundable)),

                new FieldSpec("ticket.type",
                        "Enter ticket type. Supported types: vip, usual, cheap",
                        "Invalid type provided. Currently supported types: vip, usual, cheap",
                        true, TicketType.class, (Ticket ticket, TicketType type) -> ticket.setType(type)),

                new FieldSpec("ticket.venue.name",
                        "Enter ticket's venue name, can't be null or empty",
                        "Venue name must be a string, can't be null or empty",
                        true, String.class, (Ticket ticket, String name) -> ticket.getVenue().setName(name)),

                new FieldSpec("ticket.venue.capacity",
                        "Enter ticket's venue capacity, must be greater than 0",
                        "Ticket's venue capacity must be a long, greater than 0",
                        true, Long.class, (Ticket ticket, Long capacity) -> ticket.getVenue().setCapacity(capacity))
        ));
    }

    @SuppressWarnings("unchecked")
    public Ticket build(Map<String, String> inputMap) {
        Ticket ticket = Ticket.createDefault();

        for (FieldSpec field : fieldSpecs) {
            String input = inputMap.get(field.name());

            if (input == null && field.isRequired()) {
                throw new IllegalArgumentException("Missing required field: " + field.name());
            }

            if (input != null) {
                try {
                    var value = TypeResolver.parseValue(input, field.type());
                    BiConsumer<Ticket, Object> mutator = (BiConsumer<Ticket, Object>) field.mutator();
                    mutator.accept(ticket, value);
                } catch (Exception e) {
                    throw new IllegalArgumentException("Invalid input for " + field.name() + ": " + e.getMessage());
                }
            }
        }

        return ticket;
    }

    @SuppressWarnings("unchecked")
    public Ticket buildInteractive() {
        Ticket ticket = Ticket.createDefault();

        for (FieldSpec field : fieldSpecs) {
            while (true) {
                try {
                    System.out.print(field.inputMessage());
                    String input = scanner.nextLine();

                    if (input.isEmpty() && !field.isRequired()) {
                        break;
                    }

                    var value = TypeResolver.parseValue(input, field.type());
                    BiConsumer<Ticket, Object> mutator = (BiConsumer<Ticket, Object>) field.mutator();
                    mutator.accept(ticket, value);

                    break;
                } catch (Exception e) {
                    System.out.println(field.validationErrorMessage());
                }
            }
        }

        return ticket;
    }

}

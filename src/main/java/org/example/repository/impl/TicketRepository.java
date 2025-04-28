package org.example.repository.impl;

import org.example.domain.Coordinates;
import org.example.domain.Ticket;
import org.example.domain.Venue;
import org.example.domain.enums.TicketType;
import org.example.repository.exception.DuplicateKeyException;
import org.example.repository.exception.EntityNotFoundException;
import org.example.repository.api.ITicketRepository;

import java.io.*;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Impl of the ITicketRepository interface using TreeMap.
 */
public class TicketRepository implements ITicketRepository {
    private final String CSV_TICKET_HEADER_LINE = "key,ticket_id,name,coord_x,coord_y,creation_date,price," +
            "discount,refundable,ticket_type,venue_id,venue_name,venue_capacity\n";
    private final String CSV_TICKET_FORMATTER = "%d,%d,%s,%f,%f,%s,%s,%d,%b,%s,%d,%s,%d\n";

    private final TreeMap<Integer, Ticket> tickets;
    private final String filePath;

    public TicketRepository(String filePath) {
        this.tickets = new TreeMap<>();
        this.filePath = filePath;
    }

    @Override
    public Ticket insert(Integer key, Ticket ticket) throws DuplicateKeyException {
        if (tickets.containsKey(key)) {
            throw new DuplicateKeyException(key);
        }

        ticket.setId(Math.abs(UUID.randomUUID().getMostSignificantBits()));
        ticket.setCreationDate(ZonedDateTime.now());

        if (ticket.getVenue().getId() == null) {
            ticket.getVenue().setId(Math.abs(UUID.randomUUID().getMostSignificantBits()));
        }

        tickets.put(key, ticket);
        return ticket;
    }

    @Override
    public boolean update(long id, Ticket ticket) throws EntityNotFoundException {

        Map.Entry<Integer, Ticket> entry = tickets.entrySet().stream()
                .filter(e -> e.getValue().getId() == id)
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException(id));

        Ticket existingTicket = entry.getValue();

        ticket.setId(existingTicket.getId());
        ticket.setCreationDate(existingTicket.getCreationDate());
        ticket.getVenue().setId(existingTicket.getVenue().getId());
        tickets.put(entry.getKey(), ticket);

        return true;
    }

    @Override
    public Ticket removeByKey(Integer key) throws EntityNotFoundException {
        if (!tickets.containsKey(key)) {
            throw new EntityNotFoundException(key);
        }

        return tickets.remove(key);
    }

    @Override
    public List<Ticket> getAllAscending() {
        return tickets.values().stream()
                .sorted()
                .toList();
    }

    @Override
    public List<Venue> getAllVenuesDescending() {
        return tickets.values().stream()
                .map(Ticket::getVenue)
                .sorted(Comparator.reverseOrder())
                .toList();
    }

    @Override
    public int removeGreater(Ticket ticket) {
        int initialSize = tickets.size();
        tickets.entrySet().removeIf(entry -> entry.getValue().compareTo(ticket) > 0);
        return initialSize - tickets.size();
    }

    @Override
    public boolean replaceIfGreater(Integer key, Ticket ticket) throws EntityNotFoundException {
        if (!tickets.containsKey(key)) {
            throw new EntityNotFoundException(key);
        }

        Ticket existingTicket = tickets.get(key);
        if (ticket.compareTo(existingTicket) > 0) {
            ticket.setId(existingTicket.getId());
            ticket.getVenue().setId(existingTicket.getVenue().getId());
            tickets.put(key, ticket);
            return true;
        }
        return false;
    }

    @Override
    public double getAverageDiscount() {
        OptionalDouble totalDiscount = tickets.values().stream()
                .mapToLong(Ticket::getDiscount)
                .average();

        return totalDiscount.orElse(0);
    }

    @Override
    public int clear() {
        int size = tickets.size();
        tickets.clear();
        return size;
    }

    @Override
    public List<Ticket> findAll() {
        return new ArrayList<>(tickets.values());
    }

    @Override
    public void save() throws IOException {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(CSV_TICKET_HEADER_LINE);
            for (Map.Entry<Integer, Ticket> entry : tickets.entrySet()) {
                writer.write(formatTicketCsvLine(entry.getKey(), entry.getValue()));
            }
        } catch (IOException e) {
            throw new IOException("Failed to save tickets to file: " + filePath);
        }
    }

    @Override
    public void load() throws IOException {
        File file = new File(filePath);

        if (!file.exists() || !file.canRead()) {
            throw new IOException("Failed to load file: " + filePath);
        }

        tickets.clear();

        InputStreamReader isr = new InputStreamReader(new FileInputStream(file));
        BufferedReader reader = new BufferedReader(isr);

        reader.readLine();

        String line;
        while ((line = reader.readLine()) != null) {
            Map.Entry<Integer, Ticket> entry = parseCsvLine(line);
            tickets.put(entry.getKey(), entry.getValue());
        }
    }

    private String formatTicketCsvLine(Integer key, Ticket ticket) {
        return String.format(CSV_TICKET_FORMATTER,
                key,
                ticket.getId(),
                ticket.getName(),
                ticket.getCoordinates().getX(),
                ticket.getCoordinates().getY(),
                ticket.getCreationDate().format(DateTimeFormatter.ISO_ZONED_DATE_TIME),
                ticket.getPrice() == null ? "" : ticket.getPrice().toString(),
                ticket.getDiscount(),
                ticket.isRefundable(),
                ticket.getType().name(),
                ticket.getVenue().getId(),
                ticket.getVenue().getName(),
                ticket.getVenue().getCapacity()
        );
    }

    private Map.Entry<Integer, Ticket> parseCsvLine(String line) {
        String[] parts = line.split(",");
        if (parts.length != 13) {
            throw new IllegalArgumentException("Invalid CSV line format: expected 13 parts, got " + parts.length);
        }

        Integer key = Integer.parseInt(parts[0]);
        long ticketId = Long.parseLong(parts[1]);
        String name = parts[2];
        double x = Double.parseDouble(parts[3]);
        Float y = Float.parseFloat(parts[4]);
        ZonedDateTime creationDate = ZonedDateTime.parse(parts[5], DateTimeFormatter.ISO_ZONED_DATE_TIME);
        Integer price = parts[6].isEmpty() ? null : Integer.parseInt(parts[6]);
        long discount = Long.parseLong(parts[7]);
        boolean refundable = Boolean.parseBoolean(parts[8]);
        TicketType type = TicketType.valueOf(parts[9]);
        Long venueId = Long.parseLong(parts[10]);
        String venueName = parts[11];
        long capacity = Long.parseLong(parts[12]);

        Coordinates coordinates = new Coordinates(x, y);
        Venue venue = new Venue(venueName, capacity);
        venue.setId(venueId);

        Ticket ticket = new Ticket(name, coordinates, price,
                discount, refundable, type, venue);
        ticket.setId(ticketId);
        ticket.setCreationDate(creationDate);

        return Map.entry(key, ticket);
    }
}

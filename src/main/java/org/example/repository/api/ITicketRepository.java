package org.example.repository.api;

import org.example.domain.Ticket;
import org.example.domain.Venue;
import org.example.repository.exception.DuplicateKeyException;
import org.example.repository.exception.EntityNotFoundException;
import org.example.repository.exception.RepositoryException;

import java.io.IOException;
import java.util.List;

/**
 * Repository interface for managing tickets.
 */
public interface ITicketRepository {

    /**
     * @param key    the key to associate with the ticket
     * @param ticket the ticket to insert
     * @return ticket
     */
    Ticket insert(Integer key, Ticket ticket) throws DuplicateKeyException;

    /**
     * @param id     the ID of the ticket to update
     * @param ticket the new ticket data
     * @return true if the operation was successful, false otherwise
     */
    boolean update(long id, Ticket ticket) throws RepositoryException;

    /**
     * @param key the key of the ticket to remove
     * @return true if the operation was successful, false otherwise
     */
    Ticket removeByKey(Integer key) throws EntityNotFoundException;


    /**
     * @return a collection of all tickets
     */
    List<Ticket> findAll();

    /**
     * @return a collection of all tickets in ascending order
     */
    List<Ticket> getAllAscending();

    /**
     * @return a collection of all venue values in descending order
     */
    List<Venue> getAllVenuesDescending();

    /**
     * @param ticket the reference ticket
     * @return the number of tickets removed
     */
    int removeGreater(Ticket ticket);

    /**
     * @param key    the key of the ticket to replace
     * @param ticket the new ticket
     * @return true if the operation was successful, false otherwise
     */
    boolean replaceIfGreater(Integer key, Ticket ticket) throws EntityNotFoundException;

    /**
     * @return the average discount value
     */
    double getAverageDiscount();

    /**
     * @return Amount of cleared tickets
     */
    int clear();

    /**
     * @return true if the operation was successful, false otherwise
     */
    void save() throws IOException;

    /**
     * @return true if the operation was successful, false otherwise
     */
    void load() throws IOException;
}
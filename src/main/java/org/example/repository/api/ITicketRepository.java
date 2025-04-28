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

    Ticket insert(Integer key, Ticket ticket) throws DuplicateKeyException;

    boolean update(long id, Ticket ticket) throws RepositoryException;

    Ticket removeByKey(Integer key) throws EntityNotFoundException;


    List<Ticket> findAll();

    List<Ticket> getAllAscending();

    List<Venue> getAllVenuesDescending();

    int removeGreater(Ticket ticket);

    boolean replaceIfGreater(Integer key, Ticket ticket) throws EntityNotFoundException;

    double getAverageDiscount();

    int clear();

    void save() throws IOException;

    void load() throws IOException;
}
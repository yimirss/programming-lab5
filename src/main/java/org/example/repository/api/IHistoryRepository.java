package org.example.repository.api;

import java.util.List;

/**
 * Repository for storing command history.
 */
public interface IHistoryRepository {

    void addCommand(String username, String commandName);

    List<String> getHistory(String username, int limit);

    void clearHistory(String username);
}
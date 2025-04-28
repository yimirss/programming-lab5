package org.example.repository.impl;

import org.example.repository.api.IHistoryRepository;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementation of the HistoryRepository interface that stores command history in CSV format.
 */
public class HistoryRepository implements IHistoryRepository {
    private final Map<String, List<String>> userCommandHistory;
    private final int historyCapacity;
    private final String historyFilePath;

    public HistoryRepository(int historyCapacity, String historyFilePath) {
        this.userCommandHistory = new HashMap<>();
        this.historyCapacity = historyCapacity;
        this.historyFilePath = historyFilePath;

        if (historyFilePath != null) {
            loadHistoryFromFile();
        }
    }

    @Override
    public void addCommand(String username, String commandName) {
        List<String> history = userCommandHistory.computeIfAbsent(username, k -> new ArrayList<>());

        history.add(commandName);

        if (history.size() > historyCapacity) {
            history.remove(0);
        }

        if (historyFilePath != null) {
            saveHistoryToFile();
        }
    }

    @Override
    public List<String> getHistory(String username, int limit) {
        List<String> history = userCommandHistory.getOrDefault(username, new ArrayList<>());

        if (limit >= history.size() || limit < 0) {
            return new ArrayList<>(history);
        }

        return new ArrayList<>(history.subList(history.size() - limit, history.size()));
    }

    @Override
    public void clearHistory(String username) {
        userCommandHistory.put(username, new ArrayList<>());

        if (historyFilePath != null) {
            saveHistoryToFile();
        }
    }

    private void loadHistoryFromFile() {
        File file = new File(historyFilePath);
        if (!file.exists() || !file.canRead()) {
            return;
        }

        try (InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(file));
             BufferedReader reader = new BufferedReader(inputStreamReader)) {

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] parts = line.split(",");
                if (parts.length >= 2) {
                    String username = parts[0].trim();
                    String command = parts[1].trim();

                    List<String> userHistory = userCommandHistory.computeIfAbsent(username, k -> new ArrayList<>());
                    userHistory.add(command);

                    // Ensure history doesn't exceed capacity
                    if (userHistory.size() > historyCapacity) {
                        userHistory.remove(0);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading history from file: " + e.getMessage());
        }
    }

    private void saveHistoryToFile() {
        try (FileWriter writer = new FileWriter(historyFilePath)) {
            for (Map.Entry<String, List<String>> entry : userCommandHistory.entrySet()) {
                String username = entry.getKey();
                List<String> commands = entry.getValue();

                for (String command : commands) {
                    // Format: username,command
                    writer.write(username + "," + command + "\n");
                }
            }
        } catch (IOException e) {
            System.err.println("Error saving history to file: " + e.getMessage());
        }
    }
}
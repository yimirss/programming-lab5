package org.example;

import org.example.cli.CLIApplication;
import org.example.command.core.CommandExecutor;
import org.example.repository.impl.HistoryRepository;
import org.example.repository.impl.TicketRepository;
import io.github.cdimascio.dotenv.Dotenv;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Dotenv dotenv = Dotenv.load();

        String historyPath = dotenv.get("HISTORY_PATH");
        String ticketPath = dotenv.get("TICKET_PATH");
        String scriptsLibraryPath = dotenv.get("SCRIPTS_LIBRARY_PATH");

        TicketRepository ticketRepository = new TicketRepository(ticketPath);
        ticketRepository.load();
        HistoryRepository historyRepository = new HistoryRepository(100, historyPath);

        CommandExecutor commandExecutor = new CommandExecutor(historyRepository, ticketRepository);

        CLIApplication cliApplication = new CLIApplication(commandExecutor, scriptsLibraryPath);
        cliApplication.start();
    }
}
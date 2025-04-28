package org.example.cli;

import org.example.cli.builder.TicketBuilder;
import org.example.cli.processor.*;
import org.example.command.core.CommandExecutor;

import java.io.*;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * Class of CLI application
 */
public class CLIApplication {
    private final String scriptsLibraryPath;
    private final Set<String> runningScripts;

    private final SystemCommandProcessor systemCommandProcessor;
    private final KeyTicketCommandProcessor keyTicketCommandProcessor;
    private final NoArgsCommandProcessor noArgsCommandProcessor;
    private final TicketCommandProcessor ticketCommandProcessor;
    private final IdTicketCommandProcessor idTicketCommandProcessor;
    private final KeyCommandProcessor keyCommandProcessor;

    public CLIApplication(CommandExecutor commandExecutor, String scriptsLibraryPath) {
        this.scriptsLibraryPath = scriptsLibraryPath;
        runningScripts = new HashSet<>();

        this.systemCommandProcessor = new SystemCommandProcessor();

        this.noArgsCommandProcessor = new NoArgsCommandProcessor(commandExecutor);
        this.keyCommandProcessor = new KeyCommandProcessor(commandExecutor);

        TicketBuilder ticketBuilder = TicketBuilder.createDefault();
        this.idTicketCommandProcessor = new IdTicketCommandProcessor(commandExecutor, ticketBuilder);
        this.keyTicketCommandProcessor = new KeyTicketCommandProcessor(commandExecutor, ticketBuilder);
        this.ticketCommandProcessor = new TicketCommandProcessor(commandExecutor,ticketBuilder);
    }

    public void start() {
        System.out.println("Type 'help' to see information about available commands");
        Scanner scanner = new Scanner(System.in);


        while (true) {
            System.out.print("> ");
            String inputLine = scanner.nextLine().trim();

            if (inputLine.isEmpty()) {
                continue;
            }

            String result = executeCommand(inputLine, true);

            System.out.println(result);

            if(systemCommandProcessor.wasExitCalled()) {
                break;
            }
        }
    }

    public String executeCommand(String inputLine, boolean isInteractive) {

        String[] input = inputLine.split("\\s+");
        String commandName = input[0].trim().toLowerCase();
        String rawArgs = inputLine.substring(commandName.length()).trim();

        if(systemCommandProcessor.supportedCommands.contains(commandName)) {
            return systemCommandProcessor.processCommand(commandName, rawArgs, isInteractive);
        } else if (noArgsCommandProcessor.supportedCommands.contains(commandName)) {
            return  noArgsCommandProcessor.processCommand(commandName, rawArgs, isInteractive);
        } else if(keyTicketCommandProcessor.supportedCommands.contains(commandName)) {
            return keyTicketCommandProcessor.processCommand(commandName, rawArgs, isInteractive);
        } else if(ticketCommandProcessor.supportedCommands.contains(commandName)) {
            return ticketCommandProcessor.processCommand(commandName, rawArgs, isInteractive);
        } else if (idTicketCommandProcessor.supportedCommands.contains(commandName)) {
            return idTicketCommandProcessor.processCommand(commandName, rawArgs, isInteractive);
        } else if (keyCommandProcessor.supportedCommands.contains(commandName)) {
            return keyCommandProcessor.processCommand(commandName, rawArgs, isInteractive);
        } else if (commandName.equals("execute_script")) {
            return executeScript(rawArgs);
        } else {
            return "Unsupported command";
        }
    }

    public String executeScript(String scriptName) {

        if(!checkScriptName(scriptName)) {
            return "Bad script name provided";
        }

        File file = new File(scriptsLibraryPath, scriptName);

        if (!file.exists() || !file.canRead()) {
            return "Can't read specified script: " + scriptName;
        }

        if (runningScripts.contains(scriptName)) {
            return "Recursive script execution detected. Skipping this script";
        }

        runningScripts.add(scriptName);

        try (InputStreamReader isr = new InputStreamReader(new FileInputStream(file));
             BufferedReader reader = new BufferedReader(isr)) {

            StringBuilder result = new StringBuilder();
            String inputLine;
            while ((inputLine = reader.readLine()) != null) {
                inputLine = inputLine.trim();

                if (inputLine.isEmpty() || inputLine.startsWith("#")) {
                    continue;
                }

                result.append(executeCommand(inputLine, false));
                result.append("\n");

                if(systemCommandProcessor.wasExitCalled()) {
                    break;
                }
            }

            return result.append("Script executed!").toString();
        } catch (IOException e) {
            return "Error: Failed to read script file: " + scriptName;
        } finally {
            runningScripts.remove(scriptName);
        }
    }

    public boolean checkScriptName(String scriptName) {
        return !scriptName.contains("*") && !scriptName.contains("?") && !scriptName.contains("|") &&
                !scriptName.contains("<") && !scriptName.contains(">") && !scriptName.contains(":") &&
                !scriptName.contains("\"") && !scriptName.contains("\\") && !scriptName.contains("/") &&
                !scriptName.contains("..");
    }
}

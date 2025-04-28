package org.example.cli.processor;

import java.util.Set;

/**
 * Base class for command processors. Implements custom logic for processing concrete group of commands
 */
public abstract class CommandProcessor {
    public final Set<String> supportedCommands;

    public CommandProcessor(Set<String> supportedCommands) {
        this.supportedCommands = supportedCommands;
    }

    public abstract String processCommand(String commandName, String rawArgs, boolean isInteractive);
}

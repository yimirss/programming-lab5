package org.example.command.core;

import org.example.dto.ExecuteCommandsDTO;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Class that validates CommandWithArgs
 */
public class CommandValidator {
    private final Map<String, Function<ExecuteCommandsDTO.CommandWithArgs, Boolean>> commandValidators;

    public CommandValidator() {
        commandValidators = new HashMap<>();
        commandValidators.put("insert", this::validateKeyTicketCommand);
        commandValidators.put("update", this::validateIdTicketCommand);
        commandValidators.put("remove_by_key", this::validateKeyCommand);
        commandValidators.put("clear", this::validateNoArgsCommand);
        commandValidators.put("save", this::validateNoArgsCommand);
        commandValidators.put("average_of_discount", this::validateNoArgsCommand);
        commandValidators.put("print_ascending", this::validateNoArgsCommand);
        commandValidators.put("print_descending_venue", this::validateNoArgsCommand);
        commandValidators.put("remove_greater", this::validateTicketCommand);
        commandValidators.put("replace_if_greater", this::validateKeyTicketCommand);
        commandValidators.put("show", this::validateNoArgsCommand);
        commandValidators.put("history", this::validateNoArgsCommand);
    }

    public boolean validateCommand(ExecuteCommandsDTO.CommandWithArgs commandWithArgs) {
        String commandName = commandWithArgs.commandName();

        Function<ExecuteCommandsDTO.CommandWithArgs, Boolean> validator = commandValidators.get(commandName);

        if (validator == null) {
            throw new IllegalArgumentException("CommandValidator: unknown command " + commandName);
        }

        return validator.apply(commandWithArgs);
    }

    private boolean validateKeyTicketCommand(ExecuteCommandsDTO.CommandWithArgs commandWithArgs) {
        return commandWithArgs.key() != null && commandWithArgs.ticket() != null && commandWithArgs.ticket().isValid();
    }

    private boolean validateKeyCommand(ExecuteCommandsDTO.CommandWithArgs commandWithArgs) {
        return commandWithArgs.key() != null;
    }

    private boolean validateTicketCommand(ExecuteCommandsDTO.CommandWithArgs commandWithArgs) {
        return commandWithArgs.ticket() != null && commandWithArgs.ticket().isValid();
    }

    private boolean validateNoArgsCommand(ExecuteCommandsDTO.CommandWithArgs commandWithArgs) {
        return true;
    }

    private boolean validateIdTicketCommand(ExecuteCommandsDTO.CommandWithArgs commandWithArgs) {
        return commandWithArgs.id() != null && commandWithArgs.ticket() != null && commandWithArgs.ticket().isValid();
    }
}

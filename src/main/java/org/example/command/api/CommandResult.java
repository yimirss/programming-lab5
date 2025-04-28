package org.example.command.api;

/**
 * Option-like record that represents either successful data of command execution or failure
 */
public record CommandResult<T>(
        boolean success,
        T data,
        String errorMessage
) {
    public static <T> CommandResult<T> success(T data) {
        return new CommandResult<>(true, data, null);
    }

    public static <T> CommandResult<T> failure(String errorMessage) {
        return new CommandResult<>(false, null, errorMessage);
    }

    public boolean isSuccess() {
        return success;
    }

    public boolean isFailure() {
        return !success;
    }
}
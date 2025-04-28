package org.example.command.api;

public interface ICommand<T> {
    CommandResult<T> execute();
}

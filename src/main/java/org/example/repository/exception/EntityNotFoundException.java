package org.example.repository.exception;

public class EntityNotFoundException extends RepositoryException {
    private final Object id;

    public EntityNotFoundException(Object id) {
        super(String.format("Entity with id '%s' not found", id));
        this.id = id;
    }

    public Object getId() {
        return id;
    }
}
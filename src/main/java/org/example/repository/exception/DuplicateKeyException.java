package org.example.repository.exception;


public class DuplicateKeyException extends RepositoryException {
    private final Object key;

    public DuplicateKeyException(Object key) {
        super(String.format("Entity with key '%s' already exists", key));
        this.key = key;
    }

    public Object getKey() {
        return key;
    }
}
package ru.otus.exception;

public class EntityCreateException extends RuntimeException {
    public EntityCreateException(String message, Throwable cause) {
        super(message, cause);
    }
}

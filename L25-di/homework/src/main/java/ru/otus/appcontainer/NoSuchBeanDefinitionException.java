package ru.otus.appcontainer;

public class NoSuchBeanDefinitionException extends RuntimeException {
    public NoSuchBeanDefinitionException() {
    }

    public NoSuchBeanDefinitionException(String message) {
        super(message);
    }
}

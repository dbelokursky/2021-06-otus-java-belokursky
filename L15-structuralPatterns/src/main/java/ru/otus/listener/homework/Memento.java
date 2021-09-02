package ru.otus.listener.homework;

import lombok.Getter;
import ru.otus.model.Message;

import java.time.LocalDateTime;

@Getter
public class Memento {

    private final Message message;

    private final LocalDateTime createdAt;

    public Memento(Message message, LocalDateTime createdAt) {
        this.message = message.copy();
        this.createdAt = createdAt;
    }
}

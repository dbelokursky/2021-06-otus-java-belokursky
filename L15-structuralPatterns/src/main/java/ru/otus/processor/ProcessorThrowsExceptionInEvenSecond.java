package ru.otus.processor;

import lombok.RequiredArgsConstructor;
import ru.otus.listener.homework.DateTimeProvider;
import ru.otus.model.Message;

@RequiredArgsConstructor
public class ProcessorThrowsExceptionInEvenSecond implements Processor {

    private final DateTimeProvider dateTimeProvider;

    @Override
    public Message process(Message message) {
        while (true) {
            if (dateTimeProvider.getDateTime().getSecond() % 2 == 0) {
                throw new RuntimeException("Even second");
            }
        }
    }
}

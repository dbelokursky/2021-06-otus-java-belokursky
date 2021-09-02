package ru.otus.processor;

import lombok.RequiredArgsConstructor;
import ru.otus.model.Message;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class ProcessorThrowsExceptionInEvenSecond implements Processor {

    @Override
    public Message process(Message message) {
        while (true) {
            if (LocalDateTime.now().getSecond() % 2 == 0) {
                throw new RuntimeException("Even second");
            }
        }
    }
}

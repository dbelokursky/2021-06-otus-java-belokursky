package ru.otus.processor;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.otus.model.Message;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProcessorThrowsExceptionInEvenSecondTest {

    @DisplayName("Процессор кидает исключение в четную секунду")
    @Test
    void processTest() {
        //given
        LocalDateTime time = LocalDateTime.of(1, 1, 1, 1, 1, 2);
        ProcessorThrowsExceptionInEvenSecond processor = new ProcessorThrowsExceptionInEvenSecond(() -> time);
        String expected = "Even second";

        //when
        RuntimeException exception = assertThrows(
                RuntimeException.class, () -> processor.process(Mockito.mock(Message.class)));

        //then
        assertEquals(expected, exception.getMessage());
    }
}
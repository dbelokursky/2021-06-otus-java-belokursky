package ru.otus;

import ru.otus.handler.ComplexProcessor;
import ru.otus.listener.ListenerPrinterConsole;
import ru.otus.model.Message;
import ru.otus.processor.ProcessorSwitchFields11and12;
import ru.otus.processor.ProcessorThrowsExceptionInEvenSecond;

import java.time.LocalDateTime;
import java.util.List;

public class HomeWork {

    /*
     Реализовать to do:
       1. Добавить поля field11 - field13 (для field13 используйте класс ObjectForMessage)
       2. Сделать процессор, который поменяет местами значения field11 и field12
       3. Сделать процессор, который будет выбрасывать исключение в четную секунду
       (сделайте тест с гарантированным результатом)
       Секунда должна определяться во время выполнения.
       4. Сделать Listener для ведения истории: старое сообщение - новое
       (подумайте, как сделать, чтобы сообщения не портились)
     */

    public static void main(String[] args) {
        var processors = List.of(new ProcessorSwitchFields11and12(),
                new ProcessorThrowsExceptionInEvenSecond(LocalDateTime::now));

        var complexProcessor = new ComplexProcessor(processors, ex -> {
        });
        var listenerPrinter = new ListenerPrinterConsole();
        complexProcessor.addListener(listenerPrinter);

        var message = new Message.Builder(1L)
                .field1("field1")
                .field2("field2")
                .field3("field3")
                .field6("field6")
                .field10("field10")
                .field11("field11")
                .field12("field12")
                .build();

        var result = complexProcessor.handle(message);
        System.out.println("result:" + result);

        complexProcessor.removeListener(listenerPrinter);
    }
}

package ru.otus.springdatajdbchw.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.otus.messagesystem.RequestHandler;
import ru.otus.messagesystem.client.ResultDataType;
import ru.otus.messagesystem.message.Message;

import java.util.Optional;

@Slf4j
@Component
public class SaveClientResponseHandler implements RequestHandler {

    @Override
    public <T extends ResultDataType> Optional<Message<T>> handle(Message<T> msg) {
        log.info("new message: {}", msg);

        try {
            var callback = msg.getCallback();
            if (callback != null) {
                callback.accept(msg.getData());
            } else {
                log.error("callback for messageId:{} not found", msg.getId());
            }
        } catch (Exception e) {
            log.error("message: {}", msg, e);
        }
        return Optional.empty();
    }
}

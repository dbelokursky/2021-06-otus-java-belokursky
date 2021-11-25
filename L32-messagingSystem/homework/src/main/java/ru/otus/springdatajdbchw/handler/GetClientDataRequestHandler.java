package ru.otus.springdatajdbchw.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.messagesystem.RequestHandler;
import ru.otus.messagesystem.client.ResultDataType;
import ru.otus.messagesystem.message.Message;
import ru.otus.springdatajdbchw.service.ClientService;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class GetClientDataRequestHandler implements RequestHandler {

    private final ClientService clientService;

    @Override
    public <T extends ResultDataType> Optional<Message<T>> handle(Message<T> msg) {
        return Optional.empty();
    }
}

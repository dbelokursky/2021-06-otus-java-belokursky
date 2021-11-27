package ru.otus.springdatajdbchw.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.otus.messagesystem.RequestHandler;
import ru.otus.messagesystem.client.ResultDataType;
import ru.otus.messagesystem.message.Message;
import ru.otus.messagesystem.message.MessageBuilder;
import ru.otus.springdatajdbchw.dto.ClientData;
import ru.otus.springdatajdbchw.service.ClientService;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Component
public class GetClientDataRequestHandler implements RequestHandler {

    private final ClientService clientService;

    @Override
    public <T extends ResultDataType> Optional<Message<T>> handle(Message<T> msg) {
        var clientData = (ClientData) msg.getData();
        if (clientData.getClientData() != null) {
            clientService.saveClient(clientData.getClientData().get(0));
        }
        var data = new ClientData(clientData.getClientId(), clientService.getClients());
        return Optional.of(MessageBuilder.buildReplyMessage(msg, (T) data));
    }
}

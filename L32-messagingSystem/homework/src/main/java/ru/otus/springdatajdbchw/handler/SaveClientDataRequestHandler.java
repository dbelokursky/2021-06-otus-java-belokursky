package ru.otus.springdatajdbchw.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.messagesystem.RequestHandler;
import ru.otus.messagesystem.client.ResultDataType;
import ru.otus.messagesystem.message.Message;
import ru.otus.messagesystem.message.MessageBuilder;
import ru.otus.springdatajdbchw.dto.ClientData;
import ru.otus.springdatajdbchw.dto.ClientDto;
import ru.otus.springdatajdbchw.service.ClientService;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class SaveClientDataRequestHandler implements RequestHandler {

    private final ClientService clientService;

    @Override
    public <T extends ResultDataType> Optional<Message<T>> handle(Message<T> msg) {
        var clientData = (ClientData) msg.getData();
        ClientDto client = clientService.saveClient(clientData.getClientData().get(0));
        var data = new ClientData(clientData.getClientId(), List.of(client));
        return Optional.of(MessageBuilder.buildReplyMessage(msg, (T) data));
    }
}

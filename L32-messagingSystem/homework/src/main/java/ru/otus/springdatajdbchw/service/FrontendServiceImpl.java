package ru.otus.springdatajdbchw.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.messagesystem.client.MessageCallback;
import ru.otus.messagesystem.client.MsClient;
import ru.otus.messagesystem.message.MessageType;
import ru.otus.springdatajdbchw.component.ClientNameProvider;
import ru.otus.springdatajdbchw.dto.ClientData;
import ru.otus.springdatajdbchw.dto.ClientDto;

import java.util.List;

@RequiredArgsConstructor
@Service
public class FrontendServiceImpl implements FrontendService {

    private final MsClient frontendServiceClient;
    private final ClientNameProvider clientServiceClientNameProvider;

    @Override
    public void getClientData(long clientId, MessageCallback<ClientData> dataConsumer) {
        var outMsg = frontendServiceClient.produceMessage(clientServiceClientNameProvider.getClientName(),
                new ClientData(clientId), MessageType.CLIENT_DATA, dataConsumer);

        frontendServiceClient.sendMessage(outMsg);
    }

    @Override
    public void saveClientData(ClientDto client, MessageCallback<ClientData> dataConsumer) {
        var outMsg = frontendServiceClient.produceMessage(clientServiceClientNameProvider.getClientName(),
                new ClientData(0, List.of(client)), MessageType.SAVE_CLIENT, dataConsumer);

        frontendServiceClient.sendMessage(outMsg);
    }
}

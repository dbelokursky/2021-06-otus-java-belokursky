package ru.otus.springdatajdbchw.service;

import ru.otus.messagesystem.client.MessageCallback;
import ru.otus.springdatajdbchw.dto.ClientData;
import ru.otus.springdatajdbchw.dto.ClientDto;

public interface FrontendService {

    void getClientData(long clientId, MessageCallback<ClientData> dataConsumer);

    void saveClientData(ClientDto client, MessageCallback<ClientData> dataConsumer);

}

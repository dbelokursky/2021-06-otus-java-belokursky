package ru.otus.springdatajdbchw.dto;

import lombok.Data;
import ru.otus.messagesystem.client.ResultDataType;

import java.util.List;

@Data
public class ClientData implements ResultDataType {
    private final long clientId;
    private final List<ClientDto> clientData;

    public ClientData(long clientId) {
        this.clientId = clientId;
        this.clientData = null;
    }

    public ClientData(long clientId, List<ClientDto> clientData) {
        this.clientId = clientId;
        this.clientData = clientData;
    }
}

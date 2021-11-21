package ru.otus.springdatajdbchw.service;

import ru.otus.springdatajdbchw.dto.ClientDto;
import ru.otus.springdatajdbchw.model.Client;

import java.util.List;

public interface ClientService {

    List<ClientDto> getClients();

    ClientDto getClient(Long id);

    ClientDto saveClient(Client client);
}

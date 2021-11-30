package ru.otus.springdatajdbchw.service;

import ru.otus.springdatajdbchw.dto.ClientDto;

import java.util.List;

public interface ClientService {

    List<ClientDto> getClients();

    ClientDto getClient(Long id);

    ClientDto saveClient(ClientDto client);
}

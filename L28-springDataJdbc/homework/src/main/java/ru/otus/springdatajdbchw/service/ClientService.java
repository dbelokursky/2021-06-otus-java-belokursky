package ru.otus.springdatajdbchw.service;

import ru.otus.springdatajdbchw.model.Client;

import java.util.List;

public interface ClientService {

    List<Client> getClients();

    Client getClient(Long id);

    Client saveClient(Client client);
}

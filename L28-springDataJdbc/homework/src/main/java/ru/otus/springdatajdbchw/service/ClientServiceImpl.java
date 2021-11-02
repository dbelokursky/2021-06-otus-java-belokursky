package ru.otus.springdatajdbchw.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.springdatajdbchw.exception.ClientNotFoundException;
import ru.otus.springdatajdbchw.model.Client;
import ru.otus.springdatajdbchw.repository.ClientRepository;
import ru.otus.springdatajdbchw.transaction.TransactionManager;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    private final TransactionManager transactionManager;

    @Override
    public List<Client> getClients() {
        return clientRepository.findAll();
    }

    @Override
    public Client getClient(Long id) {
        return transactionManager.doInReadOnlyTransaction(() -> clientRepository.findById(id).orElseThrow(ClientNotFoundException::new));
    }

    @Override
    public Client saveClient(Client client) {
        return transactionManager.doInTransaction(() -> clientRepository.save(client));
    }
}

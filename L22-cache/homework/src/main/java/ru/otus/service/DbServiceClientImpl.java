package ru.otus.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.cachehw.MyCache;
import ru.otus.core.repository.DataTemplate;
import ru.otus.core.sessionmanager.TransactionRunner;
import ru.otus.model.Client;

import java.util.List;
import java.util.Optional;

public class DbServiceClientImpl implements DBServiceClient {
    private static final Logger log = LoggerFactory.getLogger(DbServiceClientImpl.class);

    private final DataTemplate<Client> clientDataTemplate;
    private final TransactionRunner transactionRunner;
    private final MyCache<Long, Client> cache;

    public MyCache<Long, Client> getCache() {
        return cache;
    }

    public DbServiceClientImpl(TransactionRunner transactionRunner, DataTemplate<Client> clientDataTemplate) {
        this.transactionRunner = transactionRunner;
        this.clientDataTemplate = clientDataTemplate;
        this.cache = new MyCache<>();
    }

    @Override
    public Client saveClient(Client client) {
        return transactionRunner.doInTransaction(connection -> {
            if (client.getId() == null) {
                var clientId = clientDataTemplate.insert(connection, client);
                var createdClient = new Client(clientId, client.getName());
                cache.put(createdClient.getId(), createdClient);
                return createdClient;
            }
            clientDataTemplate.update(connection, client);
            cache.put(client.getId(), client);
            return client;
        });
    }

    @Override
    public Optional<Client> getClient(long id) {
        return transactionRunner.doInTransaction(connection -> {
            Client clientFromCache = cache.get(id);
            if (clientFromCache != null) {
                return Optional.of(clientFromCache);
            }
            var clientOptional = clientDataTemplate.findById(connection, id);
            return clientOptional;
        });
    }

    @Override
    public List<Client> findAll() {
        return transactionRunner.doInTransaction(connection -> {
            var clientList = clientDataTemplate.findAll(connection);
            log.info("clientList:{}", clientList);
            return clientList;
        });
    }
}

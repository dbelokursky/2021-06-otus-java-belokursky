package ru.otus.springdatajdbchw.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.messagesystem.HandlersStoreImpl;
import ru.otus.messagesystem.MessageSystem;
import ru.otus.messagesystem.MessageSystemImpl;
import ru.otus.messagesystem.client.MsClientImpl;
import ru.otus.messagesystem.message.MessageType;
import ru.otus.springdatajdbchw.dto.ClientDto;
import ru.otus.springdatajdbchw.exception.ClientNotFoundException;
import ru.otus.springdatajdbchw.mapper.ClientMapper;
import ru.otus.springdatajdbchw.repository.ClientRepository;
import ru.otus.springdatajdbchw.transaction.TransactionManager;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    private final TransactionManager transactionManager;

    private final ClientMapper clientMapper;

    private static final String FRONTEND_SERVICE_CLIENT_NAME = "frontendService";
    private static final String DATABASE_SERVICE_CLIENT_NAME = "databaseService";
    private MessageSystem messageSystem;

    @PostConstruct
    private void init() {
        messageSystem = new MessageSystemImpl();

        var requestHandlerDatabaseStore = new HandlersStoreImpl();
        requestHandlerDatabaseStore.addHandler(MessageType.CLIENT_DATA, new GetUserDataRequestHandler(new DBServiceImpl()));
        var databaseMsClient = new MsClientImpl(DATABASE_SERVICE_CLIENT_NAME, messageSystem, requestHandlerDatabaseStore);
        messageSystem.addClient(databaseMsClient);

        var requestHandlerFrontendStore = new HandlersStoreImpl();
        requestHandlerFrontendStore.addHandler(MessageType.USER_DATA, new GetUserDataResponseHandler());

        var frontendMsClient = new MsClientImpl(FRONTEND_SERVICE_CLIENT_NAME, messageSystem, requestHandlerFrontendStore);
        var frontendService = new FrontendServiceImpl(frontendMsClient, DATABASE_SERVICE_CLIENT_NAME);
        messageSystem.addClient(frontendMsClient);

        frontendService.getUserData(1, data -> logger.info("got data:{}", data));
        frontendService.getUserData(2, data -> logger.info("got data:{}", data));

        Thread.sleep(100);
        messageSystem.dispose();
        logger.info("done");
    }

    @Override
    public List<ClientDto> getClients() {
        return clientRepository.findAll()
                .stream()
                .map(clientMapper::clientToClientDto)
                .toList();
    }

    @Override
    public ClientDto getClient(Long id) {
        return transactionManager.doInReadOnlyTransaction(() -> clientRepository.findById(id)
                .map(clientMapper::clientToClientDto)
                .orElseThrow(ClientNotFoundException::new));
    }

    @Override
    public ClientDto saveClient(ClientDto client) {
        return clientMapper.clientToClientDto(transactionManager.doInTransaction(() ->
                clientRepository.save(clientMapper.clientDtoToClient(client))));
    }
}

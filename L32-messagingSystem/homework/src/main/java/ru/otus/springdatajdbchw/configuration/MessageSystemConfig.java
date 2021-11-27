package ru.otus.springdatajdbchw.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.messagesystem.HandlersStore;
import ru.otus.messagesystem.HandlersStoreImpl;
import ru.otus.messagesystem.MessageSystem;
import ru.otus.messagesystem.MessageSystemImpl;
import ru.otus.messagesystem.client.MsClient;
import ru.otus.messagesystem.client.MsClientImpl;
import ru.otus.messagesystem.message.MessageType;
import ru.otus.springdatajdbchw.handler.GetClientDataRequestHandler;
import ru.otus.springdatajdbchw.handler.GetClientDataResponseHandler;

@Configuration
public class MessageSystemConfig {

    private static final String CLIENT_SERVICE_CLIENT_NAME = "clientService";
    private static final String FRONTEND_SERVICE_CLIENT_NAME = "frontendService";

    @Bean
    public MessageSystem messageSystem() {
        return new MessageSystemImpl();
    }

    @Bean
    public HandlersStore clientDataRequestHandlerStore(GetClientDataRequestHandler requestHandler) {
        HandlersStore handlersStore = new HandlersStoreImpl();
        handlersStore.addHandler(MessageType.CLIENT_DATA, requestHandler);
        return handlersStore;
    }

    @Bean
    public HandlersStore clientDataResponseHandlerStore(GetClientDataResponseHandler responseHandler) {
        HandlersStore handlersStore = new HandlersStoreImpl();
        handlersStore.addHandler(MessageType.CLIENT_DATA, responseHandler);
        return handlersStore;
    }

    @Bean
    public MsClient clientServiceClient(MessageSystem messageSystem, HandlersStore clientDataRequestHandlerStore) {
        var clientServiceClient = new MsClientImpl(CLIENT_SERVICE_CLIENT_NAME, messageSystem, clientDataRequestHandlerStore);
        messageSystem.addClient(clientServiceClient);
        return clientServiceClient;
    }

    @Bean
    public MsClient frontendServiceClient(MessageSystem messageSystem, HandlersStore clientDataResponseHandlerStore) {
        var frontendServiceClient = new MsClientImpl(FRONTEND_SERVICE_CLIENT_NAME, messageSystem, clientDataResponseHandlerStore);
        messageSystem.addClient(frontendServiceClient);
        return frontendServiceClient;
    }

    @Bean
    public String clientServiceClientName() {
        return CLIENT_SERVICE_CLIENT_NAME;
    }

    @Bean
    public String frontendServiceClientName() {
        return FRONTEND_SERVICE_CLIENT_NAME;
    }
}

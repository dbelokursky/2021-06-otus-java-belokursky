package ru.otus.springdatajdbchw.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.messagesystem.HandlersStore;
import ru.otus.messagesystem.HandlersStoreImpl;
import ru.otus.messagesystem.MessageSystem;
import ru.otus.messagesystem.MessageSystemImpl;
import ru.otus.messagesystem.client.MsClient;
import ru.otus.messagesystem.client.MsClientImpl;
import ru.otus.messagesystem.message.MessageType;
import ru.otus.springdatajdbchw.component.ClientNameProvider;
import ru.otus.springdatajdbchw.handler.GetClientDataRequestHandler;
import ru.otus.springdatajdbchw.handler.GetClientDataResponseHandler;
import ru.otus.springdatajdbchw.handler.SaveClientDataRequestHandler;
import ru.otus.springdatajdbchw.handler.SaveClientResponseHandler;

@RequiredArgsConstructor
@Configuration
public class MessageSystemConfig {

    private final ClientNameProvider frontendServiceClientNameProvider;
    private final ClientNameProvider clientServiceClientNameProvider;

    @Bean
    public MessageSystem messageSystem() {
        return new MessageSystemImpl();
    }

    @Bean
    public HandlersStore clientDataRequestHandlerStore(GetClientDataRequestHandler getClientDataRequestHandler,
                                                       SaveClientDataRequestHandler saveClientDataRequestHandler) {
        HandlersStore handlersStore = new HandlersStoreImpl();
        handlersStore.addHandler(MessageType.CLIENT_DATA, getClientDataRequestHandler);
        handlersStore.addHandler(MessageType.SAVE_CLIENT, saveClientDataRequestHandler);
        return handlersStore;
    }

    @Bean
    public HandlersStore clientDataResponseHandlerStore(GetClientDataResponseHandler clientDataResponseHandler,
                                                        SaveClientResponseHandler saveClientResponseHandler) {
        HandlersStore handlersStore = new HandlersStoreImpl();
        handlersStore.addHandler(MessageType.CLIENT_DATA, clientDataResponseHandler);
        handlersStore.addHandler(MessageType.SAVE_CLIENT, saveClientResponseHandler);
        return handlersStore;
    }

    @Bean
    public MsClient clientServiceClient(MessageSystem messageSystem, HandlersStore clientDataRequestHandlerStore) {
        var clientServiceClient = new MsClientImpl(clientServiceClientNameProvider.getClientName(), messageSystem,
                clientDataRequestHandlerStore);
        messageSystem.addClient(clientServiceClient);
        return clientServiceClient;
    }

    @Bean
    public MsClient frontendServiceClient(MessageSystem messageSystem, HandlersStore clientDataResponseHandlerStore) {
        var frontendServiceClient = new MsClientImpl(frontendServiceClientNameProvider.getClientName(), messageSystem,
                clientDataResponseHandlerStore);
        messageSystem.addClient(frontendServiceClient);
        return frontendServiceClient;
    }

}

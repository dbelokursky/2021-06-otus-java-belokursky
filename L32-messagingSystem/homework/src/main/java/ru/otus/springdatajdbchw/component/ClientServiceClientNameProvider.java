package ru.otus.springdatajdbchw.component;

import org.springframework.stereotype.Component;

@Component
public class ClientServiceClientNameProvider implements ClientNameProvider{

    private static final String CLIENT_SERVICE_CLIENT_NAME = "clientService";

    @Override
    public String getClientName() {
        return CLIENT_SERVICE_CLIENT_NAME;
    }
}

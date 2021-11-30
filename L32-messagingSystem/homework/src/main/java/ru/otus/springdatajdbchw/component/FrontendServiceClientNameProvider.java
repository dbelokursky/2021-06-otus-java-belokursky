package ru.otus.springdatajdbchw.component;

import org.springframework.stereotype.Component;

@Component
public class FrontendServiceClientNameProvider implements ClientNameProvider {

    private static final String FRONTEND_SERVICE_CLIENT_NAME = "frontendService";

    @Override
    public String getClientName() {
        return FRONTEND_SERVICE_CLIENT_NAME;
    }
}

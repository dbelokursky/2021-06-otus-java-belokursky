package ru.otus.springdatajdbchw.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.springdatajdbchw.dto.ClientDto;
import ru.otus.springdatajdbchw.service.FrontendService;

@RestController
@RequiredArgsConstructor
public class ClientWebSocketController {

    private final SimpMessagingTemplate messagingTemplate;

    private final FrontendService frontendService;

    @GetMapping("/getClients")
    public void getClients() {
        frontendService.getClientData(0, data ->
                messagingTemplate.convertAndSend("/topic/clients", data.getClientData()));
    }

    @MessageMapping("/clients/save")
    public void saveClient(ClientDto client) {
        frontendService.saveClientData(client, data ->
                messagingTemplate.convertAndSend("/topic/clients", data.getClientData()));
    }

}

package ru.otus.springdatajdbchw.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.springdatajdbchw.dto.ClientDto;
import ru.otus.springdatajdbchw.model.Client;
import ru.otus.springdatajdbchw.service.ClientService;

@RestController
@RequiredArgsConstructor
public class ClientWebSocketController {

    private final SimpMessagingTemplate messagingTemplate;

    private final ClientService clientService;

    @GetMapping("/getClients")
    public void getClients() {
        messagingTemplate.convertAndSend("/topic/clients", clientService.getClients());
    }

    @MessageMapping("/clients/save")
    @SendTo("/topic/clients")
    public ClientDto saveClient(Client client) {
        return clientService.saveClient(client);
    }

}

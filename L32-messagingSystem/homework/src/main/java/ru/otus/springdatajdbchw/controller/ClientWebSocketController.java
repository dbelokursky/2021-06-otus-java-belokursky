package ru.otus.springdatajdbchw.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.springdatajdbchw.dto.ClientDto;
import ru.otus.springdatajdbchw.service.ClientService;

@RestController
@RequiredArgsConstructor
public class ClientWebSocketController {

    private final SimpMessagingTemplate messagingTemplate;

    private final ClientService clientService;

    @Scheduled(fixedRate = 1000)
    public void getClients() {
        messagingTemplate.convertAndSend("/topic/clients", clientService.getClients());
    }

    @MessageMapping("/clients/save")
//    @SendTo("/topic/clients")
    public ClientDto saveClient(ClientDto client) {
        return clientService.saveClient(client);
    }

}

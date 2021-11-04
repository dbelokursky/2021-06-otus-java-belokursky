package ru.otus.springdatajdbchw.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;
import ru.otus.springdatajdbchw.model.Client;
import ru.otus.springdatajdbchw.service.ClientService;

@RequiredArgsConstructor
@Controller("/clients")
public class ClientController {

    private final ClientService clientService;

    @GetMapping({"/", "/clients"})
    public String getClients(Model model) {
        model.addAttribute("clients", clientService.getClients());
        return "clients";
    }

    @GetMapping("/add-client")
    public String getAddClient(Model model) {
        model.addAttribute("client", new Client());
        return "addClient";
    }

    @PostMapping("/add-client")
    public RedirectView addClient(@ModelAttribute Client client) {
        clientService.saveClient(client);
        return new RedirectView("/");
    }
}

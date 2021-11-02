package ru.otus.springdatajdbchw.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.springdatajdbchw.model.Address;
import ru.otus.springdatajdbchw.model.Client;
import ru.otus.springdatajdbchw.model.Phone;
import ru.otus.springdatajdbchw.service.ClientService;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("ClientController: тест контроллера клиентов")
@SpringBootTest
@AutoConfigureMockMvc
class ClientControllerTest {

    @MockBean
    private ClientService clientService;

    @Autowired
    private MockMvc mvc;

    @DisplayName("Get clients test")
    @Test
    void getClients() throws Exception {
        Client expected = Client.builder()
                .id(1L)
                .name("name1")
                .address(new Address(1L, "street", "4333333", 1L))
                .phones(Set.of(new Phone(1L, "77777777", 1L)))
                .build();
        given(clientService.getClients()).willReturn(List.of(expected));

        mvc.perform(get("/clients"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"));
    }

    @DisplayName("Get add-client page")
    @Test
    void getAddClient() throws Exception {
        mvc.perform(get("/clients/add-client"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"));
    }

    @DisplayName("Add client test")
    @Test
    void addClient() throws Exception {
        Client expected = Client.builder()
                .id(1L)
                .name("name1")
                .address(new Address(1L, "street", "4333333", 1L))
                .phones(Set.of(new Phone(1L, "77777777", 1L)))
                .build();

        given(clientService.saveClient(expected)).willReturn(expected);

        mvc.perform(post("/clients/add-client", expected))
                .andExpect(status().is3xxRedirection());

//        verify(clientService, times(1)).saveClient(expected);
    }
}
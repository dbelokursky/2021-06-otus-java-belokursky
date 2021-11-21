package ru.otus.springdatajdbchw.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.springdatajdbchw.dto.ClientDto;
import ru.otus.springdatajdbchw.exception.ClientNotFoundException;
import ru.otus.springdatajdbchw.mapper.ClientMapper;
import ru.otus.springdatajdbchw.model.Client;
import ru.otus.springdatajdbchw.repository.ClientRepository;
import ru.otus.springdatajdbchw.transaction.TransactionManager;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    private final TransactionManager transactionManager;

    private final ClientMapper clientMapper;

    @Override
    public List<ClientDto> getClients() {
        return clientRepository.findAll()
                .stream()
                .map(clientMapper::clientToClientDto)
                .collect(Collectors.toList());
    }

    @Override
    public ClientDto getClient(Long id) {
        return transactionManager.doInReadOnlyTransaction(() -> clientRepository.findById(id)
                .map(clientMapper::clientToClientDto)
                .orElseThrow(ClientNotFoundException::new));
    }

    @Override
    public ClientDto saveClient(Client client) {
        return clientMapper.clientToClientDto(transactionManager.doInTransaction(() -> clientRepository.save(client)));
    }
}

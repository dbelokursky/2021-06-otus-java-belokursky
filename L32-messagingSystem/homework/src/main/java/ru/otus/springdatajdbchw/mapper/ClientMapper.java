package ru.otus.springdatajdbchw.mapper;

import org.mapstruct.Mapper;
import ru.otus.springdatajdbchw.dto.ClientDto;
import ru.otus.springdatajdbchw.model.Address;
import ru.otus.springdatajdbchw.model.Client;
import ru.otus.springdatajdbchw.model.Phone;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class ClientMapper {

    public ClientDto clientToClientDto(Client client) {
        return ClientDto.builder()
                .name(client.getName())
                .street(Optional.ofNullable(client.getAddress()).map(Address::getStreet).orElse(""))
                .zipCode(Optional.ofNullable(client.getAddress()).map(Address::getZipCode).orElse(""))
                .phones(client.getPhones().stream().map(Phone::getPhoneNumber).collect(Collectors.joining(",")))
                .build();
    }

    public Client clientDtoToClient(ClientDto clientDto) {
        return Client.builder()
                .name(clientDto.getName())
                .address(new Address(clientDto.getStreet(), clientDto.getZipCode()))
                .phones(Arrays.stream(clientDto.getPhones().split(",")).map(Phone::new).collect(Collectors.toSet()))
                .build();
    }
}

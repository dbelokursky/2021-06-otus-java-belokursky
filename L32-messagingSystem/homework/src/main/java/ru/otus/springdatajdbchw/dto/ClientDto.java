package ru.otus.springdatajdbchw.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClientDto {
    private final String name;
    private final String street;
    private final String zipCode;
    private final String phones;
}

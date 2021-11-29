package ru.otus.springdatajdbchw.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClientDto {
    private String name;
    private String street;
    private String zipCode;
    private String phones;
}

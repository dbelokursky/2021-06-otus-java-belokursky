package ru.otus.springdatajdbchw.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Phone {

    @Id
    private long id;

    private String phoneNumber;

    private Long clientId;

    public Phone(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Phone(long id, String phoneNumber, Long clientId) {
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.clientId = clientId;
    }
}

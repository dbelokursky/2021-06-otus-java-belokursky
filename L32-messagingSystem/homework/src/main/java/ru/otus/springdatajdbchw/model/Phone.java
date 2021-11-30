package ru.otus.springdatajdbchw.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
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

package ru.otus.springdatajdbchw.service;

import ru.otus.springdatajdbchw.model.Phone;

import java.util.Set;

public class ConverterServiceImpl implements ConverterService {
    @Override
    public Set<Phone> stringToSet(String phoneNumber) {
        return Set.of(new Phone(phoneNumber));
    }
}

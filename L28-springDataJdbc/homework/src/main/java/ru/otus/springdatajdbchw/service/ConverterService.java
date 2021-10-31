package ru.otus.springdatajdbchw.service;

import ru.otus.springdatajdbchw.model.Phone;

import java.util.Set;

public interface ConverterService {

    Set<Phone> stringToSet(String phoneNumber);
}

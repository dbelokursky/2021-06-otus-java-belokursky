package ru.otus.springdatajdbchw.service;

import ru.otus.springdatajdbchw.model.Address;
import ru.otus.springdatajdbchw.model.Phone;

import java.util.Set;

public interface FormatterService {

    String formatPhones(Set<Phone> phones);

    String formatAddress(Address address);
}

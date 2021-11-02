package ru.otus.springdatajdbchw.service;

import org.springframework.stereotype.Service;
import ru.otus.springdatajdbchw.model.Address;
import ru.otus.springdatajdbchw.model.Phone;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FormatterServiceImpl implements FormatterService {

    @Override
    public String formatPhones(Set<Phone> phones) {
        return phones.stream()
                .map(Phone::getPhoneNumber)
                .collect(Collectors.joining(", "));
    }

    @Override
    public String formatAddress(Address address) {
        return String.format("Street: %s, zip code: %s", address.getStreet(), address.getZipCode());
    }
}

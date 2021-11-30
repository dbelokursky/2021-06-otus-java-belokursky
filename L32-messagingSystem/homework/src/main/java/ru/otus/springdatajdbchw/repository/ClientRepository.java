package ru.otus.springdatajdbchw.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.springdatajdbchw.model.Client;

import java.util.List;

public interface ClientRepository extends CrudRepository<Client, Long> {

    List<Client> findAll();
}

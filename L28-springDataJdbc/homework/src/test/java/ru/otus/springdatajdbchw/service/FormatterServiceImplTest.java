package ru.otus.springdatajdbchw.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.springdatajdbchw.model.Phone;

import java.util.Set;

import static org.assertj.core.api.Assertions.*;

class FormatterServiceImplTest {

    private FormatterService formatterService = new FormatterServiceImpl();

    @DisplayName("FormatterService test")
    @Test
    void formatPhones() {
        Phone phone1 = new Phone(1L, "1111111111", 1L);
        Phone phone2 = new Phone(1L, "2222222222", 1L);

        String actual = formatterService.formatPhones(Set.of(phone1, phone2));

        assertThat(actual).contains(phone1.getPhoneNumber()).contains(phone2.getPhoneNumber());
    }
}
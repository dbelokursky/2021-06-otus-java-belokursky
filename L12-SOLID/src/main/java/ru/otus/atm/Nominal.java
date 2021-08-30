package ru.otus.atm;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Nominal {
    FIFTY(50, "Пятьдесят рублей"),
    HUNDRED(100, "Сто рублей"),
    FIVE_HUNDRED(500, "Пятьсот рублей"),
    THOUSAND(1000, "Тысяча рублей"),
    TWO_THOUSAND(2000, "Две тысячи рублей"),
    FIVE_THOUSAND(5000, "Две тысячи рублей");


    private int value;
    private String description;

}

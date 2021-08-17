package ru.otus.atm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class MoneyCell {

    private final Nominal nominal;

    private volatile long banknotesCount;

}

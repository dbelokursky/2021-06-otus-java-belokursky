package ru.otus.atm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public abstract class MoneyHolder {

    private final Nominal nominal;

    private volatile long banknotesCount;
}

package ru.otus.atm;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MoneyHolder {

    private final Nominal nominal;

    private long banknotesCount;

    public void setBanknotesCount(long banknotesCount) {
        if (banknotesCount >= 0) {
            this.banknotesCount = banknotesCount;
        } else {
            throw new IllegalArgumentException(String.format("Количество банкнот не может быть отрицательным: %d", banknotesCount));
        }
    }

    public int getNominalValue() {
        return nominal.getValue();
    }
}

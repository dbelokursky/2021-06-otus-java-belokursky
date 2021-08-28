package ru.otus.atm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.otus.atm.exceptions.NotEnoughMoneyException;

@Getter
@AllArgsConstructor
public class MoneyHolder {

    public static final String ADD_ERROR_TEMPLATE = "Количество банкнот не может быть отрицательным: %d";
    public static final String EXTRACT_ERROR_TEMPLATE = "Недостаточно банкнот. Запрошено: %d. Доступно: %d";

    private final Nominal nominal;

    private long banknotesCount;

    public void add(long banknotesCount) {
        if (banknotesCount >= 0) {
            this.banknotesCount += banknotesCount;
        } else {
            throw new IllegalArgumentException(String.format(ADD_ERROR_TEMPLATE, banknotesCount));
        }
    }

    public void extract(long banknotesCount) {
        if (banknotesCount >= 0 && this.banknotesCount >= banknotesCount) {
            this.banknotesCount -= banknotesCount;
        } else {
            throw new NotEnoughMoneyException(String.format(EXTRACT_ERROR_TEMPLATE, banknotesCount, this.banknotesCount));
        }
    }

    public int getNominalValue() {
        return nominal.getValue();
    }
}

package ru.otus.atm;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

class AtmTest {

    Atm atm;

    @BeforeEach
        //8650
    void setUp() {
        atm = new Atm(1, 1, 1,
                1, 1, 1);
    }

    @DisplayName("Must return true if the amount of money and banknotes is sufficient for issuing")
    @Test
    void withdrawMoneyHappyPath() {
        Assertions.assertThat(atm.withdrawMoney(8000L)).isTrue();

        long expected = 650;
        long actual = atm.getMoneyAmountAvailable();

        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("When there is not enough money in the ATM, should throw an exception: NotEnoughMoneyException")
    @Test
    void withdrawMoneyUnhappyPath() {
        Exception exception = assertThrows(NotEnoughMoneyException.class, () -> atm.withdrawMoney(9000L));

        String expected = "В банкомате недостаточно средств.";
        String actual = exception.getMessage();

        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    void depositMoneyHappyPath() {
        long expected = 8650 * 2;
        List<CashUnit> cashUnits = List.of(
                new CashUnit(Nominal.FIFTY, 1),
                new CashUnit(Nominal.HUNDRED, 1),
                new CashUnit(Nominal.FIVE_HUNDRED, 1),
                new CashUnit(Nominal.THOUSAND, 1),
                new CashUnit(Nominal.TWO_THOUSAND, 1),
                new CashUnit(Nominal.FIVE_THOUSAND, 1));

        atm.depositMoney(cashUnits);
        long actual = atm.getMoneyAmountAvailable();

        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("The amount of money in the ATM is equal to the amount of money during initialization.")
    @Test
    void getMoneyAmountAvailable() {
        long expected = 8650;

        long actual = atm.getMoneyAmountAvailable();

        Assertions.assertThat(actual).isEqualTo(expected);
    }
}
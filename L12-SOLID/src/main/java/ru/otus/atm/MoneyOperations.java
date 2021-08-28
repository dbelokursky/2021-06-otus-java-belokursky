package ru.otus.atm;

import java.util.List;

public interface MoneyOperations {

    boolean withdrawMoney(long amount);

    boolean depositMoney(List<CashUnit> cashUnits);

    long getMoneyAmountAvailable();
}

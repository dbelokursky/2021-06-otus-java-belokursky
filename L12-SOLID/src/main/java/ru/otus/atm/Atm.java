package ru.otus.atm;

import ru.otus.atm.exceptions.ImpossibleToIssueRequestAmountException;
import ru.otus.atm.exceptions.NotEnoughMoneyException;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static ru.otus.atm.Nominal.*;


public class Atm implements MoneyOperations {
    private Map<Nominal, MoneyCell> moneyCells;

    public Atm(int countBanknotesWithNominal50, int countBanknotesWithNominal100, int countBanknotesWithNominal500,
               int countBanknotesWithNominal1000, int countBanknotesWithNominal2000, int countBanknotesWithNominal5000) {

        this.moneyCells = new TreeMap<>(Comparator.comparingInt(Nominal::getValue).reversed());
        moneyCells.put(FIFTY, new MoneyCell(FIFTY, countBanknotesWithNominal50));
        moneyCells.put(HUNDRED, new MoneyCell(HUNDRED, countBanknotesWithNominal100));
        moneyCells.put(FIVE_HUNDRED, new MoneyCell(FIVE_HUNDRED, countBanknotesWithNominal500));
        moneyCells.put(THOUSAND, new MoneyCell(THOUSAND, countBanknotesWithNominal1000));
        moneyCells.put(TWO_THOUSAND, new MoneyCell(TWO_THOUSAND, countBanknotesWithNominal2000));
        moneyCells.put(FIVE_THOUSAND, new MoneyCell(FIVE_THOUSAND, countBanknotesWithNominal5000));
    }

    public Atm() {
        this.moneyCells = new TreeMap<>(Comparator.comparingInt(Nominal::getValue).reversed());
        moneyCells.put(FIFTY, new MoneyCell(FIFTY, 0));
        moneyCells.put(HUNDRED, new MoneyCell(HUNDRED, 0));
        moneyCells.put(FIVE_HUNDRED, new MoneyCell(FIVE_HUNDRED, 0));
        moneyCells.put(THOUSAND, new MoneyCell(THOUSAND, 0));
        moneyCells.put(TWO_THOUSAND, new MoneyCell(TWO_THOUSAND, 0));
        moneyCells.put(FIVE_THOUSAND, new MoneyCell(FIVE_THOUSAND, 0));
    }

    public boolean withdrawMoney(long amount) {
        if (getMoneyAmountAvailable() < amount) {
            throw new NotEnoughMoneyException("There are not enough funds in the ATM.");
        }

        Map<Nominal, MoneyCell> tmpMoneyCells = createCopy(moneyCells);
        long tmpAmount = amount;
        long remainder = 0;
        long resultOfDivision;
        for (MoneyCell cell : tmpMoneyCells.values()) {
            if (tmpAmount >= cell.getNominalValue() && cell.getBanknotesCount() > 0) {
                remainder = tmpAmount % cell.getNominalValue();
                resultOfDivision = tmpAmount / cell.getNominalValue();
                if (cell.getBanknotesCount() >= resultOfDivision) {
                    tmpAmount = tmpAmount - cell.getNominalValue() * resultOfDivision;
                    cell.extract(resultOfDivision);
                }
            }
        }
        if (remainder == 0) {
            moneyCells = tmpMoneyCells;
            return true;
        } else {
            throw new ImpossibleToIssueRequestAmountException(String.format("The requested amount cannot be issued: %d", amount));
        }
    }

    public boolean depositMoney(List<CashUnit> cashUnits) {
        Map<Nominal, MoneyCell> tmpMoneyCells = createCopy(moneyCells);
        for (CashUnit cashUnit : cashUnits) {
            MoneyCell moneyCell = tmpMoneyCells.get(cashUnit.getNominal());
            moneyCell.add(cashUnit.getBanknotesCount());
        }
        moneyCells = tmpMoneyCells;
        return true;
    }

    public long getMoneyAmountAvailable() {
        return moneyCells.values().stream()
                .map(cell -> cell.getNominalValue() * cell.getBanknotesCount())
                .reduce(0L, Long::sum);
    }

    private Map<Nominal, MoneyCell> createCopy(Map<Nominal, MoneyCell> original) {
        Map<Nominal, MoneyCell> result = new TreeMap<>(Comparator.comparingInt(Nominal::getValue).reversed());
        for (MoneyCell cell : original.values()) {
            result.put(cell.getNominal(), new MoneyCell(cell));
        }
        return result;
    }
}

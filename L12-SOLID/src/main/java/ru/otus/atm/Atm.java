package ru.otus.atm;

import java.util.Map;

import static ru.otus.atm.Nominal.*;

public class Atm {
    private final Map<Nominal, MoneyCell> moneyCells;

    public Atm(int countBanknotesWithNominal50, int countBanknotesWithNominal100, int countBanknotesWithNominal500,
               int countBanknotesWithNominal1000, int countBanknotesWithNominal2000, int countBanknotesWithNominal5000) {

        this.moneyCells = Map.of(
                FIFTY, new MoneyCell(FIFTY, countBanknotesWithNominal50),
                HUNDRED, new MoneyCell(Nominal.HUNDRED, countBanknotesWithNominal100),
                FIVE_HUNDRED, new MoneyCell(Nominal.FIVE_HUNDRED, countBanknotesWithNominal500),
                THOUSAND, new MoneyCell(THOUSAND, countBanknotesWithNominal1000),
                TWO_THOUSAND, new MoneyCell(TWO_THOUSAND, countBanknotesWithNominal2000),
                FIVE_THOUSAND, new MoneyCell(FIVE_THOUSAND, countBanknotesWithNominal5000));
    }

    //5800
    public synchronized boolean withDrawMoney(long amount) {
        if (getMoneyAmountAvailable() <= amount) {
            throw new NotEnoughMoneyException("В банкомате недостаточно средств.");
        }

        MoneyCell cellWith5000 = moneyCells.get(FIVE_THOUSAND);
        if (amount >= FIVE_THOUSAND.getValue() && cellWith5000.getBanknotesCount() > 0) {
            long remainder = amount % FIVE_THOUSAND.getValue();
            long resultOfDivision = amount / FIVE_THOUSAND.getValue();
            if (remainder == 0 && cellWith5000.getBanknotesCount() >= resultOfDivision) {
                cellWith5000.setBanknotesCount(cellWith5000.getBanknotesCount() - resultOfDivision);
                return true;
            }
            if (remainder != 0 || cellWith5000.getBanknotesCount() < resultOfDivision) {
                amount = amount - FIVE_THOUSAND.getValue() * cellWith5000.getBanknotesCount();
                cellWith5000.setBanknotesCount(0);
            }
        }

        MoneyCell cellWith2000 = moneyCells.get(TWO_THOUSAND);
        if (amount >= TWO_THOUSAND.getValue() && cellWith2000.getBanknotesCount() > 0) {
            long remainder = amount % TWO_THOUSAND.getValue();
            long resultOfDivision = amount / TWO_THOUSAND.getValue();
            if (remainder == 0 && cellWith2000.getBanknotesCount() >= resultOfDivision) {
                cellWith2000.setBanknotesCount(cellWith2000.getBanknotesCount() - resultOfDivision);
                return true;
            }
            if (remainder != 0 || cellWith2000.getBanknotesCount() < resultOfDivision) {
                amount = amount - TWO_THOUSAND.getValue() * cellWith2000.getBanknotesCount();
                cellWith2000.setBanknotesCount(0);
            }
        }

        MoneyCell cellWith1000 = moneyCells.get(THOUSAND);
        if (amount >= THOUSAND.getValue() && cellWith1000.getBanknotesCount() > 0) {
            long remainder = amount % THOUSAND.getValue();
            long resultOfDivision = amount / THOUSAND.getValue();
            if (remainder == 0 && cellWith1000.getBanknotesCount() >= resultOfDivision) {
                cellWith1000.setBanknotesCount(cellWith1000.getBanknotesCount() - resultOfDivision);
                return true;
            }
            if (remainder != 0 || cellWith1000.getBanknotesCount() < resultOfDivision) {
                amount = amount - THOUSAND.getValue() * cellWith1000.getBanknotesCount();
                cellWith1000.setBanknotesCount(0);
            }
        }

        MoneyCell cellWith500 = moneyCells.get(FIVE_HUNDRED);
        if (amount >= FIVE_HUNDRED.getValue() && cellWith500.getBanknotesCount() > 0) {
            long remainder = amount % FIVE_HUNDRED.getValue();
            long resultOfDivision = amount / FIVE_HUNDRED.getValue();
            if (remainder == 0 && cellWith500.getBanknotesCount() >= resultOfDivision) {
                cellWith500.setBanknotesCount(cellWith500.getBanknotesCount() - resultOfDivision);
                return true;
            }
            if (remainder != 0 || cellWith500.getBanknotesCount() < resultOfDivision) {
                amount = amount - FIVE_HUNDRED.getValue() * cellWith500.getBanknotesCount();
                cellWith500.setBanknotesCount(0);
            }
        }

        MoneyCell cellWith100 = moneyCells.get(HUNDRED);
        if (amount >= HUNDRED.getValue() && cellWith100.getBanknotesCount() > 0) {
            long remainder = amount % HUNDRED.getValue();
            long resultOfDivision = amount / HUNDRED.getValue();
            if (remainder == 0 && cellWith100.getBanknotesCount() >= resultOfDivision) {
                cellWith100.setBanknotesCount(cellWith100.getBanknotesCount() - resultOfDivision);
                return true;
            }
            if (remainder != 0 || cellWith100.getBanknotesCount() < resultOfDivision) {
                amount = amount - HUNDRED.getValue() * cellWith100.getBanknotesCount();
                cellWith100.setBanknotesCount(0);
            }
        }

        MoneyCell cellWith50 = moneyCells.get(FIFTY);
        if (amount >= FIFTY.getValue() && cellWith50.getBanknotesCount() > 0) {
            long remainder = amount % FIFTY.getValue();
            long resultOfDivision = amount / FIFTY.getValue();
            if (remainder == 0 && cellWith50.getBanknotesCount() >= resultOfDivision) {
                cellWith50.setBanknotesCount(cellWith50.getBanknotesCount() - resultOfDivision);
                return true;
            }
            if (remainder != 0 || cellWith50.getBanknotesCount() < resultOfDivision) {
                amount = amount - FIFTY.getValue() * cellWith50.getBanknotesCount();
                cellWith50.setBanknotesCount(0);
            }
        }
        return false;
    }

    public boolean depositMoney(long amount) {
        return false;
    }

    public synchronized long getMoneyAmountAvailable() {
        return moneyCells.values().stream()
                .map(cell -> cell.getNominal().getValue() * cell.getBanknotesCount())
                .reduce(0L, Long::sum);
    }
}

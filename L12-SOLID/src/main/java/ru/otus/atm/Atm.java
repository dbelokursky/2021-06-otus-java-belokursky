package ru.otus.atm;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ru.otus.atm.Nominal.*;


public class Atm {
    private Map<Nominal, MoneyCell> moneyCells;

    public Atm(int countBanknotesWithNominal50, int countBanknotesWithNominal100, int countBanknotesWithNominal500,
               int countBanknotesWithNominal1000, int countBanknotesWithNominal2000, int countBanknotesWithNominal5000) {

        this.moneyCells = Map.of(
                FIFTY, new MoneyCell(FIFTY, countBanknotesWithNominal50),
                HUNDRED, new MoneyCell(HUNDRED, countBanknotesWithNominal100),
                FIVE_HUNDRED, new MoneyCell(FIVE_HUNDRED, countBanknotesWithNominal500),
                THOUSAND, new MoneyCell(THOUSAND, countBanknotesWithNominal1000),
                TWO_THOUSAND, new MoneyCell(TWO_THOUSAND, countBanknotesWithNominal2000),
                FIVE_THOUSAND, new MoneyCell(FIVE_THOUSAND, countBanknotesWithNominal5000));
    }

    public boolean withdrawMoney(long amount) {
        Map<Nominal, MoneyCell> tmpMoneyCells = createCopy(moneyCells);
        if (getMoneyAmountAvailable() <= amount) {
            throw new NotEnoughMoneyException("В банкомате недостаточно средств.");
        }

        MoneyCell cellWith5000 = tmpMoneyCells.get(FIVE_THOUSAND);
        if (amount >= FIVE_THOUSAND.getValue() && cellWith5000.getBanknotesCount() > 0) {
            long remainder = amount % FIVE_THOUSAND.getValue();
            long resultOfDivision = amount / FIVE_THOUSAND.getValue();
            if (remainder == 0 && cellWith5000.getBanknotesCount() >= resultOfDivision) {
                cellWith5000.setBanknotesCount(cellWith5000.getBanknotesCount() - resultOfDivision);
                moneyCells = tmpMoneyCells;
                return true;
            }
            if (remainder != 0 || cellWith5000.getBanknotesCount() < resultOfDivision) {
                amount = amount - FIVE_THOUSAND.getValue() * cellWith5000.getBanknotesCount();
                cellWith5000.setBanknotesCount(0);
            }
        }

        MoneyCell cellWith2000 = tmpMoneyCells.get(TWO_THOUSAND);
        if (amount >= TWO_THOUSAND.getValue() && cellWith2000.getBanknotesCount() > 0) {
            long remainder = amount % TWO_THOUSAND.getValue();
            long resultOfDivision = amount / TWO_THOUSAND.getValue();
            if (remainder == 0 && cellWith2000.getBanknotesCount() >= resultOfDivision) {
                cellWith2000.setBanknotesCount(cellWith2000.getBanknotesCount() - resultOfDivision);
                moneyCells = tmpMoneyCells;
                return true;
            }
            if (remainder != 0 || cellWith2000.getBanknotesCount() < resultOfDivision) {
                amount = amount - TWO_THOUSAND.getValue() * cellWith2000.getBanknotesCount();
                cellWith2000.setBanknotesCount(0);
            }
        }

        MoneyCell cellWith1000 = tmpMoneyCells.get(THOUSAND);
        if (amount >= THOUSAND.getValue() && cellWith1000.getBanknotesCount() > 0) {
            long remainder = amount % THOUSAND.getValue();
            long resultOfDivision = amount / THOUSAND.getValue();
            if (remainder == 0 && cellWith1000.getBanknotesCount() >= resultOfDivision) {
                cellWith1000.setBanknotesCount(cellWith1000.getBanknotesCount() - resultOfDivision);
                moneyCells = tmpMoneyCells;
                return true;
            }
            if (remainder != 0 || cellWith1000.getBanknotesCount() < resultOfDivision) {
                amount = amount - THOUSAND.getValue() * cellWith1000.getBanknotesCount();
                cellWith1000.setBanknotesCount(0);
            }
        }

        MoneyCell cellWith500 = tmpMoneyCells.get(FIVE_HUNDRED);
        if (amount >= FIVE_HUNDRED.getValue() && cellWith500.getBanknotesCount() > 0) {
            long remainder = amount % FIVE_HUNDRED.getValue();
            long resultOfDivision = amount / FIVE_HUNDRED.getValue();
            if (remainder == 0 && cellWith500.getBanknotesCount() >= resultOfDivision) {
                cellWith500.setBanknotesCount(cellWith500.getBanknotesCount() - resultOfDivision);
                moneyCells = tmpMoneyCells;
                return true;
            }
            if (remainder != 0 || cellWith500.getBanknotesCount() < resultOfDivision) {
                amount = amount - FIVE_HUNDRED.getValue() * cellWith500.getBanknotesCount();
                cellWith500.setBanknotesCount(0);
            }
        }

        MoneyCell cellWith100 = tmpMoneyCells.get(HUNDRED);
        if (amount >= HUNDRED.getValue() && cellWith100.getBanknotesCount() > 0) {
            long remainder = amount % HUNDRED.getValue();
            long resultOfDivision = amount / HUNDRED.getValue();
            if (remainder == 0 && cellWith100.getBanknotesCount() >= resultOfDivision) {
                cellWith100.setBanknotesCount(cellWith100.getBanknotesCount() - resultOfDivision);
                moneyCells = tmpMoneyCells;
                return true;
            }
            if (remainder != 0 || cellWith100.getBanknotesCount() < resultOfDivision) {
                amount = amount - HUNDRED.getValue() * cellWith100.getBanknotesCount();
                cellWith100.setBanknotesCount(0);
            }
        }

        MoneyCell cellWith50 = tmpMoneyCells.get(FIFTY);
        if (amount >= FIFTY.getValue() && cellWith50.getBanknotesCount() > 0) {
            long remainder = amount % FIFTY.getValue();
            long resultOfDivision = amount / FIFTY.getValue();
            if (remainder == 0 && cellWith50.getBanknotesCount() >= resultOfDivision) {
                cellWith50.setBanknotesCount(cellWith50.getBanknotesCount() - resultOfDivision);
                moneyCells = tmpMoneyCells;
                return true;
            }
            if (remainder != 0 || cellWith50.getBanknotesCount() < resultOfDivision) {
                throw new NotEnoughMoneyException("В банкомате недостаточно средств.");
            }
        }
        return false;
    }

    public boolean depositMoney(List<CashUnit> cashUnits) {
        Map<Nominal, MoneyCell> tmpMoneyCells = createCopy(moneyCells);
        for (CashUnit cashUnit : cashUnits) {
            MoneyCell moneyCell = tmpMoneyCells.get(cashUnit.getNominal());
            moneyCell.setBanknotesCount(moneyCell.getBanknotesCount() + cashUnit.getBanknotesCount());
        }
        moneyCells = tmpMoneyCells;
        return true;
    }

    public long getMoneyAmountAvailable() {
        return moneyCells.values().stream()
                .map(cell -> cell.getNominal().getValue() * cell.getBanknotesCount())
                .reduce(0L, Long::sum);
    }

    private Map<Nominal, MoneyCell> createCopy(Map<Nominal, MoneyCell> original) {
        return original.entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, c -> new MoneyCell(c.getKey(), c.getValue().getBanknotesCount())));
    }
}

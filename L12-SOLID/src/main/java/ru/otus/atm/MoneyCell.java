package ru.otus.atm;

public class MoneyCell extends MoneyHolder {

    public MoneyCell(Nominal nominal, long banknotesCount) {
        super(nominal, banknotesCount);
    }

    public MoneyCell(MoneyCell moneyCell) {
        super(moneyCell.getNominal(), moneyCell.getBanknotesCount());
    }
}

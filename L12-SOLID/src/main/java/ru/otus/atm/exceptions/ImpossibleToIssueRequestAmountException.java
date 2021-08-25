package ru.otus.atm.exceptions;

public class ImpossibleToIssueRequestAmountException extends RuntimeException {

    public ImpossibleToIssueRequestAmountException(String message) {
        super(message);
    }
}

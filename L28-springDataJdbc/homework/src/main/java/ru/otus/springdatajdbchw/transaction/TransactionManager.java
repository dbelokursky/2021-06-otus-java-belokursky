package ru.otus.springdatajdbchw.transaction;

public interface TransactionManager {

    <T> T doInTransaction(TransactionAction<T> action);
}

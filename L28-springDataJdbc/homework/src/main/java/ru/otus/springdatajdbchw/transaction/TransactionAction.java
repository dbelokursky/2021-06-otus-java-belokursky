package ru.otus.springdatajdbchw.transaction;

import java.util.function.Supplier;

public interface TransactionAction<T> extends Supplier<T> {
}

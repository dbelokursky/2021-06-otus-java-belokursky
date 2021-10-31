package ru.otus.springdatajdbchw.transaction;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class SpringBootTransactionManager implements TransactionManager {

    @Transactional
    @Override
    public <T> T doInTransaction(TransactionAction<T> action) {
        return action.get();
    }
}

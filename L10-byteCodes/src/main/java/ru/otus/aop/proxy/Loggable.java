package ru.otus.aop.proxy;

public interface Loggable {
    void doSomething(int i);

    void doSomething(int i, int j);

    void doSomething(int i, int j, String k);
}

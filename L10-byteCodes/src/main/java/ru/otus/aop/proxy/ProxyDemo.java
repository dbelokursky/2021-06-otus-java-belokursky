package ru.otus.aop.proxy;

public class ProxyDemo {
    public static void main(String[] args) {
        Loggable loggable = LoggableFactory.getInstance(Loggable.class);
        loggable.doSomething(1);
        loggable.doSomething(3, 4);
        loggable.doSomething(5, 6, "7");
    }
}

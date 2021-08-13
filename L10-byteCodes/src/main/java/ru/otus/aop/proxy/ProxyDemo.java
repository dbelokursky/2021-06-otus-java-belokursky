package ru.otus.aop.proxy;

public class ProxyDemo {
    public static void main(String[] args) {
        Loggable loggable = new LoggableFactory().getInstance(Loggable.class, TargetClass.class);
        loggable.doSomething(1);
        loggable.doSomething(3, 4);
        loggable.doSomething(5, 6, "7");
    }
}

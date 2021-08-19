package ru.otus.aop.proxy;

public class ProxyDemo {
    public static void main(String[] args) {
        Loggable loggable = new LoggableFactory<Loggable>().getInstance(new TargetClass());
        loggable.doSomething(1);
        loggable.doSomething(3, 4);
        loggable.doSomething(5, 6, "7");

        Loggable2 loggable2 = new LoggableFactory<Loggable2>().getInstance(new TargetClass2());
        loggable2.doSomething(11);
        loggable2.doSomething(12, 13);
        loggable2.doSomething(14, 15, "16");
    }
}

package ru.otus.aop.proxy;

public class TargetClass implements Loggable {

    @Log
    @Override
    public void doSomething(int i) {
        System.out.println("From method with one param");
    }

    @Log
    @Override
    public void doSomething(int i, int j) {
        System.out.println("From method with two params");
    }

    @Log
    @Override
    public void doSomething(int i, int j, String k) {
        System.out.println("From method with three params");
    }
}

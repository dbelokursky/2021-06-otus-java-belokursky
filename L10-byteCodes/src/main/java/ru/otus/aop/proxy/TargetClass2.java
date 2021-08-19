package ru.otus.aop.proxy;

public class TargetClass2 implements Loggable2 {

    @Log
    @Override
    public void doSomething(int i) {
        System.out.println("From target 2 method with one param");
    }

    @Log
    @Override
    public void doSomething(int i, int j) {
        System.out.println("From target2 method with two params");
    }

    @Log
    @Override
    public void doSomething(int i, int j, String k) {
        System.out.println("From target method with three params");
    }
}

package ru.otus.aop.proxy;

import lombok.SneakyThrows;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class LoggableFactory<T> {

    @SuppressWarnings("unchecked")
    @SneakyThrows
    public T getInstance(T target) {
        InvocationHandler handler = new LogInvocationHandler<>(target);
        return (T) Proxy.newProxyInstance(LoggableFactory.class.getClassLoader(), target.getClass().getInterfaces(), handler);
    }
}

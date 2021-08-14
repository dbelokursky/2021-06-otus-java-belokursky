package ru.otus.aop.proxy;

import lombok.SneakyThrows;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class LoggableFactory<T extends Loggable> {

    @SneakyThrows
    public T getInstance(Class<T> proxy, T target) {
        InvocationHandler handler = new LogInvocationHandler(target);
        return (T) Proxy.newProxyInstance(LoggableFactory.class.getClassLoader(), new Class<?>[]{proxy}, handler);
    }
}

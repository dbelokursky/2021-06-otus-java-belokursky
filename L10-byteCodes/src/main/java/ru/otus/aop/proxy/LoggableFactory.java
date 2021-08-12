package ru.otus.aop.proxy;

import lombok.SneakyThrows;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class LoggableFactory {

    @SneakyThrows
    public static Loggable getInstance(Class<?> clazz) {
        InvocationHandler handler = new LogInvocationHandler(new TargetClass());
        return (Loggable) Proxy.newProxyInstance(LoggableFactory.class.getClassLoader(), new Class<?>[]{clazz}, handler);
    }
}

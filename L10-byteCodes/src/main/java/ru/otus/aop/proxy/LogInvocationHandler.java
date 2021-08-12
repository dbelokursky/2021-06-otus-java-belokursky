package ru.otus.aop.proxy;

import lombok.RequiredArgsConstructor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class LogInvocationHandler implements InvocationHandler {

    private final Loggable loggable;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        printMethodAdditionalInfo(method, args);
        return method.invoke(loggable, args);
    }

    private void printMethodAdditionalInfo(Method method, Object[] args) {
        StringBuilder info = new StringBuilder("Executed method: ");
        info.append(method.getName());
        info.append(", ");
        info.append("param(s): ");
        info.append(Arrays.stream(args).map(String::valueOf).collect(Collectors.joining(", ")));
        System.out.println(info);
    }
}

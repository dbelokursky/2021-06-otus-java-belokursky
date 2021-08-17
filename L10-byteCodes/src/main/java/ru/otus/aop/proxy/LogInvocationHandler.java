package ru.otus.aop.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class LogInvocationHandler<T> implements InvocationHandler {

    private final T loggable;

    private final Set<String> loggableMethods;

    public LogInvocationHandler(T loggable) {
        this.loggable = loggable;
        this.loggableMethods = AnnotationProcessor.getLoggableMethods(loggable.getClass());
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (loggableMethods.contains(Util.getMethodNameWithParams(method))) {
            printMethodAdditionalInfo(method, args);
        }
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

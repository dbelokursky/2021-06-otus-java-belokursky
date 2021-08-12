package ru.otus.aop.proxy;

import lombok.experimental.UtilityClass;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

@UtilityClass
public class Util {

    public static String getMethodNameWithParams(Method method) {
        String methodName = method.getName();
        String params = Arrays.stream(method.getParameterTypes()).map(Class::getName).collect(Collectors.joining(", "));
        return String.format("%s(%s)", methodName, params);
    }
}

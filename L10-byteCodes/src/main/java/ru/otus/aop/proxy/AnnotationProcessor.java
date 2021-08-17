package ru.otus.aop.proxy;


import lombok.experimental.UtilityClass;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

@UtilityClass
public class AnnotationProcessor {

    public static Set<String> getLoggableMethods(Class<?> clazz) {
        var loggableMethods = new HashSet<String>();
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Log.class)) {
                loggableMethods.add(Util.getMethodNameWithParams(method));
            }
        }
        return loggableMethods;
    }
}

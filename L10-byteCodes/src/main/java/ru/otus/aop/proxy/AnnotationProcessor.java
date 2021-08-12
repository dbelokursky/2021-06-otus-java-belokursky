package ru.otus.aop.proxy;


import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class AnnotationProcessor {

    public static Set<String> getLoggableMethods(Class<?> clazz) {
        if (!Loggable.class.isAssignableFrom(clazz)) {
            return Collections.emptySet();
        }

        var loggableMethods = new HashSet<String>();
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Log.class)) {
                loggableMethods.add(Util.getMethodNameWithParams(method));
            }
        }
        return loggableMethods;
    }

}

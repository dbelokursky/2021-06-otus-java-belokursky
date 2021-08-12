package ru.otus.aop.proxy;


import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
public class AnnotationProcessor {

    public List<Method> getLoggableMethods(String className) {
        var loggableMethods = new ArrayList<Method>();
        try {
            Class<?> clazz = Class.forName(className);

            if (!clazz.isAssignableFrom(Loggable.class)) {
                return Collections.emptyList();
            }

            for (Method method : clazz.getDeclaredMethods()) {
                if (method.isAnnotationPresent(Log.class)) {
                    loggableMethods.add(method);
                }
            }
            return loggableMethods;

        } catch (ClassNotFoundException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}

package ru.otus.appcontainer;

import java.lang.reflect.Method;

public class ComponentInfo {
    private final int creationOrder;
    private final Class<?> clazz;
    private final Method method;

    public ComponentInfo(int creationOrder, Class<?> clazz, Method method) {
        this.creationOrder = creationOrder;
        this.clazz = clazz;
        this.method = method;
    }

    public int getCreationOrder() {
        return creationOrder;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public Method getMethod() {
        return method;
    }

}

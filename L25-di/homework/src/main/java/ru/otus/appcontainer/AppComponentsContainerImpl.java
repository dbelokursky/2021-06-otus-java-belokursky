package ru.otus.appcontainer;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import ru.otus.appcontainer.api.AppComponent;
import ru.otus.appcontainer.api.AppComponentsContainer;
import ru.otus.appcontainer.api.AppComponentsContainerConfig;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

@Slf4j
public class AppComponentsContainerImpl implements AppComponentsContainer {

    private final Map<String, Object> appComponentsByName = new HashMap<>();
    private final Map<String, Object> appComponentsByClassName = new HashMap<>();
    private final Map<String, Object> appComponentsByClassImplName = new HashMap<>();

    public AppComponentsContainerImpl(Class<?> initialConfigClass) {
        processConfig(initialConfigClass);
    }

    private void processConfig(Class<?> configClass) {
        checkConfigClass(configClass);
        parseConfig(configClass);
    }

    @SneakyThrows
    private void parseConfig(Class<?> configClass) {
        Object config = configClass.getDeclaredConstructor().newInstance();
        Arrays.stream(configClass.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(AppComponent.class))
                .sorted(Comparator.comparingInt(method -> method.getAnnotation(AppComponent.class).order()))
                .forEach(method -> {
                    method.setAccessible(true);
                    Class<?>[] parameters = method.getParameterTypes();
                    List<Object> args = new ArrayList<>();

                    Arrays.stream(parameters).forEach(param -> {
                        Object arg = appComponentsByClassName.get(param.getSimpleName());
                        args.add(arg);
                    });

                    Object component;
                    try {
                        component = method.invoke(config, args.toArray());
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        log.error(e.getMessage(), e);
                        throw new RuntimeException(e);
                    }
                    appComponentsByName.put(method.getName(), component);
                    appComponentsByClassName.put(method.getReturnType().getSimpleName(), component);
                    appComponentsByClassImplName.put(component.getClass().getSimpleName(), component);
                });
    }

    private void checkConfigClass(Class<?> configClass) {
        if (!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
            throw new IllegalArgumentException(String.format("Given class is not config %s", configClass.getName()));
        }
    }

    @Override
    public <C> C getAppComponent(Class<C> componentClass) {
        C component = (C) appComponentsByClassName.get(componentClass.getSimpleName());
        return component != null ? component : (C) appComponentsByClassImplName.get(componentClass.getSimpleName());
    }

    @Override
    public <C> C getAppComponent(String componentName) {
        return (C) appComponentsByName.get(componentName);
    }
}

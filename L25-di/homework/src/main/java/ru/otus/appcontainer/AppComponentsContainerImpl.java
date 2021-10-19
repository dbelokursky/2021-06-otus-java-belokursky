package ru.otus.appcontainer;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import ru.otus.appcontainer.api.AppComponent;
import ru.otus.appcontainer.api.AppComponentsContainer;
import ru.otus.appcontainer.api.AppComponentsContainerConfig;

import java.lang.reflect.Method;
import java.util.*;

@Slf4j
public class AppComponentsContainerImpl implements AppComponentsContainer {

    private final Map<String, Object> appComponentsByName = new HashMap<>();
    private final Map<String, Object> appComponentsByClassName = new HashMap<>();
    private final Map<String, Object> appComponentsByClassImplName = new HashMap<>();
    private final Map<Integer, List<ComponentInfo>> appComponentsOrdered = new TreeMap<>(Comparator.naturalOrder());

    public AppComponentsContainerImpl(Class<?> initialConfigClass) {
        processConfig(initialConfigClass);
    }

    private void processConfig(Class<?> configClass) {
        checkConfigClass(configClass);
        parseConfig(configClass);
        createComponents(configClass);
    }

    private void parseConfig(Class<?> configClass) {
        for (Method method : configClass.getDeclaredMethods()) {
            if (method.isAnnotationPresent(AppComponent.class)) {
                Class<?> clazz = method.getReturnType();
                AppComponent appComponent = method.getAnnotation(AppComponent.class);
                int order = appComponent.order();
                method.setAccessible(true);
                ComponentInfo component = new ComponentInfo(order, clazz, method);
                if (appComponentsOrdered.get(order) != null) {
                    appComponentsOrdered.get(order).add(component);
                } else {
                    List<ComponentInfo> components = new ArrayList<>();
                    components.add(component);
                    appComponentsOrdered.put(order, components);
                }
            }
        }
    }

    @SneakyThrows
    private void createComponents(Class<?> initialConfigClass) {
        Object config = initialConfigClass.getDeclaredConstructor().newInstance();
        for (List<ComponentInfo> components : appComponentsOrdered.values()) {
            Object component;
            for (ComponentInfo comp : components) {
                Method method = comp.getMethod();
                if (method.getParameterCount() == 0) {
                    component = method.invoke(config);
                } else {
                    Class<?>[] parameters = method.getParameterTypes();
                    List<Object> args = new ArrayList<>();
                    for (int i = 0; i < parameters.length; i++) {
                        Class<?> param = parameters[i];
                        Object arg = appComponentsByClassName.get(param.getSimpleName());
                        args.add(arg);
                    }
                    component = method.invoke(config, args.toArray());
                }
                appComponentsByName.put(comp.getMethod().getName(), component);
                appComponentsByClassName.put(comp.getClazz().getSimpleName(), component);
                appComponentsByClassImplName.put(component.getClass().getSimpleName(), component);
            }
        }
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

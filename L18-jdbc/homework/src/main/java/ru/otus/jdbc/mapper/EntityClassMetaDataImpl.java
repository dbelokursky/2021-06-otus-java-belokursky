package ru.otus.jdbc.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.annotation.Id;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T> {

    private final Logger log = LoggerFactory.getLogger(EntityClassMetaDataImpl.class);

    private String name;

    private Constructor<T> constructor;

    private Field id;

    private Map<String, Field> allFields = new HashMap();

    private List<Field> fieldsWithoutId = new ArrayList<>();

    private List<Method> getters = new ArrayList<>();

    private List<Method> setters = new ArrayList<>();

    private Class<T> clazz;

    public EntityClassMetaDataImpl(Class<T> clazz) {
        this.clazz = clazz;
        name = clazz.getSimpleName();

        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            allFields.put(field.getName(), field);

            if (field.isAnnotationPresent(Id.class)) {
                id = field;
            } else {
                fieldsWithoutId.add(field);
            }
        }

        for (Method method : clazz.getDeclaredMethods()) {
            method.setAccessible(true);
            if (method.getName().startsWith("get")) {
                getters.add(method);
            }
            if (method.getName().startsWith("set")) {
                setters.add(method);
            }
        }

        try {
            constructor = clazz.getDeclaredConstructor();
        } catch (NoSuchMethodException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getName() {
        return clazz.getSimpleName();
    }

    @Override
    public Constructor<T> getConstructor() {
        return constructor;
    }

    @Override
    public Field getIdField() {
        return id;
    }

    @Override
    public Map<String, Field> getAllFields() {
        return allFields;
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        return fieldsWithoutId;
    }

    @Override
    public List<Method> getAllGetters() {
        return getters;
    }

    @Override
    public List<Method> getAllSetters() {
        return setters;
    }
}

package ru.otus.jdbc.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.repository.DataTemplate;
import ru.otus.core.repository.DataTemplateException;
import ru.otus.core.repository.executor.DbExecutor;
import ru.otus.exception.EntityCreateException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Сохратяет объект в базу, читает объект из базы
 */
public class DataTemplateJdbc<T> implements DataTemplate<T> {

    private final DbExecutor dbExecutor;
    private final EntitySQLMetaData<T> entitySQLMetaData;
    private final Logger log = LoggerFactory.getLogger(DataTemplateJdbc.class);

    public DataTemplateJdbc(DbExecutor dbExecutor, EntitySQLMetaData<T> entitySQLMetaData) {
        this.dbExecutor = dbExecutor;
        this.entitySQLMetaData = entitySQLMetaData;
    }

    @Override
    public Optional<T> findById(Connection connection, long id) {
        return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectByIdSql(), List.of(id), rs -> {
            try {
                if (rs.next()) {
                    return createEntity(rs);
                }
            } catch (SQLException e) {
                log.error(e.getMessage(), e);
                throw new DataTemplateException(e);
            }
            return null;
        });
    }

    private T createEntity(ResultSet rs) {
        EntityClassMetaData<T> entityClassMetaData = entitySQLMetaData.getEntityClassMetaData();
        try {
            Constructor<T> constructor = entityClassMetaData.getConstructor();
            T entity = constructor.newInstance();
            Map<String, Object> fieldsValues = getParamsFromResultSet(rs, entityClassMetaData);
            for (Field field : entityClassMetaData.getAllFields().values()) {
                Object value = fieldsValues.get(field.getName());
                if (value != null) {
                    field.set(entity, value);
                }
            }

            return entity;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            log.error(e.getMessage(), e);
            throw new EntityCreateException(String.format("Произошла ошибка при создании инстанса %s", entityClassMetaData.getName()), e);
        }
    }

    private Map<String, Object> getParamsFromResultSet(ResultSet rs, EntityClassMetaData<T> entityClassMetaData) {
        Map<String, Object> params = new HashMap<>();
        for (Field field : entityClassMetaData.getAllFields().values()) {
            try {
                params.put(field.getName(), rs.getObject(field.getName()));
            } catch (SQLException e) {
                log.error(e.getMessage(), e);
                throw new RuntimeException(e);
            }
        }
        return params;
    }

    @Override
    public List<T> findAll(Connection connection) {
        EntityClassMetaData<T> entityClassMetaData = entitySQLMetaData.getEntityClassMetaData();
        return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectAllSql(), List.of(entityClassMetaData.getName()), rs -> {
            try {
                ArrayList<T> clients = new ArrayList<>();
                while (rs.next()) {
                    clients.add(createEntity(rs));
                }
                return clients;
            } catch (SQLException e) {
                log.error(e.getMessage(), e);
                throw new DataTemplateException(e);
            }
        }).orElseThrow(() -> new RuntimeException("Unexpected error"));
    }

    @Override
    public long insert(Connection connection, T object) {
        return dbExecutor.executeStatement(connection, entitySQLMetaData.getInsertSql(), getParamsFromInstance(object));
    }

    private List<Object> getParamsFromInstance(T object) {
        EntityClassMetaData<T> entityClassMetaData = entitySQLMetaData.getEntityClassMetaData();
        List<Object> params = new ArrayList<>();
        for (Method method : entityClassMetaData.getAllGetters()) {
            try {
                Object param = method.invoke(object);
                if (param != null) {
                    params.add(method.invoke(object));
                }

            } catch (IllegalAccessException | InvocationTargetException e) {
                log.error(e.getMessage(), e);
                throw new RuntimeException(e);
            }
        }
        return params;
    }

    @Override
    public void update(Connection connection, T object) {
        dbExecutor.executeStatement(connection, entitySQLMetaData.getUpdateSql(), getParamsFromInstance(object));
    }
}

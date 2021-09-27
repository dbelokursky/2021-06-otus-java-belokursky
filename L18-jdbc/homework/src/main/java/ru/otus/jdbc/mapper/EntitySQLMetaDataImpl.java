package ru.otus.jdbc.mapper;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.stream.Collectors;

public class EntitySQLMetaDataImpl<T> implements EntitySQLMetaData {

    private static final String SELECT_ALL_TEMPLATE = "select * from %s";
    private static final String SELECT_BY_ID_TEMPLATE = "select * from %s where %s = ?";
    private static final String INSERT_TEMPLATE = "insert into";
    private static final String UPDATE_TEMPLATE = "update";

    private final EntityClassMetaData<T> metaData;

    public EntitySQLMetaDataImpl(EntityClassMetaData<T> metaData) {
        this.metaData = metaData;
    }

    @Override
    public String getSelectAllSql() {
        return String.format(SELECT_ALL_TEMPLATE, metaData.getName());
    }

    @Override
    public String getSelectByIdSql() {
        return String.format(SELECT_BY_ID_TEMPLATE, metaData.getName(), metaData.getIdField().getName());
    }

    @Override
    public String getInsertSql() {
        StringBuilder stringBuilder = new StringBuilder(INSERT_TEMPLATE);
        stringBuilder.append(" ");
        stringBuilder.append(metaData.getName());
        stringBuilder.append("(");
        stringBuilder.append(metaData.getFieldsWithoutId().stream().map(Field::getName).collect(Collectors.joining(", ")));
        stringBuilder.append(")");
        stringBuilder.append(" ");
        stringBuilder.append("values (");
        stringBuilder.append(String.join(", ", Collections.nCopies(metaData.getFieldsWithoutId().size(), "?")));
        stringBuilder.append(")");

        return stringBuilder.toString();
    }

    @Override
    public String getUpdateSql() {
        StringBuilder stringBuilder = new StringBuilder(UPDATE_TEMPLATE);
        stringBuilder.append(" ");
        stringBuilder.append(metaData.getName());
        stringBuilder.append("set ");
        String fields = metaData.getFieldsWithoutId().stream()
                .map(f -> String.format(",%s = ?", f.getName()))
                .collect(Collectors.joining(" "))
                .replaceFirst(",", "");
        stringBuilder.append(fields);
        stringBuilder.append(" where ");
        stringBuilder.append(metaData.getIdField().getName());
        stringBuilder.append("= ?");

        return stringBuilder.toString();
    }

    @Override
    public EntityClassMetaData<T> getEntityClassMetaData() {
        return metaData;
    }
}

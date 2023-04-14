package com.example.dbcio.utils;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.query.Update;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author zhang
 * @date 2023/4/12
 * @description
 */
public class EntityUtils {
    public static <T> Map<String, Object> getNonNullProperties(T entity) {
        BeanWrapper src = new BeanWrapperImpl(entity);
        java.beans.PropertyDescriptor[] propertyDescriptors = src.getPropertyDescriptors();
        Map<String, Object> nonNullProps = new HashMap<>();
        for (java.beans.PropertyDescriptor propertyDescriptor : propertyDescriptors) {
            String propertyName = propertyDescriptor.getName();
            Object propertyValue = src.getPropertyValue(propertyName);

            if (propertyValue != null) {
                Field field;
                try {
                    field = entity.getClass().getDeclaredField(propertyName);
                } catch (NoSuchFieldException e) {
                    // Ignore fields not found in the class
                    continue;
                }

                Column column = field.getAnnotation(Column.class);
                if (column != null) {
                    nonNullProps.put(column.value(), propertyValue);
                }
            }
        }
        return nonNullProps;
    }

    public static <T> Update createUpdateFromNonNullProperties(Map<String, Object> nonNullProperties) {
        if (nonNullProperties.isEmpty()) {
            return null;
        }

        Iterator<Map.Entry<String, Object>> iterator = nonNullProperties.entrySet().iterator();
        Map.Entry<String, Object> firstEntry = iterator.next();
        Update update = Update.update(firstEntry.getKey(), firstEntry.getValue());

        while (iterator.hasNext()) {
            Map.Entry<String, Object> entry = iterator.next();
            update = update.set(entry.getKey(), entry.getValue());
        }

        return update;
    }

    public static <T> String getTableName(Class<T> entityClass) {
        Table table = entityClass.getAnnotation(Table.class);
        return table != null ? table.value() : "";
    }
}

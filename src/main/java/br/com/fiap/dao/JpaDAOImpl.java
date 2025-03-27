package br.com.fiap.dao;

import br.com.fiap.annotation.Column;
import br.com.fiap.annotation.Table;
import jakarta.persistence.Id;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class JpaDAOImpl implements IJpaDAO {

    public void create(Object obj) {
        Table annotation = obj.getClass().getAnnotation(Table.class);
        StringBuilder sql = new StringBuilder("INSERT INTO " + annotation.name() + " (");

        ArrayList<String> columnNames = new ArrayList<>();
        ArrayList<String> bindString = new ArrayList<>();

        ArrayList<Object> values = new ArrayList<>();

        int index = 1;
        Field[] attributes = obj.getClass().getDeclaredFields();
        for (Field f : attributes) {
            if (!f.isAnnotationPresent(Column.class)) continue;
            Column annotationCol = f.getAnnotation(Column.class);
            columnNames.add(annotationCol.name());
            bindString.add(":" + index);
            index++;

            f.setAccessible(true);
            try {
                Object value = f.get(obj);
                values.add(value);
            } catch (IllegalAccessException e) {
                System.err.println("[AUTO_SQL] Could not get the value from the attribute");
            }
        }

        sql.append(String.join(", ", columnNames));
        sql.append(") VALUES (");
        sql.append(String.join(", ", bindString));
        sql.append(");");

        System.out.println("Insert SQL: " + sql);
    }

    public void readALl(Object obj) {
        Table annotation = obj.getClass().getAnnotation(Table.class);
        String sql = "SELECT * FROM " + annotation.name() + ";";
        System.out.println("Select All: " + sql);
    }

    public void read(Object obj) {
        Table annotation = obj.getClass().getAnnotation(Table.class);
        StringBuilder sql = new StringBuilder("SELECT * FROM " + annotation.name() + " WHERE ");

        Object value;
        Field[] attributes = obj.getClass().getDeclaredFields();
        for (Field f : attributes) {
            if (!f.isAnnotationPresent(Id.class)) continue;
            Column annotationCol = f.getAnnotation(Column.class);
            sql.append(annotationCol.name());

            f.setAccessible(true);
            try {
                value = f.get(obj);
            } catch (IllegalAccessException e) {
                System.err.println("[AUTO_SQL] Could not get the value from the attribute");
            }
        }

        sql.append(" = :1;");
        System.out.println("Select ID: " + sql);
    }

    public void update(Object obj) {
        Table annotation = obj.getClass().getAnnotation(Table.class);
        StringBuilder sql = new StringBuilder("UPDATE " + annotation.name() + " SET ");

        ArrayList<String> columnNames = new ArrayList<>();
        ArrayList<Object> values = new ArrayList<>();

        int index = 1;
        Field[] attributes = obj.getClass().getDeclaredFields();
        for (Field f : attributes) {
            if (!f.isAnnotationPresent(Column.class)) continue;
            Column annotationCol = f.getAnnotation(Column.class);
            columnNames.add(annotationCol.name() + " = :" + index);
            index++;

            f.setAccessible(true);
            try {
                Object value = f.get(obj);
                values.add(value);
            } catch (IllegalAccessException e) {
                System.err.println("[AUTO_SQL] Could not get the value from the attribute");
            }
        }

        sql.append(String.join(", ", columnNames));
        sql.append(" WHERE ");

        Object value;
        for (Field f : attributes) {
            if (!f.isAnnotationPresent(Id.class)) continue;
            Column annotationCol = f.getAnnotation(Column.class);
            sql.append(annotationCol.name());

            f.setAccessible(true);
            try {
                value = f.get(obj);
            } catch (IllegalAccessException e) {
                System.err.println("[AUTO_SQL] Could not get the value from the attribute");
            }
        }

        sql.append(" = :1;");
        System.out.println("Update SQL: " + sql);
    }

    public void delete(Object obj) {
        Table annotation = obj.getClass().getAnnotation(Table.class);
        StringBuilder sql = new StringBuilder("DELETE FROM " + annotation.name() + " WHERE ");

        Object value;
        Field[] attributes = obj.getClass().getDeclaredFields();
        for (Field f : attributes) {
            if (!f.isAnnotationPresent(Id.class)) continue;
            Column annotationCol = f.getAnnotation(Column.class);
            sql.append(annotationCol.name());

            f.setAccessible(true);
            try {
                value = f.get(obj);
            } catch (IllegalAccessException e) {
                System.err.println("[AUTO_SQL] Could not get the value from the attribute");
            }
        }

        sql.append(" = :1;");
        System.out.println("Delete SQL: " + sql);
    }
}

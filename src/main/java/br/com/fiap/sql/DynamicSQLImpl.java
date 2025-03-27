package br.com.fiap.sql;

import jakarta.persistence.*;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class DynamicSQLImpl implements IDynamicSQL {

    public String createSQLQuery(Object obj) {
        Table annotation = obj.getClass().getAnnotation(Table.class);
        StringBuilder sql = new StringBuilder("INSERT INTO " + annotation.name() + " (");

        ArrayList<String> columnNames = new ArrayList<>();
        ArrayList<String> bindString = new ArrayList<>();

        ArrayList<Object> values = new ArrayList<>();

        Field[] attributes = obj.getClass().getDeclaredFields();
        for (Field f : attributes) {
            if (!f.isAnnotationPresent(Column.class)) continue;
            Column annotationCol = f.getAnnotation(Column.class);
            columnNames.add(annotationCol.name());
            bindString.add("?");

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
        return sql.toString();
    }
    public String readALlSQLQuery(Object obj) {
        Table annotation = obj.getClass().getAnnotation(Table.class);
        String sql = "SELECT * FROM " + annotation.name() + ";";
        System.out.println("Select All: " + sql);
        return sql;
    }
    public String readSQLQuery(Object obj) {
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

        sql.append(" = ?;");
        System.out.println("Select ID: " + sql);
        return sql.toString();
    }
    public String updateSQLQuery(Object obj) {
        Table annotation = obj.getClass().getAnnotation(Table.class);
        StringBuilder sql = new StringBuilder("UPDATE " + annotation.name() + " SET ");

        ArrayList<String> columnNames = new ArrayList<>();
        ArrayList<Object> values = new ArrayList<>();

        Field[] attributes = obj.getClass().getDeclaredFields();
        for (Field f : attributes) {
            if (!f.isAnnotationPresent(Column.class)) continue;
            Column annotationCol = f.getAnnotation(Column.class);
            columnNames.add(annotationCol.name() + " = ?");

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

        sql.append(" = ?;");
        System.out.println("Update SQL: " + sql);
        return sql.toString();
    }
    public String deleteSQLQuery(Object obj) {
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

        sql.append(" = ?;");
        System.out.println("Delete SQL: " + sql);
        return sql.toString();
    }
    public ArrayList<String> getSQLQueries(Object obj) {
        ArrayList<String> queries = new ArrayList<>();
        queries.add(createSQLQuery(obj));
        queries.add(readALlSQLQuery(obj));
        queries.add(readSQLQuery(obj));
        queries.add(updateSQLQuery(obj));
        queries.add(deleteSQLQuery(obj));

        return queries;
    }

}

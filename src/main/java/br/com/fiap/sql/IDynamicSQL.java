package br.com.fiap.sql;

import java.util.ArrayList;

public interface IDynamicSQL {

    String createSQLQuery(Object obj);
    String readALlSQLQuery(Object obj);
    String readSQLQuery(Object obj);
    String updateSQLQuery(Object obj);
    String deleteSQLQuery(Object obj);
    ArrayList<String> getSQLQueries(Object obj);
}

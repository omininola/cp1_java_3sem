package br.com.fiap.dao;

import br.com.fiap.entity.Employee;

import java.util.ArrayList;

public interface IEmployeeDAO {
    void create(Employee employee);
    ArrayList<Employee> readAll();
    Employee read(long id);
    void update(Employee employee);
    void delete(long id);
}

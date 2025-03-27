package br.com.fiap.dao;

import br.com.fiap.entity.Employee;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;

import java.util.ArrayList;

@AllArgsConstructor
public class EmployeeDAOImpl implements IEmployeeDAO {

    EntityManager em;

    public void create(Employee employee) {
        em.persist(employee);
    }
    public ArrayList<Employee> readAll() {
        Query query = em.createQuery("SELECT t FROM " + Employee.class.getName() + " t");

        ArrayList<Employee> employees = new ArrayList<>();
        for (Object obj : query.getResultList()) {
            employees.add((Employee) obj);
        }

        return employees;
    }
    public Employee read(long id) {
        Employee e = em.find(Employee.class, id);
        if (e == null) System.err.println("[ID_NOT_FOUND] Unable to find id: " + id);
        return e;
    }
    public void update(Employee employee) {
        Employee e = read(employee.getId());
        em.merge(employee);
    }
    public void delete(long id) {
        Employee e = read(id);
        em.remove(e);
    }
    public void commit() {
        EntityTransaction t = em.getTransaction();
        try {
            t.begin();
            t.commit();
        } catch (Exception ex) {
            t.rollback();
            System.err.println("[COMMIT] " + ex.getMessage());
        }
    }
}

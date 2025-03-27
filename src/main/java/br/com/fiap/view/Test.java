package br.com.fiap.view;

import br.com.fiap.dao.EmployeeDAOImpl;
import br.com.fiap.sql.DynamicSQLImpl;
import br.com.fiap.entity.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.ArrayList;

public class Test {
    public static void main(String[] args) {
        Employee e = new Employee();
        e.setName("Otavio");
        e.setWorkedHours(10);
        e.setValuePerHour(10);

        DynamicSQLImpl jpaDAO = new DynamicSQLImpl();
        ArrayList<String> sqlQueries = jpaDAO.getSQLQueries(e);

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("CHECKPOINT");
        EntityManager em = emf.createEntityManager();

        EmployeeDAOImpl employeeDAO = new EmployeeDAOImpl(em);

        employeeDAO.create(e);

        employeeDAO.commit();

        System.out.println(employeeDAO.read(e.getId()));

        e.setWorkedHours(20);
        employeeDAO.update(e);

        employeeDAO.commit();

        System.out.println(employeeDAO.read(e.getId()));
        employeeDAO.delete(e.getId());

        employeeDAO.commit();

        System.out.println(employeeDAO.read(e.getId()));
    }
}

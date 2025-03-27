package br.com.fiap.view;

import br.com.fiap.dao.JpaDAOImpl;
import br.com.fiap.entity.Employee;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Test {
    public static void main(String[] args) {
        Employee e = new Employee();
        e.setName("Otavio");
        e.setWorkedHours(10);
        e.setValuePerHour(10);

        JpaDAOImpl jpaDAO = new JpaDAOImpl();
        jpaDAO.create(e);
        jpaDAO.readALl(e);
        jpaDAO.read(e);
        jpaDAO.update(e);
        jpaDAO.delete(e);

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("CHECKPOINT");
        EntityManager em = emf.createEntityManager();

        em.persist(e);
    }
}

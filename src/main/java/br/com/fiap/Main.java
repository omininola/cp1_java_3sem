package br.com.fiap;

import br.com.fiap.entity.Employee;
import br.com.fiap.entity.InternEmployee;
import br.com.fiap.entity.SeniorEmployee;
import br.com.fiap.entity.ManagerEmployee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Main {

    public static void main(String[] args) {

        Employee employee = new Employee();
        employee.setName("João");
        employee.setWorkedHours(160);
        employee.setValuePerHour(25);
        System.out.println("Employee Salary: " + employee.calcSalary());
        System.out.println(employee);

        SeniorEmployee seniorEmployee = new SeniorEmployee();
        seniorEmployee.setName("Maria");
        seniorEmployee.setWorkedHours(200);
        seniorEmployee.setValuePerHour(30);
        System.out.println("Senior Employee Salary: " + seniorEmployee.calcSalary());
        System.out.println(seniorEmployee);

        InternEmployee internEmployee = new InternEmployee();
        internEmployee.setName("Ana");
        internEmployee.setWorkedHours(120);
        internEmployee.setValuePerHour(15);
        internEmployee.setTaxDiscount(10);
        System.out.println("Intern Employee Salary: " + internEmployee.calcSalary());
        System.out.println(internEmployee);

        ManagerEmployee managerEmployee = new ManagerEmployee();
        managerEmployee.setName("Pedro");
        managerEmployee.setWorkedHours(220);
        managerEmployee.setValuePerHour(40);
        managerEmployee.setManagerBonus(500);
        System.out.println("Salário do funcionário gerente: " + managerEmployee.calcSalary());
        System.out.println(managerEmployee);
    }
}

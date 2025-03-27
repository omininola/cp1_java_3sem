package br.com.fiap;

import br.com.fiap.dao.EmployeeDAOImpl;
import br.com.fiap.entity.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);

        StringBuilder menu = new StringBuilder();
        menu.append("[1] - Criar novo funcionário").append("\n");
        menu.append("[2] - Buscar todos os funcionários").append("\n");
        menu.append("[3] - Buscar funcionário pelo ID").append("\n");
        menu.append("[4] - Atualizar funcionário").append("\n");
        menu.append("[5] - Deletar funcionário").append("\n");
        menu.append("[6] - Fechar programa").append("\n");
        menu.append("\n");
        menu.append("Digite a opção desejada: ");

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("CHECKPOINT");
        EntityManager em = emf.createEntityManager();

        EmployeeDAOImpl employeeDAO = new EmployeeDAOImpl(em);

        boolean isRunning = true;
        while (isRunning) {
            System.out.println(menu);
            int scanValue = scan.nextInt();

            switch (scanValue) {
                case 1:
                    System.out.println("Digite o nome do funcionário: ");
                    String nameCreate = scan.next() + scan.nextLine();

                    System.out.println("Digite as horas que o funcionário trabalhou: ");
                    int workedHoursCreate = scan.hasNextInt() ? scan.nextInt() : 0;

                    System.out.println("Digite o valor por hora trabalhada (R$): ");
                    double valuePerHourCreate = scan.hasNextDouble() ? scan.nextDouble() : 0;

                    Employee empCreate = new Employee();
                    empCreate.setName(nameCreate);
                    empCreate.setWorkedHours(workedHoursCreate);
                    empCreate.setValuePerHour(valuePerHourCreate);

                    System.out.println(empCreate);
                    employeeDAO.create(empCreate);
                    employeeDAO.commit();

                    break;

                case 2:
                    System.out.println("Buscando todos os funcionários...");

                    ArrayList<Employee> employees = employeeDAO.readAll();
                    for (Employee empReadAll : employees) {
                        System.out.println(empReadAll);
                    }

                    break;

                case 3:
                    System.out.println("Digite o ID do funcionário que deseja buscar: ");
                    long idRead = scan.nextLong();
                    Employee empRead = employeeDAO.read(idRead);
                    System.out.println(empRead);

                    break;

                case 4:
                    System.out.println("Digite o ID do funcionário que deseja atualizar: ");
                    long idUpdate = scan.nextLong();

                    System.out.println("Digite o novo nome do funcionário: ");
                    String nameUpdate = scan.next() + scan.nextLine();

                    System.out.println("Digite as novas horas que o funcionário trabalhou: ");
                    int workedHoursUpdate = scan.nextInt();

                    System.out.println("Digite o novo valor por hora trabalhada (R$): ");
                    double valuePerHourUpdate = scan.nextDouble();

                    Employee empUpdate = new Employee();
                    empUpdate.setId(idUpdate);
                    empUpdate.setName(nameUpdate);
                    empUpdate.setWorkedHours(workedHoursUpdate);
                    empUpdate.setValuePerHour(valuePerHourUpdate);

                    employeeDAO.update(empUpdate);
                    employeeDAO.commit();

                    break;

                case 5:
                    System.out.println("Digite o ID do funcionário que deseja deletar: ");
                    long idDelete = scan.nextLong();

                    employeeDAO.delete(idDelete);
                    employeeDAO.commit();

                    break;

                case 6:
                    System.out.println("Obrigado por utilizar nossos serviços!");
                    System.out.println("Até logo");

                    isRunning = false;
                    break;

                default:
                    System.out.println("Opção inválida! Por favor, tente novamente!");
            }
        }
    }
}

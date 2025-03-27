package br.com.fiap.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import lombok.*;

@Entity
@Table(name = "TAB_FUNCIONARIO")

// Lombok
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    @Id
    @Column(name = "id_employee")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nm_employee")
    private String name;

    @Column(name = "worked_hours")
    private int workedHours;

    @Column(name = "vl_hour")
    private double valuePerHour;

    public double calcSalary() {
        return workedHours * valuePerHour;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Employee: ").append(name).append("\n");
        if (id != null) sb.append("ID: ").append(id).append("\n");
        sb.append("Worked Hours: ").append(workedHours).append("\n");
        sb.append("Value per Hour: ").append(valuePerHour).append("\n");
        sb.append("Salary: ").append(calcSalary()).append("\n");
        return sb.toString();
    }
}

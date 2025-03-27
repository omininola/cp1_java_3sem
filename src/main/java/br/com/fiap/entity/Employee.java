package br.com.fiap.entity;

import br.com.fiap.annotation.Column;
import br.com.fiap.annotation.Table;
import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.*;

@Entity
@Table(name = "TAB_FUNCIONARIO")

// Lombok
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Employee {

    @Id
    @Column(name = "id_employee")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    @Column(name = "nm_employee")
    private String name;

    @Column(name = "worked_hours")
    private int workedHours;

    @Column(name = "vl_hour")
    private double valuePerHour;

    public double calcSalary() {
        return workedHours * valuePerHour;
    }
}

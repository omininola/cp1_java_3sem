package br.com.fiap.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

// Lombok
@Getter @Setter
public class ManagerEmployee extends Employee {
    private double managerBonus;

    @Override
    public double calcSalary() {
        return super.calcSalary() + managerBonus;
    }
}

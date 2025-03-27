package br.com.fiap.entity;

import lombok.Getter;
import lombok.Setter;

// Lombok
@Getter @Setter
public class ManagerEmployee extends Employee {
    private double managerBonus;

    @Override
    public double calcSalary() {
        return super.calcSalary() + managerBonus;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Manager Employee: ").append(getName()).append("\n");
        sb.append("Worked Hours: ").append(getWorkedHours()).append("\n");
        sb.append("Value per Hour: ").append(getValuePerHour()).append("\n");
        sb.append("Manager Bonus: ").append(managerBonus).append("\n");
        sb.append("Salary: ").append(calcSalary()).append("\n");
        return sb.toString();
    }
}

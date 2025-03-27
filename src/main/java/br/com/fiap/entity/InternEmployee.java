package br.com.fiap.entity;

import lombok.*;

// Lombok
@Getter @Setter
public class InternEmployee extends Employee {
    private double taxDiscount;

    @Override
    public double calcSalary() {
        double salary = super.calcSalary();
        return salary - (salary * (taxDiscount / 100));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Intern Employee: ").append(getName()).append("\n");
        sb.append("Worked Hours: ").append(getWorkedHours()).append("\n");
        sb.append("Value per Hour: ").append(getValuePerHour()).append("\n");
        sb.append("Tax Discount: ").append(taxDiscount).append("%").append("\n");
        sb.append("Salary: ").append(calcSalary()).append("\n");
        return sb.toString();
    }
}
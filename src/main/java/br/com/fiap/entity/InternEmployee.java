package br.com.fiap.entity;

import lombok.*;

// Lombok
@Getter @Setter
@ToString
public class InternEmployee extends Employee {
    private double taxDiscount;

    @Override
    public double calcSalary() {
        double salary = super.calcSalary();
        return salary - (salary * (taxDiscount / 100));
    }
}
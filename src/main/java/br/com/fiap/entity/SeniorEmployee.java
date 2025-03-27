package br.com.fiap.entity;

public class SeniorEmployee extends Employee {
    private static final double BONUS = 100.0; // Valor do bônus a cada 15 horas

    @Override
    public double calcSalary() {
        double salary = super.calcSalary();
        int workedHoursBonus = Math.floorDiv(getWorkedHours(), 15);
        return salary + (workedHoursBonus * BONUS);
    }
}

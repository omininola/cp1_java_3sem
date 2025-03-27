package br.com.fiap.entity;

public class SeniorEmployee extends Employee {
    private static final double BONUS = 100.0; // Valor do bônus a cada 15 horas
    private static final int BONUS_HOURS = 15; // Quantidade de horas para ganhar o bônus

    @Override
    public double calcSalary() {
        double salary = super.calcSalary();
        int workedHoursBonus = Math.floorDiv(getWorkedHours(), BONUS_HOURS);
        return salary + (workedHoursBonus * BONUS);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Senior Employee: ").append(getName()).append("\n");
        sb.append("Worked Hours: ").append(getWorkedHours()).append("\n");
        sb.append("Value per Hour: ").append(getValuePerHour()).append("\n");
        sb.append("Senior Bonus: ").append((Math.floorDiv(getWorkedHours(), BONUS_HOURS)) * BONUS).append("\n");
        sb.append("Salary: ").append(calcSalary()).append("\n");
        return sb.toString();
    }
}

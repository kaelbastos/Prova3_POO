package model;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class Employee {
    private final String cpf;
    private String name;
    private float salary;
    private Map<LocalDateTime, Appointment> schedule = new HashMap<>();

    public Employee(String name, String cpf) {
        this.name = name;
        this.cpf = cpf;
        this.salary = 0;
    }

    public Employee(String name, String cpf, float salary) {
        this.cpf = cpf;
        this.name = name;
        this.salary = salary;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getCpf() {
        return cpf;
    }

    public float getSalary() {
        return salary;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }

    public Map<LocalDateTime, Appointment> getSchedule() {
        return schedule;
    }

    public void setSchedule(Map<LocalDateTime, Appointment> schedule) {
        this.schedule = schedule;
    }
}

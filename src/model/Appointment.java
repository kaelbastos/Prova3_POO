package model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Appointment {
    private final Animal animal;
    private Employee employee;
    private LocalDateTime date;
    private ServiceOption service;
    private float price;

    public Appointment(Animal animal, Employee employee, LocalDateTime date, ServiceOption service, float price) {
        this.animal = animal;
        this.employee = employee;
        this.date = date;
        this.service = service;
        this.price = price;
    }

    public Animal getAnimal() {
        return animal;
    }

    public Employee getEmployee() {
        return employee;
    }

    public String getEmployeeName(){
        return employee.getName();
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getDateValue(){
        return date.toString();
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public ServiceOption getService() {
        return service;
    }

    public void setService(ServiceOption service) {
        this.service = service;
    }

    public String getServiceValue(){
        return service.toString();
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Appointment that = (Appointment) o;
        try{
            return animal.getId() == that.animal.getId() &&
                    employee.getCpf().equals(that.employee.getCpf()) &&
                    date.equals(that.date);
        } catch (NullPointerException e){
            return false;
        }

    }

    @Override
    public int hashCode() {
        return Objects.hash(animal.getId(), employee.getCpf(), date);
    }
}

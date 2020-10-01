package DAO;

import model.Animal;
import model.Appointment;
import model.Employee;
import model.ServiceOption;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CreateDBDAO  {
    public static void main(String[] args) throws SQLException {
        create();
    }

    public static void create() throws SQLException {
        try{
            Connection connection = DriverManager.getConnection("jdbc:sqlite:petshop.db");
        } finally {
            populate();
        }
    }
    public static void populate() throws SQLException {
        List<Employee> employeeList = new ArrayList<>();
        List<Animal> animalList = new ArrayList<>();
        List<Appointment> appointmentList = new ArrayList<>();

        Employee e1 = new Employee("joao", "615166");
        Employee e2 = new Employee("joaquina", "1289830", 100);

        employeeList.add(e1);
        employeeList.add(e2);
        EmployeeSQLIiteDAO employeeSQLIiteDAO = new EmployeeSQLIiteDAO();
        employeeSQLIiteDAO.create(employeeList);

        Animal a1 = new Animal( 161191,"frederico", "carlos", "0800 batata", "", true);
        Animal a2 = new Animal(124197, "etevaldo", "pericles", "0800 cenoura", "muito legal");

        animalList.add(a1);
        animalList.add(a2);
        AnimalSQliteDAO animalSQliteDAO = new AnimalSQliteDAO();
        animalSQliteDAO.create(animalList);

        Appointment ap1 = new Appointment(a1, e1, LocalDateTime.of(2020, 9, 10, 10, 30), ServiceOption.Complete, ServiceOption.getPrice(ServiceOption.Complete));
        Appointment ap2 = new Appointment(a2, e2, LocalDateTime.of(2020, 9, 10, 11, 30), ServiceOption.Shower, 20);
        Appointment ap3 = new Appointment(a1, e2, LocalDateTime.of(2020, 10, 2, 8, 0), ServiceOption.Shower, ServiceOption.getPrice(ServiceOption.Shower));
        Appointment ap4 = new Appointment(a2, e1, LocalDateTime.of(2020, 9, 10, 10, 30), ServiceOption.Grooming, ServiceOption.getPrice(ServiceOption.Grooming));

        appointmentList.add(ap1);
        appointmentList.add(ap2);
        appointmentList.add(ap3);
        appointmentList.add(ap4);
        AppointmentSQLiteDAO appointmentSQLiteDAO = new AppointmentSQLiteDAO();
        appointmentSQLiteDAO.create(appointmentList);
    }
}

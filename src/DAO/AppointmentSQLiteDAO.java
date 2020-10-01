package DAO;

import model.Animal;
import model.Appointment;
import model.Employee;
import model.ServiceOption;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class AppointmentSQLiteDAO implements DAO <Appointment, LocalDateTime> {
    @Override
    public void create(List<Appointment> list) throws SQLException {
        PreparedStatement statement = null;
        try(Connection connection = DriverManager.getConnection("jdbc:sqlite:petshop.db")){
            createTableIfNotExists();
            String sql = "INSERT INTO appointments (date, animalId, employeeCPF, service, price) " +
                    "values (?, ?, ?, ?, ?);";
            statement = connection.prepareStatement(sql);
            for (Appointment appointment:
                    list) {
                statement.setString(1, appointment.getDateValue());
                statement.setInt(2,appointment.getAnimal().getId());
                statement.setString(3, appointment.getEmployee().getCpf());
                statement.setString(4, appointment.getServiceValue());
                statement.setFloat(5, appointment.getPrice());
                statement.execute();
            }
        }
    }

    @Override
    public void create(Appointment appointment) throws SQLException {
        PreparedStatement statement = null;
        try(Connection connection = DriverManager.getConnection("jdbc:sqlite:petshop.db")){
            createTableIfNotExists();
            String sql = "INSERT INTO appointments (date, animalId, employeeCPF, service, price) " +
                    "values (?, ?, ?, ?, ?);";
            statement = connection.prepareStatement(sql);
            statement.setString(1, appointment.getDateValue());
            statement.setInt(2, appointment.getAnimal().getId());
            statement.setString(3, appointment.getEmployee().getCpf());
            statement.setString(4, appointment.getServiceValue());
            statement.setFloat(5, appointment.getPrice());
            statement.execute();
        }
    }

    @Override
    public Map<LocalDateTime, Appointment> importAsMap() throws SQLException {
        List<Appointment> list = importAsList();
        Map<LocalDateTime, Appointment> map = new LinkedHashMap<>();
        if(!list.isEmpty()){
            for (Appointment a:
                 list) {
                map.put(a.getDate(), a);
            }
            return map;
        }
        return null;
    }

    @Override
    public List<Appointment> importAsList() throws SQLException{
        PreparedStatement statement = null;
        try(Connection connection = DriverManager.getConnection("jdbc:sqlite:petshop.db")) {
            createTableIfNotExists();
            List<Appointment> list = new ArrayList<>();
            String sql = "SELECT date, animalId, employeeCPF, service, price FROM appointments ORDER BY date";
            statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                LocalDateTime date = LocalDateTime.parse(resultSet.getString("date"));

                AnimalSQliteDAO animalSQliteDAO = new AnimalSQliteDAO();
                Animal animal = animalSQliteDAO.importFromId(resultSet.getInt("animalId"));

                EmployeeSQLIiteDAO employeeSQLIiteDAO = new EmployeeSQLIiteDAO();
                Employee employee = employeeSQLIiteDAO.importFromCPF(resultSet.getString("employeeCPF"));

                ServiceOption serviceOption = ServiceOption.valueOf(resultSet.getString("service"));

                float price = resultSet.getFloat("price");

                Appointment appointment = new Appointment(animal, employee, date, serviceOption, price);
                list.add(appointment);
            }
            return list;
        }
    }

    @Override
    public void update(Appointment appointment) throws SQLException {
        PreparedStatement statement = null;
        try(Connection connection = DriverManager.getConnection("jdbc:sqlite:petshop.db")){
            createTableIfNotExists();
            String sql = "UPDATE appointments SET animalId = ?, employeeCPF = ?, service = ?, price = ? WHERE date = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, appointment.getAnimal().getId());
            statement.setString(2, appointment.getEmployee().getCpf());
            statement.setString(3, appointment.getServiceValue());
            statement.setFloat(4, appointment.getPrice());
            statement.setString(5, appointment.getDateValue());
            statement.execute();
        }
    }

    @Override
    public void delete(Appointment appointment) throws SQLException {
        PreparedStatement statement = null;
        try(Connection connection = DriverManager.getConnection("jdbc:sqlite:petshop.db")){
            createTableIfNotExists();
            String sql = "DELETE FROM appointments WHERE date = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, appointment.getDateValue());
            statement.execute();
        }
    }

    private void createTableIfNotExists() throws SQLException {
        PreparedStatement statement = null;
        try(Connection connection = DriverManager.getConnection("jdbc:sqlite:petshop.db")){
            String sql = "CREATE TABLE IF NOT EXISTS appointments (\n" +
                    "date text NOT NULL,\n" +
                    "animalId integer REFERENCES animals (id) NOT NULL,\n" +
                    "employeeCPF text REFERENCES employees (CPF) NOT NULL,\n" +
                    "service text NOT NULL,\n" +
                    "price float NOT NULL\n" +
                    ");";
            statement = connection.prepareStatement(sql);
            statement.execute();
        } finally {
            if(statement != null){
                statement.close();
            }
        }
    }

}

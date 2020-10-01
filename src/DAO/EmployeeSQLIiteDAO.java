package DAO;

import model.Employee;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class EmployeeSQLIiteDAO implements DAO <Employee, String> {
    @Override
    public void create(List<Employee> list) throws SQLException {
        PreparedStatement statement = null;
        try(Connection connection = DriverManager.getConnection("jdbc:sqlite:petshop.db")){
            createTableIfNotExists();
            String sql = "INSERT INTO employees (CPF, name , salary) " +
                    "values (?, ?, ?);";
            statement = connection.prepareStatement(sql);
            for (Employee employee:
                    list) {
                statement.setString(1, employee.getCpf());
                statement.setString(2, employee.getName());
                statement.setFloat(3, employee.getSalary());
                statement.execute();
            }
        }
    }

    @Override
    public void create(Employee employee) throws SQLException {
        PreparedStatement statement = null;
        try(Connection connection = DriverManager.getConnection("jdbc:sqlite:petshop.db")){
            createTableIfNotExists();
            String sql = "INSERT INTO emplyees (CPF, name , salary) " +
                    "values (?, ?, ?);";
            statement = connection.prepareStatement(sql);
            statement.setString(1, employee.getCpf());
            statement.setString(2, employee.getName());
            statement.setFloat(3, employee.getSalary());
            statement.execute();
        }
    }

    @Override
    public Map<String, Employee> importAsMap() throws SQLException {
        List<Employee> list = importAsList();
        if(!list.isEmpty()){
            Map<String, Employee> map = new LinkedHashMap<>();
            for (Employee employee:
                 list) {
                map.put(employee.getCpf(), employee);
            }
            return map;
        }
        return null;
    }

    @Override
    public List<Employee> importAsList() throws SQLException {
        PreparedStatement statement = null;
        try(Connection connection = DriverManager.getConnection("jdbc:sqlite:petshop.db")){
            createTableIfNotExists();
            String sql = "SELECT CPF, name , salary FROM employees";
            statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            List<Employee> list = new ArrayList<>();
            while(resultSet.next()) {
                String CPF = resultSet.getString("CPF");
                String name = resultSet.getString("name");
                float salary = resultSet.getFloat("salary");

                Employee employee = new Employee(name, CPF, salary);
                list.add(employee);
            }
            return list;
        }
    }

    public Employee importFromCPF(String CPF) throws SQLException {
        PreparedStatement statement = null;
        try(Connection connection = DriverManager.getConnection("jdbc:sqlite:petshop.db")){
            createTableIfNotExists();
            String sql = "SELECT name , salary FROM employees WHERE CPF = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, CPF);
            ResultSet resultSet = statement.executeQuery();
            String name = resultSet.getString("name");
            float salary = resultSet.getFloat("salary");
            return new Employee(name, CPF, salary);
        }
    }

    @Override
    public void update(Employee employee) throws SQLException {
        PreparedStatement statement = null;
        try(Connection connection = DriverManager.getConnection("jdbc:sqlite:petshop.db")){
            createTableIfNotExists();
            String sql = "UPDATE employees SET name = ?, salary = ? " +
                    "WHERE CPF = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, employee.getName());
            statement.setFloat(2, employee.getSalary());
            statement.setString(3, employee.getCpf());

            statement.execute();
        }
    }

    @Override
    public void delete(Employee employee) throws SQLException {
        PreparedStatement statement = null;
        try(Connection connection = DriverManager.getConnection("jdbc:sqlite:petshop.db")) {
            createTableIfNotExists();
            String sql = "DELETE FROM employees WHERE CPF = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, employee.getCpf());
            statement.execute();
        }
    }

    private void createTableIfNotExists() throws SQLException {
        PreparedStatement statement = null;
        try(Connection connection = DriverManager.getConnection("jdbc:sqlite:petshop.db")){
            String sql = "CREATE TABLE IF NOT EXISTS employees (\n" +
                    "CPF text NOT NULL,\n" +
                    "name text NOT NULL,\n" +
                    "salary float NOT NULL\n" +
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

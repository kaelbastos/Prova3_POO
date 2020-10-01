package DAO;

import model.Animal;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class AnimalSQliteDAO implements DAO <Animal, Integer> {
    @Override
    public void create(List<Animal> list) throws SQLException {
        PreparedStatement statement = null;
        try(Connection connection = DriverManager.getConnection("jdbc:sqlite:petshop.db")){
            createTableIfNotExists();
            String sql = "INSERT INTO animals (id, name ,ownerName, ownerPhone, allergic, additionalInfo) " +
                    "values (?, ?, ?, ?, ?, ?);";
            statement = connection.prepareStatement(sql);
            for (Animal a:
                 list) {
                statement.setInt(1,a.getId());
                statement.setString(2, a.getAnimalName());
                statement.setString(3, a.getOwnerName());
                statement.setString(4, a.getOwnerPhone());
                int allergic = 0;
                if(a.isAllergic()){
                    allergic = 1;
                }
                statement.setInt(5, allergic);
                statement.setString(6, a.getAdditionalInfo());
                statement.execute();
            }
        }
    }

    @Override
    public void create(Animal animal) throws SQLException {
        PreparedStatement statement = null;
        try(Connection connection = DriverManager.getConnection("jdbc:sqlite:petshop.db")){
            createTableIfNotExists();
            String sql = "INSERT INTO animals (id, name ,ownerName, ownerPhone, allergic, additionalInfo) " +
                    "values (?, ?, ?, ?, ?, ?);";
            statement = connection.prepareStatement(sql);
            statement.setInt(1,animal.getId());
            statement.setString(2, animal.getAnimalName());
            statement.setString(3, animal.getOwnerName());
            statement.setString(4, animal.getOwnerPhone());
            int allergic = 0;
            if(animal.isAllergic()){
                allergic = 1;
            }
            statement.setInt(5, allergic);
            statement.setString(6, animal.getAdditionalInfo());
            statement.execute();
        }
    }

    @Override
    public Map<Integer, Animal> importAsMap() throws SQLException {
        List<Animal> list = importAsList();
        if(!list.isEmpty()){
            Map<Integer, Animal> map = new LinkedHashMap<>();
            for (Animal a:
                    list) {
                map.put(a.getId(), a);
            }
            return map;
        }
        return null;
    }

    @Override
    public List<Animal> importAsList() throws SQLException {
        List<Animal> list = new ArrayList<>();
        PreparedStatement statement = null;
        try(Connection connection = DriverManager.getConnection("jdbc:sqlite:petshop.db")){
            createTableIfNotExists();
            String sql = "SELECT id, name ,ownerName, ownerPhone, allergic, additionalInfo FROM animals";
            statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String ownerName = resultSet.getString("ownerName");
                String ownerPhone = resultSet.getString("ownerPhone");
                boolean allergic = resultSet.getBoolean("allergic");
                String additionalInfo = resultSet.getString("additionalInfo");

                Animal animal = new Animal(id, name, ownerName, ownerPhone,additionalInfo, allergic);
                list.add(animal);
            }
            return list;
        }
    }

    public Animal importFromId(int id) throws SQLException {
        PreparedStatement statement = null;
        try(Connection connection = DriverManager.getConnection("jdbc:sqlite:petshop.db")){
            createTableIfNotExists();
            String sql = "SELECT name ,ownerName, ownerPhone, allergic, additionalInfo FROM animals WHERE id = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                String name = resultSet.getString("name");
                String ownerName = resultSet.getString("ownerName");
                String ownerPhone = resultSet.getString("ownerPhone");
                boolean allergic = resultSet.getBoolean("allergic");
                String additionalInfo = resultSet.getString("additionalInfo");
                return new Animal(id, name, ownerName, ownerPhone,additionalInfo, allergic);
            } else {
                return null;
            }
        }
    }

    @Override
    public void update(Animal animal) throws SQLException {
        PreparedStatement statement = null;
        try(Connection connection = DriverManager.getConnection("jdbc:sqlite:petshop.db")){
            createTableIfNotExists();
            String sql = "UPDATE animals SET name = ?, ownerName = ?, ownerPhone = ?, allergic = ?, additionalInfo = ? " +
                    "WHERE id = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, animal.getAnimalName());
            statement.setString(2, animal.getOwnerName());
            statement.setString(3, animal.getOwnerPhone());
            int allergic = 0;
            if(animal.isAllergic()){
                allergic = 1;
            }
            statement.setInt(4, allergic);
            statement.setString(5, animal.getAdditionalInfo());
            statement.setInt(6,animal.getId());
            statement.execute();
        }
    }

    @Override
    public void delete(Animal animal) throws SQLException {
        PreparedStatement statement = null;
        try(Connection connection = DriverManager.getConnection("jdbc:sqlite:petshop.db")) {
            createTableIfNotExists();
            String sql = "DELETE FROM animals WHERE ID = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, animal.getId());
            statement.execute();
        }
    }

    private void createTableIfNotExists() throws SQLException {
        PreparedStatement statement = null;
        try(Connection connection = DriverManager.getConnection("jdbc:sqlite:petshop.db")){
            String sql = "CREATE TABLE IF NOT EXISTS animals (\n" +
                    "id integer NOT NULL,\n" +
                    "name text NOT NULL,\n" +
                    "ownerName text NOT NULL,\n" +
                    "ownerPhone text NOT NULL,\n" +
                    "allergic int NOT NULL,\n" +
                    "additionalInfo text\n" +
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

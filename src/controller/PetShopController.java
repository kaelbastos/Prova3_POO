package controller;

import DAO.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Animal;
import model.Appointment;
import model.Employee;
import model.ServiceOption;
import view.loaders.*;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

public class PetShopController {
    @FXML private TableView<Appointment> appointmentTable;
    @FXML private TableColumn<Appointment, String> employeeColumn;
    @FXML private TableColumn<Appointment, String> serviceColumn;
    @FXML private TableColumn<Appointment, String> timeColumn;
    @FXML private ChoiceBox<String> employeeChoice;
    @FXML private ComboBox<Integer> animalCombo;
    @FXML private DatePicker datePicker;
    @FXML private ChoiceBox<ServiceOption> serviceChoice;
    @FXML private TextField price;
    @FXML private Slider hourSlider;
    @FXML private Slider minuteSlider;
    @FXML private Button closeButton;
    @FXML private TextField filterInput;


    private static List<Employee> employeeList = new ArrayList<>();
    private static List<Animal> animalList = new ArrayList<>();
    private static Map<LocalDateTime, Appointment> schedule = new LinkedHashMap<>();

    public void init() throws SQLException {
        DAO<Employee, String> employeeDAO = new EmployeeSQLIiteDAO();
        employeeList.addAll(employeeDAO.importAsList());
        DAO<Animal, Integer> animalDAO = new AnimalSQliteDAO();
        animalList.addAll(animalDAO.importAsList());
        DAO<Appointment, LocalDateTime> appointmentDAO = new AppointmentSQLiteDAO();
        schedule.putAll(appointmentDAO.importAsMap());
        bindTable();
    }

    public void bindTable(){
        employeeColumn.setCellValueFactory(new PropertyValueFactory<>("employeeName"));
        serviceColumn.setCellValueFactory(new PropertyValueFactory<>( "serviceValue"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("dateValue"));
        serviceChoice.setItems(FXCollections.observableArrayList(ServiceOption.values()));
        employeeChoice.setItems(FXCollections.observableArrayList(
                employeeList.stream()
                        .map(Employee::getName)
                        .collect(Collectors.toList())
        ));
        animalCombo.setItems(FXCollections.observableArrayList(
                animalList.stream()
                        .map(Animal::getId)
                        .collect(Collectors.toList())
        ));
        appointmentTable.setItems(FXCollections.observableArrayList(schedule.values()));
    }

    public static Employee getEmployee(String cpf){
        for (Employee employee : employeeList) {
            if(employee.getCpf().equals(cpf)){
                return employee;
            }
        }
        return null;
    }

    public static Employee getEmployeeByName(String name){
        for (Employee employee : employeeList) {
            if(employee.getName().equals(name)){
                return employee;
            }
        }
        return null;
    }

    public static Animal getAnimal(Integer id){
        for (Animal animal : animalList) {
            if(animal.getId() == id){
                return animal;
            }
        }
        return null;
    }

    public void updatePrice(){
        if(!serviceChoice.getSelectionModel().isEmpty()) {
            price.setText(Float.toString(ServiceOption.getPrice(serviceChoice.getSelectionModel().getSelectedItem())));
        }
    }

    public void updateTable() throws SQLException {
        appointmentTable.getItems().clear();
        appointmentTable.getItems().addAll(schedule.values());
        appointmentTable.refresh();
    }

    public void filterTable(){
        if(!filterInput.getText().isEmpty()){
            appointmentTable.getItems().clear();
            for (Appointment appointment:
                    schedule.values()) {
                String filter = filterInput.getText();
                if(appointment.getServiceValue().toUpperCase().contains(filter.toUpperCase()) ||
                        appointment.getEmployeeName().toUpperCase().contains(filter.toUpperCase()) ||
                        appointment.getDateValue().toUpperCase().contains(filter)){
                    appointmentTable.getItems().add(appointment);
                }
            }
            appointmentTable.refresh();
        } else {
            appointmentTable.getItems().clear();
            appointmentTable.getItems().addAll(schedule.values());
            appointmentTable.refresh();
        }
    }

    public void saveAppointment() throws SQLException {
        if(!employeeChoice.getSelectionModel().isEmpty() &&
                !animalCombo.getSelectionModel().isEmpty() &&
                datePicker.getValue() != null &&
                !serviceChoice.getSelectionModel().isEmpty() &&
                !price.getText().isEmpty()){
            int hour = (int) hourSlider.getValue();
            int minute = (int) minuteSlider.getValue();
            LocalTime time = LocalTime.of(hour,minute);
            Appointment appointment = new Appointment(
                    getAnimal(animalCombo.getSelectionModel().getSelectedItem()),
                    getEmployeeByName(employeeChoice.getSelectionModel().getSelectedItem()),
                    LocalDateTime.of(datePicker.getValue(), time),
                    serviceChoice.getSelectionModel().getSelectedItem(),
                    Float.parseFloat(price.getText()));
            saveAppointment(appointment);
            schedule.put(appointment.getDate(), appointment);
            updateTable();
            employeeChoice.getSelectionModel().clearSelection();
            animalCombo.getSelectionModel().clearSelection();
            datePicker.setValue(null);
            serviceChoice.getSelectionModel().clearSelection();
            price.clear();
        }
    }

    public static void saveAppointment(Appointment appointment) throws SQLException {
        if(!schedule.containsValue(appointment)){
            DAO<Appointment, LocalDateTime> appointmentDAO = new AppointmentSQLiteDAO();
            try{
                appointmentDAO.create(appointment);
                schedule.put(appointment.getDate(), appointment);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public static void updateAppointment(Appointment appointment, Appointment newAppointment) throws SQLException {
        if(schedule.containsValue(appointment)){
            DAO<Appointment, LocalDateTime> appointmentDAO = new AppointmentSQLiteDAO();
            appointmentDAO.delete(appointment);
            schedule.remove(appointment.getDate(), appointment);
            saveAppointment(newAppointment);
        }
    }

    public void deleteAppointment() throws SQLException {
        if(!appointmentTable.getSelectionModel().isEmpty()){
            Appointment appointment = appointmentTable.getSelectionModel().getSelectedItem();
            DAO<Appointment, LocalDateTime> appointmentDAO = new AppointmentSQLiteDAO();
            appointmentDAO.delete(appointment);
            schedule.remove(appointment.getDate(), appointment);
            updateTable();
        }
    }

    public void openAppointmentUpdate() throws SQLException {
        if(!appointmentTable.getSelectionModel().isEmpty()){
            AppointmentWindowLoader window = new AppointmentWindowLoader();
            window.showAndWait(appointmentTable.getSelectionModel().getSelectedItem());
            updateTable();
        }
    }

    public void openAddAnimalWindow() throws SQLException {
        AnimalWindowLoader animalWindow = new AnimalWindowLoader();
        animalWindow.showAndWait();
        updateTable();
    }

    public void openUpdateAnimal(Animal animal) throws SQLException {
        AnimalWindowLoader animalWindow = new AnimalWindowLoader();
        animalWindow.showAndWait(animal, 1);
        updateTable();
    }

    public void openDeleteAnimalWindow() throws SQLException {
        DeleteAnimalWindowLoader deleteAnimalWindow = new DeleteAnimalWindowLoader();
        deleteAnimalWindow.showAndWait();
        updateTable();
    }

    public void openEmployeeWindow() throws SQLException {
        EmployeeWindowLoader window = new EmployeeWindowLoader();
        window.showAndWait();
        updateTable();
    }

    public void openDeleteEmployeeWindow() throws SQLException {
        DeleteEmployeeWindowLoader window = new DeleteEmployeeWindowLoader();
        window.showAndWait();
        updateTable();
    }

    public void close(){
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

}

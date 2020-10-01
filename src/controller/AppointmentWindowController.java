package controller;

import DAO.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Appointment;
import model.Employee;
import model.ServiceOption;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AppointmentWindowController {
    @FXML private ChoiceBox<String> employeeChoice;
    @FXML private TextField animalName;
    @FXML private TextField ownerName;
    @FXML private TextField ownerPhone;
    @FXML private ChoiceBox<ServiceOption> serviceChoice;
    @FXML private TextField price;
    @FXML private DatePicker datePicker;
    @FXML private Slider hourSlider;
    @FXML private Slider minuteSlider;
    @FXML private Button closeButton;
    @FXML private Button updateButton;

    private final List<Employee> employeeList = new ArrayList<>();
    private final List<ServiceOption> serviceOptionList = new ArrayList<>();
    private Appointment originalAppointent;

    public void fill(Appointment appointment) throws SQLException {

        this.originalAppointent = appointment;
        animalName.setEditable(false);
        ownerName.setEditable(false);
        ownerPhone.setEditable(false);
        DAO<Employee, String> employeeDAO = new EmployeeSQLIiteDAO();
        employeeList.addAll(employeeDAO.importAsList());
        employeeChoice.setItems(FXCollections.observableArrayList(employeeList.stream()
                .map(Employee::getName)
                .collect(Collectors.toList())
        ));
        employeeChoice.getSelectionModel().select(appointment.getEmployee().getName());
        animalName.setText(appointment.getAnimal().getAnimalName());
        ownerName.setText(appointment.getAnimal().getOwnerName());
        ownerPhone.setText(appointment.getAnimal().getOwnerPhone());
        serviceOptionList.addAll(Arrays.asList(ServiceOption.values()));
        serviceChoice.setItems(FXCollections.observableArrayList(serviceOptionList));
        serviceChoice.getSelectionModel().select(appointment.getService());
        price.setText(Float.toString(appointment.getPrice()));
        datePicker.setValue(appointment.getDate().toLocalDate());
        hourSlider.adjustValue(appointment.getDate().getHour());
        minuteSlider.adjustValue(appointment.getDate().getMinute());
    }

    public void update() throws SQLException {
        if(!employeeChoice.getSelectionModel().isEmpty() &&
                datePicker.getValue() != null &&
                !serviceChoice.getSelectionModel().isEmpty() &&
                !price.getText().isEmpty()){

            int hour = (int) hourSlider.getValue();
            int minute = (int) minuteSlider.getValue();
            LocalTime time = LocalTime.of(hour,minute);
            Appointment appointment = new Appointment(originalAppointent.getAnimal(),
                                                        PetShopController.getEmployeeByName(employeeChoice.getSelectionModel().getSelectedItem()),
                                                        LocalDateTime.of(datePicker.getValue(), time),
                                                        serviceChoice.getSelectionModel().getSelectedItem(),
                                                        Float.parseFloat(price.getText()));
            PetShopController.updateAppointment(originalAppointent, appointment);
        }
        close();
    }

    public void close(){
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}

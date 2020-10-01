package controller;

import DAO.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Employee;
import java.sql.SQLException;

public class DeleteEmployeeWindowController {
    @FXML private TextField cpfInput;
    @FXML private Button closeButton;

    public void delete(ActionEvent actionEvent) throws SQLException {
        DAO<Employee, String> employeeDAO = new EmployeeSQLIiteDAO();
        Employee employee = PetShopController.getEmployee(cpfInput.getText());
        if(employee != null){
            employeeDAO.delete(employee);
        } else {
            cpfInput.setText("Invalid!");
            cpfInput.setStyle("-fx-text-fill: red;");
        }
    }

    public void close(ActionEvent actionEvent) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}

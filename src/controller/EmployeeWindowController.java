package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EmployeeWindowController {
    @FXML TextField employeeName;
    @FXML TextField employeeCPF;
    @FXML Button closeButton;

    public void saveEmployee(){

    }

    public void close(){
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}

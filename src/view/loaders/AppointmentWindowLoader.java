package view.loaders;

import controller.AppointmentWindowController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Appointment;
import model.ServiceOption;

import java.io.IOException;
import java.sql.SQLException;

public class AppointmentWindowLoader {

    public void showAndWait(Appointment appointment) throws SQLException {
        FXMLLoader loader = new FXMLLoader();
        try{
            Pane anchorPane = loader.load(getClass().getResource("../fxml/FXMLAppointmentWindow.fxml").openStream());
            AppointmentWindowController ctrl = loader.getController();
            ctrl.fill(appointment);
            Stage stage = new Stage();
            stage.setTitle("Appointment window");
            stage.setScene(new Scene(anchorPane));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

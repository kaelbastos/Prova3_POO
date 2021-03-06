package view.loaders;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class DeleteEmployeeWindowLoader {
    public void showAndWait(){
        FXMLLoader loader = new FXMLLoader();
        try {
            Pane anchorPane = loader.load(getClass().getResource("../fxml/FXMLDeleteEmployeeWindow.fxml").openStream());
            Stage stage = new Stage();
            stage.setScene(new Scene(anchorPane));
            stage.setTitle("Delete Employee");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}

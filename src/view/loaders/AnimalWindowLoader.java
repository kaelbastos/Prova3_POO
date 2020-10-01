package view.loaders;

import controller.AnimalWindowController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Animal;

import java.io.IOException;

public class AnimalWindowLoader {

    public void showAndWait (){
        showAndWait(null, 0);
    }

    public void showAndWait(Animal animal, Integer modo){
        FXMLLoader loader = new FXMLLoader();
        try {
            Pane anchorPane = loader.load(getClass().getResource("../fxml/FXMLAnimalWindow.fxml").openStream());
            Stage stage = new Stage();
            stage.setScene(new Scene(anchorPane));
            stage.setTitle("Animal Window");
            if (animal != null){
                AnimalWindowController ctrl = loader.getController();
                if(modo == 1){
                    ctrl.setUpdateMode(animal);
                    stage.setTitle("Update Animal");
                } else if (modo == 2){
                    stage.setTitle("Visualize Animal");
                    ctrl.setVisualizeMode(animal);
                }
            }
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}

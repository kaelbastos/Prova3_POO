package view.loaders;

import DAO.CreateDBDAO;
import controller.PetShopController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.sql.SQLException;

public class PetShopLoader extends Application {
    public static void main(String[] args) throws SQLException {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        Pane anchorPane = (Pane) loader.load(getClass().getResource("../fxml/FXMLPetShop.fxml").openStream());
        PetShopController ctrl = loader.getController();
        ctrl.init();
        stage.setTitle("Petshop Manager");
        stage.setScene(new Scene(anchorPane));
        stage.show();
    }
}

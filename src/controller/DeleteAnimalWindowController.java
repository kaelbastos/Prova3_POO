package controller;

import DAO.AnimalSQliteDAO;
import DAO.DAO;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Animal;
import java.sql.SQLException;

public class DeleteAnimalWindowController {
    @FXML TextField idInput;
    @FXML Button closeButton;


    public void delete() throws SQLException {
        if(!idInput.getText().isEmpty() && isNumeric(idInput.getText())){
            int id = Integer.parseInt(idInput.getText());
            DAO<Animal, Integer> animalDAO = new AnimalSQliteDAO();
            Animal animal = PetShopController.getAnimal(id);
            if(animal != null){
                animalDAO.delete(animal);
                idInput.clear();
            } else {
                idInput.setText("Invalid!");
                idInput.setStyle("-fx-text-fill: red;");
            }
        }
    }

    private boolean isNumeric(String string){
        try {
            Integer.parseInt(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public void close(){
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}

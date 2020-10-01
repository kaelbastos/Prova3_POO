package controller;

import DAO.AnimalSQliteDAO;
import DAO.DAO;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Animal;

import java.sql.SQLException;

public class AnimalWindowController{
    @FXML TextField idInput;
    @FXML TextField animalNameInput;
    @FXML TextField ownerNameInput;
    @FXML TextField ownerPhoneInput;
    @FXML CheckBox allergicCheck;
    @FXML TextArea additionalInput;
    @FXML Button cancelButton;
    @FXML Button saveButton;
    private boolean updateMode = false;

    public void fill(Animal animal){
        idInput.setText(Integer.toString(animal.getId()));
        animalNameInput.setText(animal.getAnimalName());
        ownerNameInput.setText(animal.getOwnerName());
        ownerPhoneInput.setText(animal.getOwnerPhone());
        allergicCheck.setSelected(animal.isAllergic());
        additionalInput.setText(animal.getAdditionalInfo());
        //saveButton.setVisible(false);
    }

    public void setUpdateMode(Animal animal){
        updateMode = true;
        idInput.setEditable(false);
        animalNameInput.setEditable(false);
        fill(animal);
    }

    public void setVisualizeMode(Animal animal){
        saveButton.setVisible(false);
        idInput.setEditable(false);
        animalNameInput.setEditable(false);
        ownerNameInput.setEditable(false);
        ownerPhoneInput.setEditable(false);
        allergicCheck.setDisable(true);
        fill(animal);
    }

    public void save() throws SQLException {
        if(!idInput.getText().isEmpty() &&
                !animalNameInput.getText().isEmpty() &&
                !ownerNameInput.getText().isEmpty() &&
                !ownerPhoneInput.getText().isEmpty()){
            Animal animal = new Animal(Integer.parseInt(idInput.getText()),
                                            animalNameInput.getText(),
                                            ownerNameInput.getText(),
                                            ownerPhoneInput.getText(),
                                            additionalInput.getText(),
                                            allergicCheck.isSelected()
            );
            DAO <Animal, Integer> animalDAO = new AnimalSQliteDAO();
            if(updateMode){
                animalDAO.update(animal);
            } else {
                animalDAO.create(animal);
            }
            idInput.clear();
            animalNameInput.clear();
            ownerNameInput.clear();
            ownerPhoneInput.clear();
            allergicCheck.setSelected(false);
            additionalInput.clear();
        }

    }

    public void cancel(){
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }//ok
}

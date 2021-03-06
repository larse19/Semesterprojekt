package org.gruppe06.presentation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import org.gruppe06.domain.CredIT;
import org.gruppe06.domain.ProgramSystem;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class createProgramController  implements Initializable {

    @FXML
    private TextField programNameField;

    @FXML
    private TextField yearField;

    @FXML
    private TextField producerID;

    @FXML
    private TextField producerRole;

    private ProgramSystem programSystem;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        programSystem = new ProgramSystem();
        if(CredIT.getCredITInstance().getUserRole() == 2){
            producerID.setText(CredIT.getCredITInstance().getUsername());
        }
    }

    //Creates a program
    @FXML
    void createProgramHandler(ActionEvent event){
        if (programSystem.createNewProgram(programNameField.getText(), yearField.getText(), producerID.getText(), producerRole.getText())){
            programNameField.setText("");
            yearField.setText("");
        } else {
            System.out.println("Program with ID: " + programNameField.getText() + " already exists!");
        }
    }

    //Returns to the front page
    @FXML
    void returnHandler(ActionEvent event) throws IOException {
        if(CredIT.getCredITInstance().getUserRole() == 1) {
            App.setRoot("adminFrontPage");
        }
        else{
            App.setRoot("producerFrontPage");
        }
    }

}


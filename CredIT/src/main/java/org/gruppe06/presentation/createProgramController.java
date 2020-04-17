package org.gruppe06.presentation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import org.gruppe06.domain.ProgramSystem;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class createProgramController  implements Initializable {

    @FXML
    public TextField idField;

    @FXML
    private TextField programNameField;

    @FXML
    private RadioButton seriesButton;

    @FXML
    private RadioButton movieButton;

    @FXML
    private TextField numberOfSeasonsField;

    @FXML
    private TextField numberOfEpisodesField;

    @FXML
    private TextField yearField;

    @FXML
    private TextField descriptionField;

    @FXML
    private Button createProgramButton;

    @FXML
    private Button backButton;

    private ProgramSystem programSystem;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        programSystem = new ProgramSystem();
    }

    @FXML
    void createProgramHandler(ActionEvent event){
        if (programSystem.createNewProgram(programNameField.getText(), yearField.getText())){
            idField.setText("");
            programNameField.setText("");
            yearField.setText("");
        } else {
            System.out.println("Program with ID: " + programNameField.getText() + " already exists!");
        }
        //App.setRoot("addCastMembers");
    }


    @FXML
    void returnHandler(ActionEvent event) throws IOException {
        App.setRoot("adminFrontPage");
    }

}


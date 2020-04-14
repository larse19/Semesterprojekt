package org.gruppe06.presentation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

import java.io.IOException;

public class createProgramController {

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

    @FXML
    void createProgramHandler(ActionEvent event) throws IOException{
        App.setRoot("addCastMembers");
    }

    @FXML
    void programTypeButtons(ActionEvent event) {

    }

    @FXML
    void returnHandler(ActionEvent event) throws IOException {
        App.setRoot("producerFrontPage");
    }

}


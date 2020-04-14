package org.gruppe06.presentation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.IOException;

public class editProgramAdminController {

    @FXML
    private ListView<?> listOfPrograms;

    @FXML
    private Button backButton;

    @FXML
    private TextField numberOfEpisodesField;

    @FXML
    private Button updateButton;

    @FXML
    private Button addMemberButton;

    @FXML
    private TextField descriptionField;

    @FXML
    private Button updateDescriptionButton;

    @FXML
    private Button deleteProgramButton;

    @FXML
    void addMemberButtonHandler(ActionEvent event) throws IOException {
        App.setRoot("addCastMembers");
    }

    @FXML
    void backButtonHandler(ActionEvent event) throws IOException {
        App.setRoot("producerFrontPage");
    }

    @FXML
    void deleteProgramButtonHandler(ActionEvent event) {

    }

    @FXML
    void updateButtonHandler(ActionEvent event) {

    }

    @FXML
    void updateDescriptionButtonHandler(ActionEvent event) {

    }

}

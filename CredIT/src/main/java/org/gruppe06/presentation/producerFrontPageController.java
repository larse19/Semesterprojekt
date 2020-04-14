package org.gruppe06.presentation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;

public class producerFrontPageController {

    @FXML
    private Button signOutButton;

    @FXML
    private TextArea searchResultField;

    @FXML
    private TextField searchField;

    @FXML
    private Button searchButton;

    @FXML
    private Button createProgramButton;

    @FXML
    private Button editProgramButton;

    @FXML
    void createProgramHandler(ActionEvent event) throws IOException{
        App.setRoot("createProgram");
    }

    @FXML
    void editProgramHandler(ActionEvent event) throws IOException{
        App.setRoot("editProgram");
    }

    @FXML
    void searchHandler(ActionEvent event) {

    }

    @FXML
    void signOutHandler(ActionEvent event) throws IOException {
        App.setRoot("frontPage");
    }

}


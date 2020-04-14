package org.gruppe06.presentation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;

public class adminFrontPageController {

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
    private Button addUserButton;

    @FXML
    private Button editUserButton;

    @FXML
    void addUserButtonHandler(ActionEvent event) throws  IOException{
        App.setRoot("addUser");
    }

    @FXML
    void createProgramButtonHandler(ActionEvent event) throws IOException {
        App.setRoot("createProgram");
    }

    @FXML
    void editProgramButtonHandler(ActionEvent event) throws IOException{
        App.setRoot("editProgramAdmin");
    }

    @FXML
    void editUserButtonHandler(ActionEvent event) throws IOException{
        App.setRoot("editUser");
    }

    @FXML
    void searchButtonHandler(ActionEvent event) {

    }

    @FXML
    void signOutButtonHandler(ActionEvent event) throws IOException{
        App.setRoot("frontPage");
    }

}


package org.gruppe06.presentation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.IOException;

public class editUserController {

    @FXML
    private ListView<?> usersList;

    @FXML
    private TextField roleField;

    @FXML
    private Button backButton;

    @FXML
    private Button updateButton;

    @FXML
    private TextField nameField;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    @FXML
    private Button deleteUserButton;

    @FXML
    private TextField userSearchField;

    @FXML
    private Button searchButton;

    @FXML
    void backButtonHandler(ActionEvent event) throws IOException {
        App.setRoot("adminFrontPage");
    }

    @FXML
    void deleteUserButtonHandler(ActionEvent event) {

    }

    @FXML
    void searchButtonHandler(ActionEvent event) {

    }

    @FXML
    void updateButtonHandler(ActionEvent event) {

    }

}


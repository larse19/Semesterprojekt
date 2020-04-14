package org.gruppe06.presentation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

import java.io.IOException;

public class addUserController {

    @FXML
    private Button createButton;

    @FXML
    private RadioButton adminUser;

    @FXML
    private RadioButton producerUser;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    @FXML
    private TextField emailField;

    @FXML
    private Button backButton;

    @FXML
    void backButtonHandler(ActionEvent event) throws IOException {
        App.setRoot("adminFrontPage");
    }

    @FXML
    void createButtonHandler(ActionEvent event) {

    }

    @FXML
    void userTypeButtonHandler(ActionEvent event) {

    }

}


package org.gruppe06.presentation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class loginPageController {

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Button loginButton;

    @FXML
    private Button backButton;

    @FXML
    void loginHandler(ActionEvent event) throws IOException{
        if (password != null) {
            App.setRoot("adminFrontPage");
        }
    }

    @FXML
    void returnHandler(ActionEvent event) throws IOException {
        App.setRoot("frontPage");
    }

}

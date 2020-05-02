package org.gruppe06.presentation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.gruppe06.domain.LoginSystem;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class loginPageController implements Initializable {

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Label wrongCredentialsLabel;

    @FXML
    private Button loginButton;

    @FXML
    private Button backButton;

    private LoginSystem loginSystem;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loginSystem = new LoginSystem();
    }

    @FXML
    void loginHandler(ActionEvent event) throws IOException{
        int loginResult = loginSystem.login(username.getText(), password.getText());

        if (loginResult == 1) {
            App.setRoot("adminFrontPage");
        }else if(loginResult == 2){
            App.setRoot("producerFrontPage");
        }else if(loginResult == 0){
            wrongCredentialsLabel.setText("Wrong username or password");
        }
    }

    @FXML
    void returnHandler(ActionEvent event) throws IOException {
        App.setRoot("frontPage");
    }

}

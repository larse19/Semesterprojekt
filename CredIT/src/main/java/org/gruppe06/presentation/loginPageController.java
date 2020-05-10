package org.gruppe06.presentation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.gruppe06.domain.CredIT;
import org.gruppe06.domain.LoginSystem;
import org.gruppe06.domain.PasswordAuthentication;

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
    private PasswordAuthentication passwordAuthentication;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loginSystem = new LoginSystem();
        passwordAuthentication = new PasswordAuthentication();
        setEvent(username);
        setEvent(password);
    }

    @FXML
    void loginHandler(ActionEvent event) throws IOException{
        int loginResult = loginSystem.login(username.getText(), password.getText());

        if (loginResult == 1) {
            CredIT.getCredITInstance().setUserRole(1);
            App.setRoot("adminFrontPage");
        }else if(loginResult == 2){
            CredIT.getCredITInstance().setUserRole(2);
            App.setRoot("producerFrontPage");
        }else if(loginResult == 0){
            wrongCredentialsLabel.setText("Wrong username or password");
        }
    }

    @FXML
    void returnHandler(ActionEvent event) throws IOException {
        App.setRoot("frontPage");
    }

    private void setEvent(TextField textField){
        textField.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
            if (ev.getCode() == KeyCode.ENTER){
                loginButton.fire();
            }
        });
    }

}

package org.gruppe06.presentation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.gruppe06.domain.CredIT;
import org.gruppe06.domain.PasswordAuthentication;
import org.gruppe06.domain.UserSystem;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class addUserController implements Initializable {

    @FXML
    private Button createButton;

    @FXML
    private RadioButton adminUserRadio;

    @FXML
    private RadioButton producerUserRadio;

    @FXML
    private TextField nameField;

    @FXML
    private TextField passwordField;

    @FXML
    private TextField emailField;

    @FXML
    private Label usernameLabel;

    @FXML
    private Button backButton;

    private ToggleGroup userRoleGroup;

    private UserSystem userSystem;
    private PasswordAuthentication passwordAuthentication;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userSystem = new UserSystem();
        userRoleGroup = new ToggleGroup();
        passwordAuthentication = new PasswordAuthentication();
        adminUserRadio.setToggleGroup(userRoleGroup);
        producerUserRadio.setToggleGroup(userRoleGroup);
        adminUserRadio.fire();
    }

    @FXML
    void backButtonHandler(ActionEvent event) throws IOException {
        if(CredIT.getCredITInstance().getUserRole() == 1) {
            App.setRoot("adminFrontPage");
        }
        else{
            App.setRoot("producerFrontPage");
        }
    }

    @FXML
    void createButtonHandler(ActionEvent event) {
        String username;
        if (!nameField.getText().equals("") && !passwordField.getText().equals("")) {
            if (userRoleGroup.getSelectedToggle() == adminUserRadio) {
                username = userSystem.createSystemAdministrator(nameField.getText(), passwordAuthentication.hash(passwordField.getText().toCharArray()));
                usernameLabel.setText("New System Administrator: " + username);
            }
            else if(userRoleGroup.getSelectedToggle() == producerUserRadio){
                username = userSystem.createProducer(nameField.getText(), passwordAuthentication.hash(passwordField.getText().toCharArray()));
                usernameLabel.setText("New producer: " + username);
            }

        }

    }

    @FXML
    void userTypeButtonHandler(ActionEvent event) {

    }


}


package org.gruppe06.presentation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userSystem = new UserSystem();
        userRoleGroup = new ToggleGroup();
        adminUserRadio.setToggleGroup(userRoleGroup);
        producerUserRadio.setToggleGroup(userRoleGroup);
        adminUserRadio.fire();
    }

    @FXML
    void backButtonHandler(ActionEvent event) throws IOException {
        App.setRoot("adminFrontPage");
    }

    @FXML
    void createButtonHandler(ActionEvent event) {
        String username;
        if (!nameField.getText().equals("") && !passwordField.getText().equals("")) {
            if (userRoleGroup.getSelectedToggle() == adminUserRadio) {
                username = userSystem.createSystemAdministrator(nameField.getText(), passwordField.getText());
                usernameLabel.setText("New System Administrator: " + username);
            }
            else if(userRoleGroup.getSelectedToggle() == producerUserRadio){
                username = userSystem.createProducer(nameField.getText(), passwordField.getText());
                usernameLabel.setText("New producer: " + username);
            }

        }

    }

    @FXML
    void userTypeButtonHandler(ActionEvent event) {

    }


}


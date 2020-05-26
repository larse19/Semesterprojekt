package org.gruppe06.presentation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import org.gruppe06.domain.CredIT;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class adminFrontPageController implements Initializable {

    @FXML
    private Label nameLabel, usernameLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CredIT credIT = CredIT.getCredITInstance();
        nameLabel.setText(credIT.getName());
        usernameLabel.setText(credIT.getUsername());
    }
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
        App.setRoot("editProgram");
    }

    @FXML
    void editUserButtonHandler(ActionEvent event) throws IOException{
        App.setRoot("editUser");
    }

    @FXML
    void goToCastMemberHandler(ActionEvent event) throws IOException{
        App.setRoot("createCastMember");
    }

    @FXML
    void signOutButtonHandler(ActionEvent event) throws IOException{
        App.setRoot("frontPage");
    }

}


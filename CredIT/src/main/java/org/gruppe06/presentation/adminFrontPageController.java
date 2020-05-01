package org.gruppe06.presentation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.gruppe06.domain.CredIT;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class adminFrontPageController implements Initializable {

    @FXML
    private Parent searchProgram;

    @FXML
    private SearchController searchProgramController;

    @FXML
    private Button signOutButton;

    @FXML
    private Label nameLabel, usernameLabel;

    @FXML
    private Button createProgramButton;

    @FXML
    private Button editProgramButton;

    @FXML
    private Button addUserButton;

    @FXML
    private Button editUserButton;

    @FXML
    private Button newCastMemberButton;

    private CredIT credIT;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        credIT = CredIT.getCredITInstance();
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
        App.setRoot("editProgramAdmin");
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


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

public class producerFrontPageController implements Initializable {

    @FXML
    private Parent searchProgram;

    @FXML
    private SearchController searchController;

    @FXML
    private Button signOutButton;

    @FXML
    private Button createProgramButton;

    @FXML
    private Button editProgramButton;

    @FXML
    private Label nameLabel, usernameLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nameLabel.setText(CredIT.getCredITInstance().getName());
        usernameLabel.setText(CredIT.getCredITInstance().getUsername());
    }

    @FXML
    void createProgramButtonHandler(ActionEvent event) throws IOException{
        App.setRoot("createProgram");
    }

    @FXML
    void editProgramButtonHandler(ActionEvent event) throws IOException{
        App.setRoot("editProgram");
    }


    @FXML
    void signOutButtonHandler(ActionEvent event) throws IOException {
        App.setRoot("frontPage");
    }

    @FXML
    void goToCastMemberHandler(ActionEvent event) throws IOException {
        App.setRoot("addCastMembers");
    }


}


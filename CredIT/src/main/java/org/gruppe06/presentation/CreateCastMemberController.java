package org.gruppe06.presentation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.gruppe06.domain.CastMemberSystem;
import org.gruppe06.domain.CredIT;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CreateCastMemberController implements Initializable {

    @FXML
    private Button backButton;

    @FXML
    private TextField createIDTextField;

    @FXML
    private TextField createNameTextField;

    @FXML
    private Button addButton;

    @FXML
    private TextField updateIDTextField;

    @FXML
    private TextField updateNameTextField;

    @FXML
    private Button updateButton;

    @FXML
    private TextField deleteIDTextField;

    @FXML
    private Button addButton11;

    @FXML
    private Label createLabel;

    @FXML
    private Label updateLabel;

    @FXML
    private Label deleteLabel;

    private CastMemberSystem castMemberSystem;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        castMemberSystem = new CastMemberSystem();
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
        if(castMemberSystem.createNewCastMember(createIDTextField.getText(), createNameTextField.getText())){
            createLabel.setText(createNameTextField.getText() + " added");
            createIDTextField.setText("");
            createNameTextField.setText("");
        }else{
            createLabel.setText("Cast member ID: " + createIDTextField.getText() + "already exists");
        }

    }

    @FXML
    void deleteButtonHandler(ActionEvent event) {
        if(castMemberSystem.deleteCastMember(deleteIDTextField.getText())){
            deleteLabel.setText(deleteIDTextField.getText() + " is deleted");
            deleteIDTextField.setText("");
        }else{
            deleteLabel.setText(deleteIDTextField.getText() + " doesn't exist");
        }
    }

    @FXML
    void updateButtonHandler(ActionEvent event) {
        if(castMemberSystem.updateCastMember(updateIDTextField.getText(), updateNameTextField.getText())){
            updateLabel.setText("User: " + updateIDTextField.getText() + " name is now: " + updateNameTextField.getText());
            updateNameTextField.setText("");
            updateIDTextField.setText("");
        }else{
            updateLabel.setText("User; " + updateIDTextField.getText() + " doesn't exist");
        }
    }



}


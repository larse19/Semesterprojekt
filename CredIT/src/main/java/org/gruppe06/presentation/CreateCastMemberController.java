package org.gruppe06.presentation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.gruppe06.domain.CastMemberSystem;
import org.gruppe06.domain.CredIT;
import org.gruppe06.domain.SearchSystem;
import org.gruppe06.domain.UserSystem;
import org.gruppe06.interfaces.ICastMember;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CreateCastMemberController implements Initializable {

    @FXML
    private Button backButton;

    @FXML
    private TextField createNameTextField;

    @FXML
    private Button addButton;

    @FXML
    private SearchSystem updateIDTextField;

    @FXML
    private TextField updateNameTextField;

    @FXML
    private Button updateButton;

    @FXML
    private SearchSystem deleteIDTextField;

    @FXML
    private Button addButton11;

    @FXML
    private Label createLabel;

    @FXML
    private Label updateLabel;

    @FXML
    private Label deleteLabel;

    private CastMemberSystem castMemberSystem;
    private UserSystem userSystem;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        castMemberSystem = new CastMemberSystem();
        userSystem = new UserSystem();
        setSearchSystem();
    }

    private void setSearchSystem() {
        List<ICastMember> cast = castMemberSystem.getAllCastMembers();
        ArrayList<String> castIDs = new ArrayList<>();
        for (ICastMember castMember : cast) {
            castIDs.add(castMember.getID());
        }
        updateIDTextField.getEntries().addAll(castIDs);
        deleteIDTextField.getEntries().addAll(castIDs);
    }

    @FXML
    void backButtonHandler(ActionEvent event) throws IOException {
        if (CredIT.getCredITInstance().getUserRole() == 1) {
            App.setRoot("adminFrontPage");
        } else {
            App.setRoot("producerFrontPage");
        }
    }

    @FXML
    void createButtonHandler(ActionEvent event) {
        if (!createNameTextField.getText().equals("")) {
            String username = "";
            do {
                username = userSystem.createUsername(createNameTextField.getText());
            }
            while (!castMemberSystem.createNewCastMember(username, createNameTextField.getText()));

            createLabel.setText(username + " tilføjet");
            createNameTextField.setText("");
            setSearchSystem();
        }
    }

    @FXML
    void deleteButtonHandler(ActionEvent event) {
        if (castMemberSystem.deleteCastMember(deleteIDTextField.getText())) {
            deleteLabel.setText(deleteIDTextField.getText() + " er slettet");
            deleteIDTextField.setText("");
            setSearchSystem();
        } else {
            deleteLabel.setText(deleteIDTextField.getText() + " eksisterer ikke");
        }
    }

    @FXML
    void updateButtonHandler(ActionEvent event) {
        if (castMemberSystem.updateCastMember(updateIDTextField.getText(), updateNameTextField.getText())) {
            updateLabel.setText("Medvirkende:; " + updateIDTextField.getText() + "s navn er nu: " + updateNameTextField.getText());
            updateNameTextField.setText("");
            updateIDTextField.setText("");
        } else {
            updateLabel.setText("Medvirkende; " + updateIDTextField.getText() + " eksisterer ikke");
        }
    }


}


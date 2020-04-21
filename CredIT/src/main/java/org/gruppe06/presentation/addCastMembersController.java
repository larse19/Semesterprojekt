package org.gruppe06.presentation;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import org.gruppe06.domain.ProgramInfo;
import org.gruppe06.domain.ProgramSystem;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class addCastMembersController implements Initializable {

    @FXML
    private Parent programsListView;

    @FXML
    private ProgramsListViewController programsListViewController;

    @FXML
    private RadioButton actorRadioButton;

    @FXML
    private TextField nameField;

    @FXML
    private TextField roleField;

    @FXML
    private TextField numberOfEpisodesField;

    @FXML
    private Button addButton;

    @FXML
    private Button doneButton;

    @FXML
    private Label roleLabel, resultLabel;

    private ProgramSystem programSystem;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        programSystem = new ProgramSystem();
    }

    @FXML
    void addButtonHandler(ActionEvent event) {
        ProgramInfo programInfo = programsListViewController.getSelectedProgramInfo();

        if (programInfo != null) {
            if (!nameField.getText().equals("") && !roleField.getText().equals("")) {
                String role;
                if (actorRadioButton.isArmed()) {
                    role = "{actor}" + roleField.getText();
                } else {
                    role = roleField.getText();
                }
                if (programSystem.addCastMemberToProgram(programInfo, nameField.getText(), role)) {
                    resultLabel.setText(nameField.getText() + " added to cast as: " + role);
                    nameField.setText("");
                    roleField.setText("");
                } else {
                    System.out.println("Couldn't add " + nameField.getText() + " to cast");
                }
            } else {
                if(nameField.getText().equals("")){
                    System.out.println("CastID is empty");
                }
                else if(roleField.getText().equals("")){
                    System.out.println("Role is empty");
                }
            }

        } else {
            System.out.println("No program selected");
        }

    }

    @FXML
    void doneButtonHandler(ActionEvent event) throws IOException {
        App.setRoot("adminFrontPage");
    }

    @FXML
    public void actorHandler(ActionEvent actionEvent) {
        if (roleLabel.getText().equals("Rolle")) {
            roleLabel.setText("Karakter navn");
        } else {
            roleLabel.setText("Rolle");
        }
    }


}

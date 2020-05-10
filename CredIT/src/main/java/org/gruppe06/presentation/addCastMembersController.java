package org.gruppe06.presentation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import org.gruppe06.domain.CastMemberSystem;
import org.gruppe06.domain.CredIT;
import org.gruppe06.domain.SearchSystem;
import org.gruppe06.interfaces.IActor;
import org.gruppe06.interfaces.ICastMember;
import org.gruppe06.interfaces.IProgramInfo;
import org.gruppe06.domain.ProgramSystem;
import org.gruppe06.interfaces.IRole;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class addCastMembersController implements Initializable {

    @FXML
    private Parent programsListView;

    @FXML
    private ProgramsListViewController programsListViewController;

    @FXML
    private RadioButton actorRadioButton;

    @FXML
    private SearchSystem nameField;

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
    private CastMemberSystem castMemberSystem;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        programSystem = new ProgramSystem();
        castMemberSystem = new CastMemberSystem();

        ArrayList<String> castMemberNames = new ArrayList<>();
        for(ICastMember castMember : castMemberSystem.getAllCastMembers()){
            castMemberNames.add(castMember.getID());
        }
        nameField.getEntries().addAll(castMemberNames);
    }

    @FXML
    void addButtonHandler(ActionEvent event) {
        IProgramInfo programInfo = programsListViewController.getSelectedProgramInfo();

        if (programInfo != null) {
            if (!nameField.getText().equals("") && !roleField.getText().equals("")) {
                ICastMember castMember = castMemberSystem.getCastMember(nameField.getText());
                if (actorRadioButton.isSelected()) {
                    castMember.setRole(castMemberSystem.createActor(roleField.getText()));
                } else {
                    castMember.setRole(castMemberSystem.createRole(roleField.getText()));
                }
                if (programSystem.addCastMemberToProgram(programInfo, castMember)) {
                    resultLabel.setText(nameField.getText() + " added to cast as: " + castMember.toString());
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
        App.setRoot("editProgram");
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

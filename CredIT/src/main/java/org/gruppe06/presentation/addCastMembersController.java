package org.gruppe06.presentation;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import org.gruppe06.domain.*;
import org.gruppe06.interfaces.*;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class addCastMembersController implements Initializable {

    @FXML
    private ComboBox<String> castTypeCombobox;

    @FXML
    private SearchSystem nameField;

    @FXML
    private TextField roleField;

    @FXML
    private Button addButton;

    @FXML
    private Label roleLabel, resultLabel;

    private ProgramSystem programSystem;
    private CastMemberSystem castMemberSystem;
    private ProducerSystem producerSystem;
    private editProgramController editProgramController;
    private ProgramsListViewController programsListViewController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        programSystem = new ProgramSystem();
        castMemberSystem = new CastMemberSystem();
        producerSystem = new ProducerSystem();

        addCastMembersToNameField();

        String[] castTypes = {"Producer", "Skuespiller", "Andet"};
        ObservableList<String> castTypesObs = FXCollections.observableArrayList();
        castTypesObs.setAll(castTypes);
        castTypeCombobox.setItems(castTypesObs);
    }

    private void addCastMembersToNameField(){
        nameField.getEntries().clear();
        ArrayList<String> castMemberNames = new ArrayList<>();
        for (ICastMember castMember : castMemberSystem.getAllCastMembers()) {
            castMemberNames.add(castMember.getID());
        }
        nameField.getEntries().addAll(castMemberNames);
    }

    private void addProducersToNameField(){
        nameField.getEntries().clear();
        ArrayList<String> castMemberNames = new ArrayList<>();
        for (IProducer producer : producerSystem.getAllProducers()) {
            castMemberNames.add(producer.getID());
        }
        nameField.getEntries().addAll(castMemberNames);
    }

    public void setProgramsListViewController(ProgramsListViewController programsListViewController) {
        this.programsListViewController = programsListViewController;
    }

    public void setEditProgramController(editProgramController editProgramController) {
        this.editProgramController = editProgramController;
    }

    @FXML
    void updateNameField(ActionEvent event){
        if(castTypeCombobox.getSelectionModel().getSelectedItem().equals("Producer")){
            addProducersToNameField();
        }else{
            addCastMembersToNameField();
        }
    }

    @FXML
    void addButtonHandler(ActionEvent event) {
        IProgramInfo programInfo = programsListViewController.getSelectedProgramInfo();

        if (programInfo != null) {
            if (!nameField.getText().equals("") && !roleField.getText().equals("")) {
                if(castTypeCombobox.getSelectionModel().getSelectedItem() != null) {
                    if (!castTypeCombobox.getSelectionModel().getSelectedItem().equals("Producer")) {
                        addCastMembersToNameField();
                        ICastMember castMember = castMemberSystem.getCastMember(nameField.getText());
                        if (castTypeCombobox.getSelectionModel().getSelectedItem().equals("Skuespiller")) {
                            castMember.setRole(castMemberSystem.createActor(roleField.getText()));
                        } else if (castTypeCombobox.getSelectionModel().getSelectedItem().equals("Andet")) {
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
                        addProducersToNameField();
                        IProducer producer = producerSystem.getProducer(nameField.getText());
                        producer.setRole(roleField.getText());
                        if(programSystem.addProducerToProgram(programInfo, producer)) {
                            resultLabel.setText(producer.getName() + " added as " + roleField.getText());
                            nameField.setText("");
                            roleField.setText("");
                        }else{
                            System.out.println("Kunne ikke tilføje producer");
                        }
                    }
                }

            } else {

                if (nameField.getText().equals("")) {
                    System.out.println("CastID is empty");
                } else if (roleField.getText().equals("")) {
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

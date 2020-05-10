package org.gruppe06.presentation;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import org.gruppe06.domain.CastMemberSystem;
import org.gruppe06.domain.CredIT;
import org.gruppe06.domain.ProgramSystem;
import org.gruppe06.interfaces.ICast;
import org.gruppe06.interfaces.ICastMember;
import org.gruppe06.interfaces.IRole;
import org.gruppe06.persistance.CastMember;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class editProgramController implements Initializable {

    @FXML
    public Label programNameLabel;
    @FXML
    public Button saveUpdateButton;
    @FXML
    public TextField updateProgramName, editRoleField;
    @FXML
    public TextField updateReleaseYear;
    @FXML
    public Label releaseYearLabel;
    @FXML
    private Parent programsListView;

    @FXML
    private ProgramsListViewController programsListViewController;

    @FXML
    private Button editCastButton, updateCastButton, deleteCastButton;
    @FXML
    private ListView<ICastMember> editCastListView;
    @FXML
    private Label editCastLabel, roleLabel;
    @FXML
    private RadioButton actorRadioButton;

    private ProgramSystem programSystem;
    private CastMemberSystem castMemberSystem;

    private String oldName;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        programSystem = new ProgramSystem();
        castMemberSystem = new CastMemberSystem();

        setUpdateProgramVisible(false);
        setEditCastVisible(false);
    }

    private void setUpdateProgramVisible(boolean state){
        programNameLabel.setVisible(state);
        saveUpdateButton.setVisible(state);
        updateProgramName.setVisible(state);
        updateReleaseYear.setVisible(state);
        releaseYearLabel.setVisible(state);
    }

    private void setEditCastVisible(boolean state){
        updateCastButton.setVisible(state);
        deleteCastButton.setVisible(state);
        editCastListView.setVisible(state);
        editCastLabel.setVisible(state);
        roleLabel.setVisible(state);
        editRoleField.setVisible(state);
        actorRadioButton.setVisible(state);
    }

    @FXML
    void addMemberButtonHandler(ActionEvent event) throws IOException {
        App.setRoot("addCastMembers");
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
    void deleteProgramButtonHandler(ActionEvent event) {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Slet program");
            alert.setHeaderText("Du er ved at slette " + programsListViewController.getSelectedProgramInfo().getName());
            alert.setContentText("Alle henvisninger til programmet, forsvinder hvis det bliver slettet!");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                programSystem.deleteProgram(programsListViewController.getSelectedProgramInfo());
                System.out.println(programsListViewController.getSelectedProgramInfo().getName() + " deleted");
                programsListViewController.refreshListView();
            }
        }
        catch (NullPointerException ex) {
            System.out.println("no program chosen");
        }
    }

    @FXML
    void updateButtonHandler(ActionEvent event) {
        setEditCastVisible(false);
        setUpdateProgramVisible(true);

        this.oldName = programsListViewController.getSelectedProgramInfo().getName();
        updateProgramName.setText(programsListViewController.getSelectedProgramInfo().getName());
        updateReleaseYear.setText(programsListViewController.getSelectedProgramInfo().getYear());
    }

    public void saveUpdateButtonHandler(ActionEvent actionEvent) {
        programSystem.updateProgram(this.oldName, updateProgramName.getText(), updateReleaseYear.getText());
        programNameLabel.setVisible(false);
        saveUpdateButton.setVisible(false);
        updateProgramName.setVisible(false);
        updateReleaseYear.setVisible(false);
        releaseYearLabel.setVisible(false);
        programsListViewController.refreshListView();
    }

    private void refreshEditListView(){
        String programName = programsListViewController.getSelectedProgramInfo().getName();
        ObservableList<ICastMember> programCast = FXCollections.observableArrayList();
        programCast.setAll(programSystem.getProgram(programName).getCast());
        editCastListView.setItems(programCast);
    }

    @FXML
    void editCastButtonHandler(ActionEvent event) {
        setEditCastVisible(true);
        setUpdateProgramVisible(false);

        refreshEditListView();
    }

    @FXML
    void updateCastHandler(ActionEvent event) {
        try{
            ICastMember castMember = editCastListView.getSelectionModel().getSelectedItem();
            IRole irole;
            if(actorRadioButton.isSelected()){
                irole = castMemberSystem.createActor(editRoleField.getText());
            }else{
                irole = castMemberSystem.createRole(editRoleField.getText());
            }

            if(programSystem.updateCastMembersRoleOnProgram(programsListViewController.getSelectedProgramInfo().getID(), editCastListView.getSelectionModel().getSelectedItem(), irole)){
                System.out.println("Cast member updated");
                refreshEditListView();
            }else{
                System.out.println("Cast member not updated");
            }

        }catch (NullPointerException e){
            System.out.println("No cast member chosen");
        }
    }

    @FXML
    void deleteCastHandler(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Slet medvirkende");
        alert.setHeaderText("Du er ved at fjerne " + editCastListView.getSelectionModel().getSelectedItem().getName() + " fra " + programsListViewController.getSelectedProgramInfo().getName());
        alert.setContentText("Personen vil ikke længere stå som medvirkende på programmet!");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            if (programSystem.removeCastMemberFromProgram(programsListViewController.getSelectedProgramInfo().getID(), editCastListView.getSelectionModel().getSelectedItem())) {
                System.out.println("Cast member removed");
                refreshEditListView();
            } else {
                System.out.println("Cast member not removed");
            }
        }
    }
}

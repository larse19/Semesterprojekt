package org.gruppe06.presentation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import org.gruppe06.domain.ProgramSystem;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class editProgramAdminController implements Initializable {

    @FXML
    private Parent programsListView;

    @FXML
    private ProgramsListViewController programsListViewController;

    //@FXML
    //private ListView<?> listOfPrograms;

    @FXML
    private Button backButton;

    //@FXML
    //private TextField numberOfEpisodesField;

    @FXML
    private Button updateButton;

    @FXML
    private Button addMemberButton;

    @FXML
    private TextField descriptionField;

    @FXML
    private Button updateDescriptionButton;

    @FXML
    private Button deleteProgramButton;

    private ProgramSystem programSystem;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        programSystem = new ProgramSystem();
    }

    @FXML
    void addMemberButtonHandler(ActionEvent event) throws IOException {
        App.setRoot("addCastMembers");
    }

    @FXML
    void backButtonHandler(ActionEvent event) throws IOException {
        App.setRoot("adminFrontPage");
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
        catch (NullPointerException ex){
            System.out.println("no program chosen");
        }
    }

    @FXML
    void updateButtonHandler(ActionEvent event) {

    }

    @FXML
    void updateDescriptionButtonHandler(ActionEvent event) {

    }


}

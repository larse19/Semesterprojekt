package org.gruppe06.presentation;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import org.gruppe06.domain.CastMemberSystem;
import org.gruppe06.domain.CredIT;
import org.gruppe06.domain.ProgramSystem;
import org.gruppe06.interfaces.ICast;
import org.gruppe06.interfaces.ICastMember;
import org.gruppe06.interfaces.IProducer;
import org.gruppe06.interfaces.IRole;
import java.io.IOException;
import java.net.URL;
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

    //Loads the nested controller; ProgramsListViewController;
    @FXML
    private Parent programsListView;
    @FXML
    private ProgramsListViewController programsListViewController;

    //Loads the nested controller; CastMemberController
    @FXML
    private Parent addCastMember;
    @FXML
    private addCastMembersController addCastMemberController;

    @FXML
    private Button updateCastButton, deleteCastButton;
    @FXML
    private ListView<ICast> editCastListView;
    @FXML
    private Label editCastLabel, roleLabel;
    @FXML
    private CheckBox actorCheckBox;

    private ProgramSystem programSystem;
    private CastMemberSystem castMemberSystem;

    private String oldName;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        programSystem = new ProgramSystem();
        castMemberSystem = new CastMemberSystem();
        addCastMemberController.setProgramsListViewController(programsListViewController);

        editCastListView.setOnMouseClicked((EventHandler<Event>) arg0 -> {
            if(editCastListView.getSelectionModel().getSelectedItem() instanceof IProducer){
                actorCheckBox.setSelected(false);
                actorCheckBox.setVisible(false);
            }
            else{
                actorCheckBox.setVisible(true);
            }
        });

        addCastMember.setVisible(false);
        setUpdateProgramVisible(false);
        setEditCastVisible(false);
    }

    //Toggles visibility for update program functionality
    private void setUpdateProgramVisible(boolean state){
        programNameLabel.setVisible(state);
        saveUpdateButton.setVisible(state);
        updateProgramName.setVisible(state);
        updateReleaseYear.setVisible(state);
        releaseYearLabel.setVisible(state);
    }

    //Toggles visibility for edit cast functionality
    private void setEditCastVisible(boolean state){
        updateCastButton.setVisible(state);
        deleteCastButton.setVisible(state);
        editCastListView.setVisible(state);
        editCastLabel.setVisible(state);
        roleLabel.setVisible(state);
        editRoleField.setVisible(state);
        actorCheckBox.setVisible(state);
    }

    //Makes add member functionality visible
    @FXML
    void addMemberButtonHandler(ActionEvent event)  {
        addCastMember.setVisible(true);
        setUpdateProgramVisible(false);
        setEditCastVisible(false);
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

    //Deletes a program
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

    //Shows update program functionality
    @FXML
    void updateButtonHandler(ActionEvent event) {
        if(programsListViewController.getSelectedProgramInfo() != null) {
            setEditCastVisible(false);
            setUpdateProgramVisible(true);
            addCastMember.setVisible(false);

            this.oldName = programsListViewController.getSelectedProgramInfo().getName();
            updateProgramName.setText(programsListViewController.getSelectedProgramInfo().getName());
            updateReleaseYear.setText(programsListViewController.getSelectedProgramInfo().getYear());
        }
    }

    //Updates program
    public void saveUpdateButtonHandler(ActionEvent actionEvent) {
        programSystem.updateProgram(this.oldName, updateProgramName.getText(), updateReleaseYear.getText());
        programNameLabel.setVisible(false);
        saveUpdateButton.setVisible(false);
        updateProgramName.setVisible(false);
        updateReleaseYear.setVisible(false);
        releaseYearLabel.setVisible(false);
        programsListViewController.refreshListView();
    }

    //Refreshes List view of cast members from selected program (for update cast functionality)
    private void refreshEditListView(){
        if(programsListViewController.getSelectedProgramInfo() != null) {
            String programName = programsListViewController.getSelectedProgramInfo().getName();
            ObservableList<ICast> programCast = FXCollections.observableArrayList();
            programCast.setAll(programSystem.getProgram(programName).getCast());
            programCast.addAll(programSystem.getProgram(programName).getProducers());
            editCastListView.setItems(programCast);
        }
    }

    //Shows edit cast functionality
    @FXML
    void editCastButtonHandler(ActionEvent event) {
        setEditCastVisible(true);
        setUpdateProgramVisible(false);
        addCastMember.setVisible(false);

        refreshEditListView();
    }

    //Updates cast members role on program
    @FXML
    void updateCastHandler(ActionEvent event) {
        try{
            ICast cast = editCastListView.getSelectionModel().getSelectedItem();
            if(cast instanceof ICastMember) {
                ICastMember castMember = (ICastMember) cast;
                IRole irole;
                if(actorCheckBox.isSelected()){
                    irole = castMemberSystem.createActor(editRoleField.getText());
                }else{
                    irole = castMemberSystem.createRole(editRoleField.getText());
                }
                if (programSystem.updateCastMembersRoleOnProgram(programsListViewController.getSelectedProgramInfo(), castMember, irole)) {
                    System.out.println("Cast member updated");
                    refreshEditListView();
                } else {
                    System.out.println("Cast member not updated");
                }
            }else{
                IProducer producer = (IProducer) cast;
                if(programSystem.updateProducersRoleOnProgram(programsListViewController.getSelectedProgramInfo(), producer, editRoleField.getText())){
                    System.out.println(producer.getRole());
                    refreshEditListView();
                    System.out.println("Producer updated");
                }else{
                    System.out.println("Producer couldn't be updated");
                }
            }

        }catch (NullPointerException e){
            System.out.println("No cast member chosen");
        }
    }

    //Removes a cast member from the program
    @FXML
    void deleteCastHandler(ActionEvent event) {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Slet medvirkende");
            alert.setHeaderText("Du er ved at fjerne " + editCastListView.getSelectionModel().getSelectedItem().getName() + " fra " + programsListViewController.getSelectedProgramInfo().getName());
            alert.setContentText("Personen vil ikke længere stå som medvirkende på programmet!");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                if (editCastListView.getSelectionModel().getSelectedItem() instanceof ICastMember) {
                    if (programSystem.removeCastMemberFromProgram(programsListViewController.getSelectedProgramInfo(), (ICastMember) editCastListView.getSelectionModel().getSelectedItem())) {
                        System.out.println("Cast member removed");
                        refreshEditListView();
                    } else {
                        System.out.println("Cast member not removed");
                    }
                }
                else{
                    if(programSystem.removeProducerFromProgram(programsListViewController.getSelectedProgramInfo(), (IProducer) editCastListView.getSelectionModel().getSelectedItem())){
                        System.out.println("Producer removed");
                        refreshEditListView();
                    }else{
                        System.out.println("Producer couldn't be removed");
                    }
                }
            }
        }catch (NullPointerException e){
            System.out.println("No cast member chosen");
        }
    }
}

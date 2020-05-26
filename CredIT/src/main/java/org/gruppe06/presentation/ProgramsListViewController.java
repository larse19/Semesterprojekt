package org.gruppe06.presentation;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.gruppe06.domain.CredIT;
import org.gruppe06.interfaces.IProgramInfo;
import org.gruppe06.domain.ProgramSystem;

import java.net.URL;
import java.util.ResourceBundle;

public class ProgramsListViewController implements Initializable {

    @FXML
    private ListView<IProgramInfo> listOfPrograms;

    @FXML
    private TextField searchBar;

    private ProgramSystem programSystem;
    private ObservableList<IProgramInfo> programsObservableList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        programSystem = new ProgramSystem();
        programsObservableList = FXCollections.observableArrayList();
        if(CredIT.getCredITInstance().getUserRole() == 1) {
            programsObservableList.setAll(programSystem.getAllProgramsInfo(""));
        }else{
            programsObservableList.setAll(programSystem.getAllProducersProgramInfo(CredIT.getCredITInstance().getUsername(), ""));
        }
        listOfPrograms.setItems(programsObservableList);
    }

    //Returns the selected program
    public IProgramInfo getSelectedProgramInfo(){
        return listOfPrograms.getSelectionModel().getSelectedItem();
    }

    //Refreshes list view
    public void refreshListView(){
        if(CredIT.getCredITInstance().getUserRole() == 1) {
            programsObservableList.setAll(programSystem.getAllProgramsInfo(searchBar.getText()));
        }else{
            programsObservableList.setAll(programSystem.getAllProducersProgramInfo(CredIT.getCredITInstance().getUsername(), searchBar.getText()));
        }
    }

    //Searches for a program, and adds result to the list view
    @FXML
    void SearchProgram(ActionEvent event) {
        programsObservableList.setAll(programSystem.getAllProgramsInfo(searchBar.getText()));
    }
}
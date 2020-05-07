package org.gruppe06.presentation;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.gruppe06.domain.ProgramInfo;
import org.gruppe06.domain.ProgramSystem;

import java.net.URL;
import java.util.ResourceBundle;

public class ProgramsListViewController implements Initializable {

    @FXML
    private ListView<ProgramInfo> listOfPrograms;

    @FXML
    private TextField searchBar;

    @FXML
    private Button searchButton;

    private ProgramSystem programSystem;
    private ObservableList<ProgramInfo> programsObservableList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        programSystem = new ProgramSystem();
        programsObservableList = FXCollections.observableArrayList();
        programsObservableList.setAll(programSystem.getAllProgramsInfo());
        listOfPrograms.setItems(programsObservableList);
    }

    public ProgramInfo getSelectedProgramInfo(){
        return listOfPrograms.getSelectionModel().getSelectedItem();
    }

    public void refreshListView(){
        programsObservableList.setAll(programSystem.getAllProgramsInfo());
    }

    @FXML
    void SearchProgram(ActionEvent event) {

    }
}
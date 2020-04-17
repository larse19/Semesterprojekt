package org.gruppe06.presentation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;

public class producerFrontPageController {

    @FXML
    private Parent searchProgram;

    @FXML
    private SearchController searchController;

    @FXML
    private Button signOutButton;

    @FXML
    private Button createProgramButton;

    @FXML
    private Button editProgramButton;

    @FXML
    void createProgramHandler(ActionEvent event) throws IOException{
        App.setRoot("createProgram");
    }

    @FXML
    void editProgramHandler(ActionEvent event) throws IOException{
        App.setRoot("editProgram");
    }


    @FXML
    void signOutHandler(ActionEvent event) throws IOException {
        App.setRoot("frontPage");
    }

}


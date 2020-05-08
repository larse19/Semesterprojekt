package org.gruppe06.presentation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;

import java.io.IOException;

public class frontPageController {

    @FXML
    private Parent searchProgram;

    @FXML
    private SearchController searchProgramController;

    @FXML
    private Button loginButton;

    @FXML
    public void loginHandler(ActionEvent actionEvent) throws IOException {
        App.setRoot("loginPage");
    }


}


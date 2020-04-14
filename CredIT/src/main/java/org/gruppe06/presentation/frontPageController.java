package org.gruppe06.presentation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;

public class frontPageController {

    @FXML
    private Button loginButton;

    @FXML
    private TextArea searchResultField;

    @FXML
    private TextField searchField;

    @FXML
    private Button searchButton;

    @FXML
    void searchHandler(ActionEvent event) {
        System.out.println("Helo worl");
    }

    @FXML
    public void loginHandler(ActionEvent actionEvent) throws IOException {
        App.setRoot("loginPage");
    }


}


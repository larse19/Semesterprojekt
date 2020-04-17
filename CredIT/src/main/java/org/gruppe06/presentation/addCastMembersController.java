package org.gruppe06.presentation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

import java.io.IOException;

public class addCastMembersController {

    @FXML
    private RadioButton actorRadioButton;

    @FXML
    private TextField nameFIeld;

    @FXML
    private TextField roleField;

    @FXML
    private TextField numberOfEpisodesField;

    @FXML
    private Button addButton;

    @FXML
    private Button doneButton;

    @FXML
    private ListView<?> castMembersList;

    @FXML
    void addButtonHandler(ActionEvent event) {

    }

    @FXML
    void doneButtonHandler(ActionEvent event) throws IOException {
        App.setRoot("adminFrontPage");
    }

    @FXML
    public void actorHandler(ActionEvent actionEvent) {
    }
}

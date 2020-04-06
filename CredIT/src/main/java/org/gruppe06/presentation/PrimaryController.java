package org.gruppe06.presentation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.gruppe06.persistance.Postgres;

import java.net.URL;
import java.util.ResourceBundle;

public class PrimaryController implements Initializable {

    @FXML
    private Button primaryButton;

    @FXML
    private Label textLabel;

    @FXML
    void switchToSecondary(ActionEvent event) {
        textLabel.setText(Postgres.getPeople().toString());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}

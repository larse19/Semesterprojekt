package org.gruppe06.presentation;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.gruppe06.domain.UserSystem;
import org.gruppe06.interfaces.IPerson;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.Optional;
import java.util.ResourceBundle;

public class editUserController implements Initializable {

    @FXML
    private ListView<IPerson> usersList;

    @FXML
    private TextField roleField;

    @FXML
    private Label errorLabel;

    @FXML
    private Button backButton;

    @FXML
    private Button updateButton;

    @FXML
    private TextField nameField;

    @FXML
    private TextField passwordField;

    @FXML
    private Button deleteUserButton;

    @FXML
    private TextField userSearchField;

    @FXML
    private Button searchButton;

    private UserSystem userSystem;
    private ObservableList<IPerson> usersObservableList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userSystem = new UserSystem();
        usersObservableList = FXCollections.observableArrayList();
        usersObservableList.setAll(userSystem.getListOfUsers(""));
        usersList.setItems(usersObservableList);
    }

    @FXML
    void backButtonHandler(ActionEvent event) throws IOException {
        App.setRoot("adminFrontPage");
    }

    @FXML
    void deleteUserButtonHandler(ActionEvent event) {
        errorLabel.setText("");
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Slet bruger");
            alert.setHeaderText("Du er ved at slette " + usersList.getSelectionModel().getSelectedItem().getID());
            alert.setContentText("Denne person vil ikke længere kunne tilgå systemet");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                if(userSystem.deleteUser(usersList.getSelectionModel().getSelectedItem().getID())){
                    Alert okAlert = new Alert(Alert.AlertType.INFORMATION);
                    okAlert.setTitle("Bruger slettet");
                    okAlert.setHeaderText(usersList.getSelectionModel().getSelectedItem().getID() + " er blevet slettet.");
                    okAlert.show();
                }else{
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Bruger kunne ikke slettes");
                    errorAlert.setHeaderText("Der skete en fejl under sletningen af bruger: " + usersList.getSelectionModel().getSelectedItem().getID());
                }
            }
        }
        catch (NullPointerException ex){
            errorLabel.setText("Vælg en bruger");
        }
        searchButton.fire();
    }

    @FXML
    void searchButtonHandler(ActionEvent event) {
        usersObservableList.setAll(userSystem.getListOfUsers(userSearchField.getText()));
    }

    @FXML
    void updateButtonHandler(ActionEvent event) {

    }


}


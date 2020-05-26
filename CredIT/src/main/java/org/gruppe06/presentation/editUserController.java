package org.gruppe06.presentation;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.gruppe06.domain.CredIT;
import org.gruppe06.domain.PasswordAuthentication;
import org.gruppe06.domain.UserSystem;
import org.gruppe06.interfaces.IPerson;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class editUserController implements Initializable {

    @FXML
    private ListView<IPerson> usersList;

    @FXML
    private ComboBox<String> roleComboBox;

    @FXML
    private Label errorLabel;

    @FXML
    private TextField nameField;

    @FXML
    private TextField passwordField;

    @FXML
    private TextField userSearchField;

    @FXML
    private Button searchButton;

    private UserSystem userSystem;
    private ObservableList<IPerson> usersObservableList;
    private PasswordAuthentication passwordAuthentication;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userSystem = new UserSystem();
        passwordAuthentication = new PasswordAuthentication();
        usersObservableList = FXCollections.observableArrayList();
        usersObservableList.setAll(userSystem.getListOfUsers(""));
        usersList.setItems(usersObservableList);
        roleComboBox.getItems().clear();
        String[] roles = {"Administrator", "Producer"};
        roleComboBox.getItems().setAll(roles);
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

    //Deletes a user from the system
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
                if (userSystem.deleteUser(usersList.getSelectionModel().getSelectedItem().getID())) {
                    Alert okAlert = new Alert(Alert.AlertType.INFORMATION);
                    okAlert.setTitle("Bruger slettet");
                    okAlert.setHeaderText(usersList.getSelectionModel().getSelectedItem().getID() + " er blevet slettet.");
                    okAlert.show();
                } else {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Bruger kunne ikke slettes");
                    errorAlert.setHeaderText("Der skete en fejl under sletningen af bruger: " + usersList.getSelectionModel().getSelectedItem().getID());
                }
            }
        } catch (NullPointerException ex) {
            errorLabel.setText("Vælg en bruger");
        }
        searchButton.fire();
    }

    //Searches for a user, and shows result in list view
    @FXML
    void searchButtonHandler(ActionEvent event) {
        usersObservableList.setAll(userSystem.getListOfUsers(userSearchField.getText()));
    }

    //Updates user
    @FXML
    void updateButtonHandler(ActionEvent event) {
        if (usersList.getSelectionModel().getSelectedItem() != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Opdater bruger");
            alert.setHeaderText("Du er ved at opdatere " + usersList.getSelectionModel().getSelectedItem().getID());
            alert.setContentText("Sikre dig, at de nye oplysninger er korrekt!");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                IPerson user = usersList.getSelectionModel().getSelectedItem();
                if (roleComboBox.getSelectionModel().getSelectedItem() != null) {
                    if (roleComboBox.getSelectionModel().getSelectedItem().equals("Administrator")) {
                        if (!userSystem.updateUserRole(user.getID(), 1)) {
                            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                            errorAlert.setTitle("Brugerens rolle kunne ikke opdateres");
                            errorAlert.setHeaderText("Der skete en fejl under opdateringen af bruger: " + user.getID());
                        }
                    } else if (roleComboBox.getSelectionModel().getSelectedItem().equals("Producer")) {
                        if (!userSystem.updateUserRole(user.getID(), 2)) {
                            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                            errorAlert.setTitle("Brugerens rolle kunne ikke opdateres");
                            errorAlert.setHeaderText("Der skete en fejl under opdateringen af bruger: " + user.getID());
                        }
                    }
                }
                if (!nameField.getText().equals("")) {
                    if (!userSystem.updateUsersName(user.getID(), nameField.getText())) {
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.setTitle("Brugerens navn kunne ikke opdateres");
                        errorAlert.setHeaderText("Der skete en fejl under opdateringen af bruger: " + user.getID());
                    }
                    nameField.setText("");
                }
                if (!passwordField.getText().equals("")) {
                    if (!userSystem.updatePassword(user.getID(), passwordAuthentication.hash(passwordField.getText().toCharArray()))) {
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.setTitle("Brugerens password kunne ikke opdateres");
                        errorAlert.setHeaderText("Der skete en fejl under opdateringen af bruger: " + user.getID());
                    } else {
                        Alert okAlert = new Alert(Alert.AlertType.INFORMATION);
                        okAlert.setTitle("Password opdateret");
                        okAlert.setHeaderText(usersList.getSelectionModel().getSelectedItem().getID() + "s password er blevet opdateret.");
                        okAlert.show();
                    }
                }

            }
        }

        searchButton.fire();
    }

}


package org.gruppe06.presentation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.gruppe06.interfaces.ICastMember;
import org.gruppe06.interfaces.IProducer;
import org.gruppe06.interfaces.IProgram;
import org.gruppe06.domain.CredtIT;

import java.io.IOException;

public class frontPageController {

    @FXML
    private Parent searchProgram;

    @FXML
    private SearchController searchController;

    @FXML
    private Button loginButton;

    @FXML
    public void loginHandler(ActionEvent actionEvent) throws IOException {
        App.setRoot("loginPage");
    }


}


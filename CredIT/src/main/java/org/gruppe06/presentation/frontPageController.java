package org.gruppe06.presentation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import java.io.IOException;

public class frontPageController {

    @FXML
    public void loginHandler(ActionEvent actionEvent) throws IOException {
        App.setRoot("loginPage");
    }


}


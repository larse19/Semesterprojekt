package org.gruppe06.presentation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.gruppe06.domain.CastMember;
import org.gruppe06.domain.CredtIT;
import org.gruppe06.domain.Producer;
import org.gruppe06.domain.Program;

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
        Program program = CredtIT.getProgram(searchField.getText());
        String name = program.getName();
        StringBuilder producers = new StringBuilder();
        StringBuilder castMembers = new StringBuilder();

        for(Producer producer : program.getProducers()){
            producers.append(producer.getName()).append(" \n");
        }

        for(CastMember castMember : program.getCast()){
            castMembers.append(castMember.toString()).append("\n");
        }

        searchResultField.setText("Tilte:\n" + name + "\n\n");
        searchResultField.appendText("Producers:\n" + producers + "\n");
        searchResultField.appendText("Cast:\n" + castMembers);
    }

    @FXML
    public void loginHandler(ActionEvent actionEvent) throws IOException {
        App.setRoot("loginPage");
    }


}


package org.gruppe06.presentation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
    private Button loginButton;

    @FXML
    private TextArea searchResultField;

    @FXML
    private TextField searchField;

    @FXML
    private Button searchButton;

    @FXML
    void searchHandler(ActionEvent event) {
        IProgram program = CredtIT.getProgram(searchField.getText());
        String name = program.getName();
        StringBuilder producers = new StringBuilder();
        StringBuilder castMembers = new StringBuilder();

        for(IProducer producer : program.getProducers()){
            producers.append(producer.getName()).append(" \n");
        }

        for(ICastMember castMember : program.getCast()){
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


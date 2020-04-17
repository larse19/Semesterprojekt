package org.gruppe06.presentation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.gruppe06.domain.CredtIT;
import org.gruppe06.interfaces.ICastMember;
import org.gruppe06.interfaces.IProducer;
import org.gruppe06.interfaces.IProgram;

public class SearchController {

    @FXML
    private TextField searchTextField;

    @FXML
    private TextArea resultTextArea;

    @FXML
    void searchHandler(ActionEvent event) {
        IProgram program = CredtIT.getProgram(searchTextField.getText());
        String name = program.getName();
        StringBuilder producers = new StringBuilder();
        StringBuilder castMembers = new StringBuilder();

        for(IProducer producer : program.getProducers()){
            producers.append(producer.getName()).append(" \n");
        }

        for(ICastMember castMember : program.getCast()){
            castMembers.append(castMember.toString()).append("\n");
        }

        resultTextArea.setText("Tilte:\n" + name + "\n\n");
        resultTextArea.appendText("Producers:\n" + producers + "\n");
        resultTextArea.appendText("Cast:\n" + castMembers);
    }

}

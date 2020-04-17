package org.gruppe06.presentation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.gruppe06.domain.ProgramSystem;
import org.gruppe06.interfaces.ICastMember;
import org.gruppe06.interfaces.IProducer;
import org.gruppe06.interfaces.IProgram;

import java.net.URL;
import java.util.ResourceBundle;

public class SearchController implements Initializable {

    @FXML
    private TextField searchTextField;

    @FXML
    private TextArea resultTextArea;

    private ProgramSystem programSystem;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        programSystem = new ProgramSystem();
    }

    @FXML
    void searchHandler(ActionEvent event) {
        try {
            IProgram program = programSystem.getProgram(searchTextField.getText());
            String name = program.getName();
            StringBuilder producers = new StringBuilder();
            StringBuilder castMembers = new StringBuilder();

            for(IProducer producer : program.getProducers()){
                producers.append(producer.getName()).append(" \n");
            }

            for(ICastMember castMember : program.getCast()){
                castMembers.append(castMember.toString()).append("\n");
            }

            resultTextArea.setText("Title:\n" + name + "\n\n");
            resultTextArea.appendText("Producers:\n" + producers + "\n");
            resultTextArea.appendText("Cast:\n" + castMembers);
        }catch (NullPointerException e){
            resultTextArea.setText("Program not found");
        }

    }


}

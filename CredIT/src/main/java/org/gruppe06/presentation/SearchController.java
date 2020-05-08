package org.gruppe06.presentation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.gruppe06.domain.CastMemberSystem;
import org.gruppe06.domain.ProducerSystem;
import org.gruppe06.domain.ProgramSystem;
import org.gruppe06.domain.SearchSystem;
import org.gruppe06.interfaces.ICastMember;
import org.gruppe06.interfaces.IProducer;
import org.gruppe06.interfaces.IProgram;
import org.gruppe06.persistance.ProducerDataHandler;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SearchController implements Initializable {
    @FXML
    public Button searchButton;

    @FXML
    private TextArea resultTextArea;

    private ProgramSystem programSystem;


    @FXML
    public SearchSystem searchTextField;

    private ArrayList<String> programsList;
  
    private CastMemberSystem castMemberSystem;

    private ProducerSystem producerSystem;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        programSystem = new ProgramSystem();
        programsList = new ArrayList();
      
        for (String programName : programSystem.getListOfProgramNames()){
            programsList.add(programName);
        }
        searchTextField.getEntries().addAll(programsList);
        setEvent(searchTextField);
      
        castMemberSystem = new CastMemberSystem();
        producerSystem = new ProducerSystem();
    }

    @FXML
    void searchHandler(ActionEvent event) {
        try {
            resultTextArea.setText("");

            //Search program
            IProgram program = programSystem.getProgram(searchTextField.getText());
            String name = program.getName();
            System.out.println(name);
            String year = program.getYear();
            System.out.println(year);
            StringBuilder producers = new StringBuilder();
            StringBuilder castMembers = new StringBuilder();

            for(IProducer producer : program.getProducers()){
                producers.append(producer.getName()).append(" \n");
            }
            System.out.println(producers);

            for(ICastMember castMember : program.getCast()){
                castMembers.append(castMember.toString()).append("\n");
            }
            System.out.println(castMembers);

            resultTextArea.setText("Title:\n" + name + "\n\n");
            resultTextArea.appendText("Release Year:\n" + year + "\n\n");
            resultTextArea.appendText("Producers:\n" + producers + "\n");
            resultTextArea.appendText("Cast:\n" + castMembers);

//            if(resultTextArea.getText().equals("")){
//                ICastMember castMember = castMemberSystem.getCastMember(searchTextField.getText());
//                resultTextArea.setText(castMember.toString());
//            }

//            if(resultTextArea.getText().equals("")){
//                IProducer producer = producerSystem.getProducer(searchTextField.getText());
//                resultTextArea.setText(producer.toString());
//            }

        }catch (NullPointerException e){
            System.out.println("Nothing was found");
        }
    }

    private void setEvent(SearchSystem searchField){
        searchField.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
            if (ev.getCode() == KeyCode.ENTER){
                searchButton.fire();
            }
        });
    }
}

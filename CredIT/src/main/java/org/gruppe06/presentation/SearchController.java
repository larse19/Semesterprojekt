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
import org.gruppe06.domain.ProgramSystem;
import org.gruppe06.domain.SearchSystem;
import org.gruppe06.domain.SpellChecker;
import org.gruppe06.interfaces.ICastMember;
import org.gruppe06.interfaces.IProducer;
import org.gruppe06.interfaces.IProgram;
import org.gruppe06.persistance.ProducerDataHandler;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.stream.Stream;

public class SearchController implements Initializable {
    @FXML
    public Button searchButton;

    @FXML
    private TextArea resultTextArea;

    @FXML
    public SearchSystem searchTextField;

    private ProgramSystem programSystem;
    private ArrayList<String> programsList;
    private SpellChecker spellChecker;
    private CastMemberSystem castMemberSystem;

    // Der skal laves en ProducerSystem-klasse.
    // private ProducerSystem producerSystem;

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
        // producerSystem = new ProducerSystem();

        spellChecker = new SpellChecker();

        Stream.of(spellChecker.getDICTIONARY_VALUES().toLowerCase().split(",")).forEach((word) -> {
            spellChecker.getDictionary().compute(word, (k, v) -> v == null ? 1 : v + 1);
        });
    }

    @FXML
    void searchHandler(ActionEvent event) {
        try {
            IProgram program = programSystem.getProgram(searchTextField.getText());
            String name = program.getName();
            String year = program.getYear();
            StringBuilder producers = new StringBuilder();
            StringBuilder castMembers = new StringBuilder();

            for(IProducer producer : program.getProducers()){
                producers.append(producer.getName()).append(" \n");
            }

            for(ICastMember castMember : program.getCast()){
                castMembers.append(castMember.toString()).append("\n");
            }

            resultTextArea.setText("Title:\n" + name + "\n\n");
            resultTextArea.appendText("Release Year:\n" + year + "\n\n");
            resultTextArea.appendText("Producers:\n" + producers + "\n");
            resultTextArea.appendText("Cast:\n" + castMembers);

//            ICastMember castMember = castMemberSystem.getCastMember(searchTextField.getText());
//            String castMemberName = castMember.getName();
//            resultTextArea.setText("Person:\n" + castMemberName);

//            IProducer producer = producerSystem.getProducer(searchTextField.getText());
//            String producerName = producer.getName();
//            resultTextArea.setText("Person:\n" + producerName);

        } catch (NullPointerException e) {
            resultTextArea.setText("Mente du: " + spellChecker.correct(searchTextField.getText().replaceAll("\\s+", "")));
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

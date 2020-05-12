package org.gruppe06.presentation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.gruppe06.domain.CastMemberSystem;
import org.gruppe06.domain.ProducerSystem;
import org.gruppe06.domain.ProgramSystem;
import org.gruppe06.domain.SearchSystem;
import org.gruppe06.domain.SpellChecker;
import org.gruppe06.interfaces.ICastMember;
import org.gruppe06.interfaces.IProducer;
import org.gruppe06.interfaces.IProgram;
import org.gruppe06.interfaces.IProgramRole;
import org.gruppe06.persistance.ProducerDataHandler;
import org.gruppe06.persistance.ProgramRole;

import java.net.URL;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.stream.Stream;

public class SearchController implements Initializable {
    @FXML
    public Button searchButton;

    @FXML
    private VBox resultVBox;

    @FXML
    public SearchSystem searchTextField;

    private ProgramSystem programSystem;
    private SpellChecker spellChecker;
    private CastMemberSystem castMemberSystem;
    private ProducerSystem producerSystem;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        programSystem = new ProgramSystem();
        castMemberSystem = new CastMemberSystem();
        producerSystem = new ProducerSystem();

        ArrayList<String> databaseList = new ArrayList<>(programSystem.getListOfProgramNames());
        databaseList.addAll(castMemberSystem.getListOfCastMembers());
        databaseList.addAll(producerSystem.getListOfProducers());
        searchTextField.getEntries().addAll(databaseList);
        setEvent(searchTextField);

        spellChecker = new SpellChecker();

        Stream.of(spellChecker.getDICTIONARY_VALUES().toLowerCase().split(",")).forEach((word) -> {
            spellChecker.getDictionary().compute(word, (k, v) -> v == null ? 1 : v + 1);
        });

    }

    private Label programInformationLabel(IProgram program) {
        Label programLabel = new Label();
        programLabel.setText("Title:\n" + program.getName() + "\n\n" + "Release Year:\n" + program.getYear());
        programLabel.setOnMouseClicked(mouseEvent -> {
            searchTextField.setText(program.getName());
            searchButton.fire();
        });
        return programLabel;
    }

    private Label getCastMembersLabel(ICastMember castMember) {
        Label castMemberLabel = new Label();
        castMemberLabel.setText(castMember.toString());
        castMemberLabel.setOnMouseClicked(mouseEvent -> {
            searchTextField.setText(castMember.getName());
            searchButton.fire();
        });
        return castMemberLabel;
    }

    private void addProgramRoles(IProgramRole programRole) {
        Label label = new Label(programRole.toString());
        label.setOnMouseClicked(mouseEvent -> {
            searchTextField.setText(programRole.getProgramInfo().getName());
            searchButton.fire();
        });
        resultVBox.getChildren().add(label);
    }

    private void producerSearchResult(IProducer producer) {
        resultVBox.getChildren().add(new Label(producer.getName()));
        resultVBox.getChildren().add(new Text("\nProgrammer:"));
        if (producer.getProgramRoles() != null) {
            for (IProgramRole programRole : producer.getProgramRoles()) {
                addProgramRoles(programRole);
            }
        }
    }

    private void castMemberSearchResult(ICastMember castMember) {
        resultVBox.getChildren().add(new Label(castMember.getName()));
        resultVBox.getChildren().add(new Text("\nProgrammer:"));
        if (castMember.getProgramRoles() != null) {
            for (IProgramRole programRole : castMember.getProgramRoles()) {
                addProgramRoles(programRole);
            }
        }
    }

    private Label getProducersLabel(IProducer producer) {
        Label producerLabel = new Label();
        producerLabel.setText(producer.toString());
        producerLabel.setOnMouseClicked(mouseEvent -> {
            searchTextField.setText(producer.getName());
            searchButton.fire();
        });
        return producerLabel;
    }

    @FXML
    void searchHandler(ActionEvent event) {
        if (!searchTextField.getText().equals("")) {
            resultVBox.getChildren().clear();
            try {

                //Search program
                IProgram program = programSystem.getProgram(searchTextField.getText());
                resultVBox.getChildren().add(programInformationLabel(program));

                resultVBox.getChildren().add(new Text("\nProducers:"));
                for (IProducer producer : program.getProducers()) {
                    resultVBox.getChildren().add(getProducersLabel(producer));
                }

                resultVBox.getChildren().add(new Text("\nCast:"));
                for (ICastMember castMember : program.getCast()) {
                    resultVBox.getChildren().add(getCastMembersLabel(castMember));
                }

            } catch (NullPointerException e1) {
                try {
                    ICastMember castMember = castMemberSystem.getCastMember(searchTextField.getText());
                    castMemberSearchResult(castMember);
                } catch (NullPointerException e2) {
                    try {
                        IProducer producer = producerSystem.getProducer(searchTextField.getText());
                        producerSearchResult(producer);
                    } catch (NullPointerException e3) {
                        try {
                            Label didYouMeanLabel = new Label ("Mente du: " + spellChecker.correct(searchTextField.getText().replaceAll("\\s+", "")));
                            didYouMeanLabel.setOnMouseClicked(mouseEvent -> {
                                searchTextField.setText(spellChecker.correct(searchTextField.getText().replaceAll("\\s+", "")));
                                searchButton.fire();
                            });
                            resultVBox.getChildren().add(didYouMeanLabel);
                        } catch (NullPointerException e4) {
                            resultVBox.getChildren().add(new Label("No search result"));
                        }
                    }
                }
            }
        }
    }

    private void setEvent(SearchSystem searchField) {
        searchField.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
            if (ev.getCode() == KeyCode.ENTER) {
                searchButton.fire();
            }
        });
    }
}

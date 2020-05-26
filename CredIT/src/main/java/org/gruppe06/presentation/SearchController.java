package org.gruppe06.presentation;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
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
import org.gruppe06.persistance.CastMember;

import java.net.URL;
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


    //The two shapes makes the search glass
    @FXML
    public Rectangle rectangleIcon;

    @FXML
    public Circle circleIcon;


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

        changeIcon();

    }

    private Label programInformationLabel(IProgram program) {
        Label programLabel = new Label();
        programLabel.setText("\n" + "Title: " + "\n" + program.getName() + "\n\n" + "Release Year:\n" + program.getYear());
        programLabel.setOnMouseClicked(mouseEvent -> {
            searchTextField.setText(program.getName());
            searchButton.fire();
        });
        programLabel.hoverProperty().addListener((observable, oldValue, newValue) -> {
            if (programLabel.isHover())
            {
                programLabel.setStyle("-fx-underline: true; " + "-fx-font-weight: normal;" + "-fx-cursor: hand;");
            }
            else if (!programLabel.isHover()){
                programLabel.setStyle("-fx-underline: false;" + "-fx-font-weight: normal;");
            }
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
        castMemberLabel.hoverProperty().addListener((observable, oldValue, newValue) -> {
            if (castMemberLabel.isHover())
            {
                castMemberLabel.setStyle("-fx-underline: true; " + "-fx-font-weight: normal;" + "-fx-cursor: hand;");
            }
            else if (!castMemberLabel.isHover()){
                castMemberLabel.setStyle("-fx-underline: false;" + "-fx-font-weight: normal;");
            }
        });

        return castMemberLabel;
    }

    private void addProgramRoles(IProgramRole programRole) {
        Label label = new Label(programRole.toString());
        label.setOnMouseClicked(mouseEvent -> {
            searchTextField.setText(programRole.getProgramInfo().getName());
            searchButton.fire();
        });
        label.hoverProperty().addListener((observable, oldValue, newValue) -> {
            if (label.isHover())
            {
                label.setStyle("-fx-underline: true; " + "-fx-font-weight: normal;" + "-fx-cursor: hand;");
            }
            else if (!label.isHover()){
                label.setStyle("-fx-underline: false;" + "-fx-font-weight: normal;");
            }
        });
        resultVBox.getChildren().add(label);
    }

    private void producerSearchResult(IProducer producer) {
        Label nameLabel = new Label("\n" + producer.getName());
        Label programText = new Label("\nProgrammer:");
        resultVBox.getChildren().add(nameLabel);
        resultVBox.getChildren().add(programText);

        nameLabel.setStyle("-fx-font-size: 16px; " + "-fx-font-weight: bold;");
        programText.setStyle("-fx-font-size: 14px; " + "-fx-font-weight: bold;");

        if (producer.getProgramRoles() != null) {
            for (IProgramRole programRole : producer.getProgramRoles()) {
                addProgramRoles(programRole);
            }
        }
    }

    private void castMemberSearchResult(ICastMember castMember) {
        Label nameLabel = new Label("\n" + castMember.getName());
        Label programText = new Label("\nProgrammer:");
        resultVBox.getChildren().add(nameLabel);
        resultVBox.getChildren().add(programText);

        nameLabel.setStyle("-fx-font-size: 16px; " + "-fx-font-weight: bold;");
        programText.setStyle("-fx-font-size: 14px; " + "-fx-font-weight: bold;");

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
        producerLabel.hoverProperty().addListener((observable, oldValue, newValue) -> {
            if (producerLabel.isHover())
            {
                producerLabel.setStyle("-fx-underline: true; " + "-fx-font-weight: normal;" + "-fx-cursor: hand;");
            }
            else if (!producerLabel.isHover()){
                producerLabel.setStyle("-fx-underline: false;" + "-fx-font-weight: normal;");
            }
        });
        return producerLabel;
    }

    private void changeIcon(){
        searchTextField.focusedProperty().addListener((arg0, oldPropertyValue, newPropertyValue) -> {
            if (searchTextField.isFocused())
            {
                rectangleIcon.setStyle("-fx-opacity: 0");
                circleIcon.setStyle("-fx-opacity: 0");
            }
            else if(searchTextField.getText().isEmpty())
            {
                rectangleIcon.setStyle("-fx-opacity: 1");
                circleIcon.setStyle("-fx-opacity: 1");
            }
        });
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

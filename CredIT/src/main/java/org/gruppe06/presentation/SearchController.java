package org.gruppe06.presentation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import org.gruppe06.domain.CastMemberSystem;
import org.gruppe06.domain.ProducerSystem;
import org.gruppe06.domain.ProgramSystem;
import org.gruppe06.domain.SearchSystem;
import org.gruppe06.domain.SpellChecker;
import org.gruppe06.interfaces.ICastMember;
import org.gruppe06.interfaces.IProducer;
import org.gruppe06.interfaces.IProgram;
import org.gruppe06.interfaces.IProgramRole;

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
        databaseList.addAll(castMemberSystem.getListOfCastMemberNames());
        databaseList.addAll(producerSystem.getListOfProducerNames());
        searchTextField.getEntries().addAll(databaseList);
        setEvent(searchTextField);

        spellChecker = new SpellChecker();

        Stream.of(spellChecker.getDICTIONARY_VALUES().toLowerCase().split(",")).forEach((word) -> spellChecker.getDictionary().compute(word, (k, v) -> v == null ? 1 : v + 1));

        changeIcon();

    }

    //Adds labels to the VBox about the programs information (Used when a program is being searched)
    private void programInformationLabel(IProgram program) {
        Label titleText = new Label("\nTitel");
        titleText.setStyle("-fx-font-size: 14px; " + "-fx-font-weight: bold;");
        Label titleLabel = new Label(program.getName());
        Label yearText = new Label("\nUdgivelsesår:\n");
        yearText.setStyle("-fx-font-size: 14px; " + "-fx-font-weight: bold;");
        Label yearLabel = new Label(program.getYear());

        //Label programLabel = new Label();
        //programLabel.setText("\n" + "Title: " + "\n" + program.getName() + "\n\n" + "Release Year:\n" + program.getYear());
        titleLabel.setOnMouseClicked(mouseEvent -> {
            searchTextField.setText(program.getName());
            searchButton.fire();
        });
        titleLabel.hoverProperty().addListener((observable, oldValue, newValue) -> {
            if (titleLabel.isHover())
            {
                titleLabel.setStyle("-fx-underline: true; " + "-fx-font-weight: normal;" + "-fx-cursor: hand;");
            }
            else if (!titleLabel.isHover()){
                titleLabel.setStyle("-fx-underline: false;" + "-fx-font-weight: normal;");
            }
        });
        resultVBox.getChildren().addAll(titleText, titleLabel, yearText, yearLabel);
    }

    //Returns a label with information on a cast member (Used when a program is being searched)
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

    //Adds programRoles to the VBox (Used when a cast member is being searched)
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

    //Adds the search results the the VBox, when a producers is being searched
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

    //Adds the search results the the VBox, when a cast member is being searched
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

    //Returns a label with information on a producer (Used when a program is being searched)
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

    //Toggles the search icon, when the search field is focused
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

    //Searches for a program, cast member or producer
    @FXML
    void searchHandler(ActionEvent event) {
        if (!searchTextField.getText().equals("")) {
            resultVBox.getChildren().clear();
            try {

                //Search programs, if none are found, a null pointer exception is thrown by getProgram()
                IProgram program = programSystem.getProgram(searchTextField.getText());
                programInformationLabel(program);

                //Adds producers to search result
                Label producerText = new Label("\nProducere:");
                producerText.setStyle("-fx-font-size: 14px; " + "-fx-font-weight: bold;");
                resultVBox.getChildren().add(producerText);
                for (IProducer producer : program.getProducers()) {
                    resultVBox.getChildren().add(getProducersLabel(producer));
                }

                //Adds cast members to search result
                Label castMemberText = new Label("\nMedvirkende:");
                castMemberText.setStyle("-fx-font-size: 14px; " + "-fx-font-weight: bold;");
                resultVBox.getChildren().add(castMemberText);
                for (ICastMember castMember : program.getCast()) {
                    resultVBox.getChildren().add(getCastMembersLabel(castMember));
                }

            } catch (NullPointerException e1) {
                try {
                    //Searches for cast member, throws null pointer exception, if none are found
                    ICastMember castMember = castMemberSystem.getCastMember(searchTextField.getText());
                    castMemberSearchResult(castMember);
                } catch (NullPointerException e2) {
                    try {
                        //Searches for producer, throws null pointer exception, if none are found
                        IProducer producer = producerSystem.getProducer(searchTextField.getText());
                        producerSearchResult(producer);
                    } catch (NullPointerException e3) {
                        try {
                            //Tries to spellcheck the search keyword, if no search results are found, and makes a suggestion
                            Label didYouMeanLabel = new Label ("Mente du: " + spellChecker.correct(searchTextField.getText().replaceAll("\\s+", "")));
                            didYouMeanLabel.setOnMouseClicked(mouseEvent -> {
                                searchTextField.setText(spellChecker.correct(searchTextField.getText().replaceAll("\\s+", "")));
                                searchButton.fire();
                            });
                            resultVBox.getChildren().add(didYouMeanLabel);
                        } catch (NullPointerException e4) {
                            resultVBox.getChildren().add(new Label("Intet resultat"));
                        }
                    }
                }
            }
        }
    }

    //Eventlistener that pressed the search butten, when Enter is pressed
    private void setEvent(SearchSystem searchField) {
        searchField.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
            if (ev.getCode() == KeyCode.ENTER) {
                searchButton.fire();
            }
        });
    }
}

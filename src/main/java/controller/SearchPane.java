package controller;

import dictionary.Dictionary;
import dictionary.Word;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SearchPane extends Dictionary implements Initializable {

    // search
    @FXML
    private TextField searchTextField;

    @FXML
    private Button searchButton;

    @FXML
    private ListView<String> hintWordsView;

    // translation
    @FXML
    private Label spellingLabel;

    @FXML
    private Label pronunciationLabel;

    @FXML
    private Label meaningLabel;

    @FXML
    private Button playAudioButton;

    //@FXML
    //private ToggleButton favoriteButton;


    // edit
    @FXML
    private Button upgradeButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button addButton;



    @Override
    public void initialize(URL location, ResourceBundle resources) {

        dictionaryLoadFromFile();
        loadListView();
        stopShowingTranslation();
    }


    // searching methods
    @FXML
    private void clickSearch(ActionEvent event) {
        try {
            Word result = getChosenWord();
            showTranslation(result);

        } catch (Exception e) {
            System.out.println("Error! Can not display word!");
        }
    }

    @FXML
    private void clickWordInListView(MouseEvent event) {
        try {
            Word result = getChosenWord();
            showTranslation(result);
        } catch (Exception e) {
            System.out.println("Error! Can not display word!");
        }
    }


    @FXML
    private void handleChangeInput(KeyEvent event) {
        String inputSpelling = searchTextField.getText();
        System.out.println("Typed: " + inputSpelling);

        loadListView(inputSpelling);
    }


    public void loadListView(String spelling) {
        try {
            ArrayList<String> hintWordsSpelling = dictionary.searchHintSpelling(spelling);
            hintWordsView.getItems().clear();
            // if hint words exist
            if (hintWordsSpelling != null) {
                hintWordsView.getItems().addAll(hintWordsSpelling);
            }
        } catch (Exception e) {
            System.out.println("Error! Can not load list view.");
        }
    }

    public void loadListView() {
        try {
            ArrayList<Word> words = dictionary.getDictionaryListWords();
            ObservableList list = FXCollections.observableArrayList();
            for (Word word : words) {
                list.add(word.getSpelling());
            }

            hintWordsView.getItems().clear();
            hintWordsView.getItems().addAll(list);

        } catch (Exception e) {
            System.out.println("Error! Can not load list view.");
        }
    }


    public String getChosenWordSpelling() {
        try {
            return hintWordsView.getSelectionModel().getSelectedItem();
        } catch (NullPointerException exception) {
            System.out.println("You've chosen nothing.");
            return null;
        }

    }

    public Word getChosenWord() {
        try {

            Word chosenWord = this.dictionary.searchWord(getChosenWordSpelling());
            return chosenWord;

        } catch (NullPointerException exception) {
            System.out.println("You've chosen nothing.");
            return null;
        }
    }


    private ArrayList<String> convertWordsToSpellings(ArrayList<Word> listWords) {
        if (listWords == null) {
            return null;
        }
        ArrayList<String> listSpelling = new ArrayList<String>();
        for (Word word : listWords) {
            listSpelling.add(word.getSpelling());
        }
        return listSpelling;
    }



    // translating methods
    @FXML
    private void stopShowingTranslation() {
        spellingLabel.setText(null);
        pronunciationLabel.setText(null);
        meaningLabel.setText(null);
    }


    @FXML
    public void showTranslation(Word translatedWord) {
        try {

            spellingLabel.setText(translatedWord.getSpelling());
            pronunciationLabel.setText(translatedWord.getPronunciation());
            meaningLabel.setText(translatedWord.getMeaning());

        } catch (Exception e) {
            System.out.println("Error! Can not show translation.");
        }
    }

    @FXML
    private void clickPlayAudio(ActionEvent event) {
    }


    // edit methods
    @FXML
    void clickAddButton(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/displayAddWord.fxml"));
            Parent addWordScene = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Add new word");
            stage.setScene(new Scene(addWordScene));
            stage.show();
            //AddNewWordPane addNewWordPane = fxmlLoader.getController();
            //Word temp = addNewWordPane.getAddedWord();

        } catch (Exception e) {
            System.out.println("Error! Can not load new window.");
        }
    }

    @FXML
    void clickDeleteButton(ActionEvent event) {
        try {
            String spelling = this.getChosenWordSpelling();
            dictionary.deleteWord(spelling);
            loadListView();
            System.out.println("Successfully delete !.");
        } catch (Exception e) {
            System.out.println("Error! Can not delete word.");
        }
    }


    public void clickUpgradeButton(ActionEvent event) {
    }
}

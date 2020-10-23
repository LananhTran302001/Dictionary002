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

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SearchPane implements Initializable {

    private Dictionary dictionary = new Dictionary();

    private Word currentWord = new Word();

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

        //dictionary.dictionaryLoadFromFile();
        dictionary.dictionarySaveToFile();
        //loadListView();
        //stopShowingTranslation();
    }


    // searching methods
    @FXML
    private void clickSearch(ActionEvent event) {
        try {
            // get text from textField
            currentWord = this.dictionary.searchWord(searchTextField.getText());
            showTranslation(currentWord);

        } catch (Exception e) {
            System.out.println("Error! Can not search word!");
            AlertBox alert = new AlertBox();
            alert.display("Notification", "Error! Can not search word!");
        }
    }

    @FXML
    private void clickWordInListView(MouseEvent event) {
        try {
            // get text from listView
            currentWord = getChosenWord();
            showTranslation(currentWord);
        } catch (Exception e) {
            System.out.println("Error! Can not search word!");
            AlertBox alert = new AlertBox();
            alert.display("Notification", "Error! Can not search word!");
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
            AlertBox alert = new AlertBox();
            alert.display("Notification", "Error! Can not load list view!");
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
            AlertBox alert = new AlertBox();
            alert.display("Notification", "Error! Can not load list view!");
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

            return this.dictionary.searchWord(getChosenWordSpelling());

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
    private void stopShowingTranslation() {
        spellingLabel.setText(null);
        pronunciationLabel.setText(null);
        meaningLabel.setText(null);
    }


    public void showTranslation(Word translatedWord) {
        try {
            if (translatedWord == null || translatedWord.isEmpty()) {
                this.stopShowingTranslation();
                spellingLabel.setText("Not found word.");
            } else {
                spellingLabel.setText(translatedWord.getSpelling());
                pronunciationLabel.setText(translatedWord.getPronunciation());
                meaningLabel.setText(translatedWord.getMeaning());
            }

        } catch (Exception e) {
            System.out.println("Error! Can not show translation.");
        }
    }

    @FXML
    private void clickPlayAudio(ActionEvent event) {
    }


    // edit methods
    @FXML
    private void clickDeleteButton(ActionEvent event) {
        if (currentWord == null || currentWord.isEmpty()) {
            AlertBox alertNull = new AlertBox();
            alertNull.display("Notification", "There is no word to delete.");
            return;
        }
        try {
            openNewWindow("Delete", "/view/displayDeleteWord.fxml");
        } catch (Exception e) {
            System.out.println("Error! Can not delete word.");
            AlertBox alertNull = new AlertBox();
            alertNull.display("Notification", "Error! Can not delete word!");
        }
    }

    @FXML
    private void clickAddButton(ActionEvent event) {
        openNewWindow("Add", "/view/displayAddWord.fxml");
    }

    @FXML
    private void clickUpgradeButton(ActionEvent event) {
        openNewWindow("Upgrade", "/view/displayUpgradeWord.fxml");
    }

    private void openNewWindow(String title, String pathFxml) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(pathFxml));
            Parent scene = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(scene));
            stage.show();

        } catch (Exception e) {
            System.out.println("Error! Can not load new window.");
            AlertBox alert = new AlertBox();
            alert.display("Notification", "Error! Can not load new window!");
        }
    }

    // called back, newWord is legal for sure
    public void addWord(Word newWord) {
        dictionary.addWord(newWord);
        this.reset();
    }

    public void deleteWord() {
        dictionary.deleteWord(currentWord);
        this.reset();
    }

    public void upgradeWord(String oldWord, Word newWord) {
        dictionary.upgradeWord(oldWord, newWord);
        this.reset();
    }

    public void reset() {
        //dictionary.dictionarySaveToFile();
        //dictionary.dictionaryLoadFromFile("/data/u.txt");
        loadListView(searchTextField.getText());
    }
}

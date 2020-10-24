package controller;

import dictionary.Dictionary;
import dictionary.DictionaryManagement;
import dictionary.Word;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.Initializable;

import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import services.TextToSpeech;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class SearchPane implements Initializable {

    private Dictionary dictionary = new Dictionary();

    private DictionaryManagement favouriteWords = new DictionaryManagement();

    private Word currentWord = new Word();

    private boolean notifyAvailable = true;

    // menu dictionaries
    @FXML
    private MenuButton menuDictButton;

    @FXML
    private MenuItem evOption;

    @FXML
    private MenuItem veOption;

    @FXML
    private MenuItem favouriteOp;


    // search
    @FXML
    private TextField searchTextField;

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

    @FXML
    private Button ggTransButton;

    @FXML
    private ToggleButton favouriteButton;

    // edit
    @FXML
    private Button addButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button upgradeButton;

    // setting
    @FXML
    private ToggleButton notifySwitchButton;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        dictionary.dictionaryLoadFromFile();
        //dictionary.dictionarySaveToFile();
        menuDictButton.setText("English - Vietnamese");
        loadListView();
        stopShowingTranslation();
        notifySwitchButton.setSelected(false);
    }


    // -------------------------------- GET DICTIONARY ------------------------------------------------- //
    //                                                                                                   //
    // ------------------------------------------------------------------------------------------------- //

    @FXML
    private void clickEVOption(ActionEvent event) {
        if (menuDictButton.getText() != "English - Vietnamese") {
            menuDictButton.setText("English - Vietnamese");
            dictionary.dictionaryLoadFromFile();
            loadListView();
            stopShowingTranslation();
        }
    }

    @FXML
    private void clickVEOption(ActionEvent event) {
        if (menuDictButton.getText() != "Vietnamese - English") {
            menuDictButton.setText("Vietnamese - English");
            dictionary.dictionaryVELoadFromFile();
            loadListView();
            stopShowingTranslation();
        }
    }

    @FXML
    private void clickFavOption(ActionEvent event) {
        if (menuDictButton.getText() != "Favourite") {
            menuDictButton.setText("Favourite");
            dictionary.dictionaryLoadFromFile("/data/favdict.txt");
            loadListView();
            stopShowingTranslation();
        }
    }


    // -------------------------------- SEARCH METHODS ------------------------------------------------- //
    //                                                                                                   //
    // ------------------------------------------------------------------------------------------------- //


    /**
     * Execute when user clicks search button (search and display word has spelling
     * is same as the text typed in searchTextField).
     * @param event click search button.
     */
    @FXML
    private void clickSearch(ActionEvent event) {
        try {
            currentWord = this.dictionary.searchWord(searchTextField.getText());
            showTranslation(currentWord);

        } catch (Exception e) {
            createNotifyAlert("Error! Can not search word!");
        }
    }


    /**
     * Execute when user chooses an option in listView (search and display word has spelling
     * is same as that option).
     * @param event choose an option in listview.
     */
    @FXML
    private void clickWordInListView(MouseEvent event) {
        try {
            currentWord = getChosenWord();
            showTranslation(currentWord);
        } catch (Exception e) {
            createNotifyAlert("Error! Can not search word!");
        }
    }


    /**
     * Execute when user type a key on keyboard.
     * This method will call the method loadListView to reload the listView shows hint words.
     * @param event is a KeyEvent (key release).
     */
    @FXML
    private void handleChangeInput(KeyEvent event) {
        String inputSpelling = searchTextField.getText();
        System.out.println("Typed: " + inputSpelling);

        loadListView(inputSpelling);
    }


    /**
     * Find and reload the listView shows hint words of parameter spelling.
     * @param spelling from searchTextField.
     */
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

    /**
     * (If loadListView(String spelling) -> find and reload hint words).
     * Then this method: loadListView() executes when searchTextField is empty.
     * It will load all words of the dictionary to listView.
     */
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
            createNotifyAlert("Error! Can not load list view.");
        }
    }


    /**
     * Get string name of the option (spelling) is chosen in ListView.
     * @return string of that option.
     */
    public String getChosenWordSpelling() {
        try {
            return hintWordsView.getSelectionModel().getSelectedItem();
        } catch (NullPointerException exception) {
            System.out.println("You've chosen nothing.");
            return null;
        }

    }


    /**
     * Find word has spelling = getChosenWordSpelling() in the dictionary.
     * @return that word.
     */
    public Word getChosenWord() {
        try {

            return this.dictionary.searchWord(getChosenWordSpelling());

        } catch (NullPointerException exception) {
            System.out.println("You've chosen nothing.");
            return null;
        }
    }


    /**
     * Convert a list of words to a list of spelling.
     * @param listWords is input list words.
     * @return list of spelling = ArrayList <String> listSpelling.
     */
    private ArrayList<String> convertWordsToSpellings(ArrayList<Word> listWords) {
        if (listWords == null) {
            return null;
        }
        ArrayList<String> listSpelling = new ArrayList<>();
        for (Word word : listWords) {
            listSpelling.add(word.getSpelling());
        }
        return listSpelling;
    }



    // -------------------------------- TRANSLATION METHODS -------------------------------------------- //
    //                                                                                                   //
    // ------------------------------------------------------------------------------------------------- //

    /**
     * Clear translation.
     */
    private void stopShowingTranslation() {
        spellingLabel.setText(null);
        pronunciationLabel.setText(null);
        meaningLabel.setText(null);
        playAudioButton.setVisible(false);
        favouriteButton.setVisible(false);
        ggTransButton.setVisible(false);
        deleteButton.setDisable(true);
        upgradeButton.setDisable(true);

    }


    /**
     * Display translation.
     * This method is called when user click an option in listView or click searchButton.
     * @param translatedWord is a word input.
     */
    public void showTranslation(Word translatedWord) {
        try {
            if (translatedWord == null || translatedWord.isEmpty()) {
                this.stopShowingTranslation();
                spellingLabel.setText("Not found word.");
            } else {
                spellingLabel.setText(translatedWord.getSpelling());
                pronunciationLabel.setText(translatedWord.getPronunciation());
                meaningLabel.setText(translatedWord.getMeaning());
                playAudioButton.setVisible(true);
                favouriteButton.setVisible(true);
                favouriteButton.setSelected(translatedWord.isFav());
                ggTransButton.setVisible(true);
                deleteButton.setDisable(false);
                upgradeButton.setDisable(false);
            }

        } catch (Exception e) {
            createNotifyAlert("Error! Can not show translation!");
        }
    }


    /**
     * Play audio of the word is being translated.
     * @param event when user click audioButton.
     */
    @FXML
    private void clickPlayAudio(ActionEvent event) {
        if (menuDictButton.getText() == "Vietnamese - English") {
            TextToSpeech audio = new TextToSpeech(currentWord.getMeaning());
            audio.read();
        } else {
            TextToSpeech audio = new TextToSpeech(currentWord.getSpelling());
            audio.read();
        }
    }


    /**
     * Add or delete the word being translated to/from favourite words.
     * @param event when user click favouriteButton
     */
    @FXML
    private void clickFavour(MouseEvent event) {
        if (favouriteButton.isSelected()) {
            currentWord.setFav(true);
            favouriteWords.addWord(currentWord);
            createNotifyAlert("*Added word: \n\t" + currentWord.getSpelling() + "\nTo favourite list.");
        } else {
            favouriteWords.deleteWord(currentWord);
            currentWord.setFav(false);
            createNotifyAlert("*Removed word: \n\t" + currentWord.getSpelling() + "\nFrom favourite list.");
        }
    }

    /**
     * Using google API to search word.
     * @param event when user click ggTransButton.
     */
    @FXML
    private void clickGGTrans(ActionEvent event) {

    }

    // ----------------------------------- EDITING METHODS --------------------------------------------- //
    //                                                                                                   //
    // ------------------------------------------------------------------------------------------------- //



    @FXML
    private void clickDeleteButton(ActionEvent event) {
        if (currentWord == null || currentWord.isEmpty()) {
            createNotifyAlert("There is no such word to delete!");
            return;
        }
        try {
            boolean del = new DeleteWordPane().acceptDelete(currentWord);
            if (del) {
                this.dictionary.deleteWord(currentWord);
                loadListView();
                createNotifyAlert("Deleted successfully.");
            }
        } catch (Exception e) {
            createNotifyAlert("Error! Can not delete word!");
        }
    }



    @FXML
    private void clickAddButton(ActionEvent event) {
        try {

            Word newWord = new AddNewWordPane().getAcceptWord();

            if (newWord != null && !newWord.isEmpty()) {
                if (this.dictionary.canAddWord(newWord)) {
                    loadListView();
                    createNotifyAlert("Added successfully.");
                } else {
                    createNotifyAlert("Can not Add! This word has been in the dictionary.");
                }
            }

        } catch (Exception e) {
            createNotifyAlert("Error! Can not add word!");
        }
    }

    @FXML
    private void clickUpgradeButton(ActionEvent event) {
        try {

            Word newWord = new UpgradeWordPane().getAcceptWord(currentWord);

            if (newWord != null && !newWord.isEmpty()) {

                if (currentWord.equalsTo(newWord)) {
                    createNotifyAlert("Word was not changed!");
                    return;
                }

                this.dictionary.upgradeWord(currentWord.getSpelling(), newWord);
                reset();
                createNotifyAlert("Upgrade successfully.");
            }
        } catch (Exception e) {
            createNotifyAlert("Error! Can not upgrade word!");
        }
    }

    public void reset() {
        dictionary.dictionarySaveToFile();
        //dictionary.dictionaryLoadFromFile();
        loadListView();
    }

    // ----------------------------------- SETTING METHODS --------------------------------------------- //
    //                                                                                                   //
    // ------------------------------------------------------------------------------------------------- //




    @FXML
    private void clickSaveButton(ActionEvent event) {
        this.dictionary.dictionarySaveToFile();
    }

    @FXML
    private void switchNotification(MouseEvent event) {
        notifyAvailable = !notifySwitchButton.isSelected();
    }

    public void createNotifyAlert(String message) {
        if (notifyAvailable) {
            AlertBox alert = new AlertBox();
            alert.display("Notification", message);
        }
        System.out.println(message);
    }
}

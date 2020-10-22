package controller;

import dictionary.Word;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class AddNewWordPane implements Initializable {

    @FXML
    private TextField spellingTextField;

    @FXML
    private TextField pronounceTextField;

    @FXML
    private TextField soundLinkTextField;

    @FXML
    private Button loadLinkButton;

    @FXML
    private TextArea meaningTextArea;

    @FXML
    private Button addingButton;

    @FXML
    private Button cancelButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/view/displayAddWord.fxml"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clear() {
        spellingTextField.clear();
        pronounceTextField.clear();
        meaningTextArea.clear();
    }

    @FXML
    public void addWord(ActionEvent event) {
        try {
            Word newWord = new Word(
                    spellingTextField.getText(),
                    pronounceTextField.getText(),
                    meaningTextArea.getText());

            if (newWord.isEmpty()) {
                return;
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/displaySearch.fxml"));
            Parent root = (Parent) loader.load();
            SearchPane searchManagement = loader.getController();
            searchManagement.dictionary.addWord(newWord);
            searchManagement.loadListView();

        } catch (Exception e) {
            System.out.println("Can not add word!");
        }

        clear();
    }

    @FXML
    public void cancel(ActionEvent event) {
        if (event.getSource() == cancelButton) {
            spellingTextField.clear();
            pronounceTextField.clear();
            meaningTextArea.clear();
        }
    }

    @FXML
    void upSoundFile(ActionEvent event) {

    }


}

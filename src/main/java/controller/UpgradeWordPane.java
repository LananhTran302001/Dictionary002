package controller;

import dictionary.Word;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class UpgradeWordPane {

    @FXML
    private TextField spellingTextField;

    @FXML
    private TextField pronunciationTextField;

    @FXML
    private TextField meaningTextArea;

    @FXML
    private Button upgradeButton;

    @FXML
    private Button cancelButton;

    public Word getUpgradeNewWord() {
        Word word = new Word();
        word.setSpelling(spellingTextField.getText());
        word.setPronunciation(pronunciationTextField.getText());
        word.setMeaning(meaningTextArea.getText());
        return word;
    }

    @FXML
    private void clickLoadAudioFile(ActionEvent event) {
    }


    @FXML
    private void clickUpgradeButton(ActionEvent event) {

    }


    @FXML
    private void clickCancel(ActionEvent event) {
        if (event.getSource() == cancelButton) {
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.close();
        }
    }
}

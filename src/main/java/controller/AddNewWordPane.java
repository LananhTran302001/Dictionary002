package controller;

import dictionary.Word;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class AddNewWordPane {

    @FXML
    private TextField spellingTextField;

    @FXML
    private TextField pronounceTextField;

    @FXML
    private TextArea meaningTextArea;

    @FXML
    private TextField soundLinkTextField;

    @FXML
    private Button loadLinkButton;

    @FXML
    private Button addingButton;

    @FXML
    private Button cancelButton;


    @FXML
    private void addWord(ActionEvent event) {
        try {
            Word newWord = new Word(
                    spellingTextField.getText(),
                    pronounceTextField.getText(),
                    meaningTextArea.getText());

            if (newWord.isEmpty()) {
                AlertBox alert = new AlertBox();
                alert.display("Notification", "Word is empty! Can not be added!");
                System.out.println("Word is empty! Can not be added!");
                return;
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/displaySearch.fxml"));
            Parent searchScene = loader.load();
            SearchPane searchManagement = loader.getController();
            searchManagement.addWord(newWord);
            //AlertBox alert = new AlertBox();
            //alert.display("Notification", "Add word " + newWord.getSpelling() + " successfully.", "OK");


        } catch (Exception e) {
            System.out.println("Can not add word!");
            AlertBox alert = new AlertBox();
            alert.display("Notification", "Error! Can not add new word!");
            System.out.println("Error! Can not add new word!");
        }
        this.clear();
    }


    @FXML
    public void cancel(ActionEvent event) {
        if (event.getSource() == cancelButton) {
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.close();
        }
    }

    @FXML
    void upSoundFile(ActionEvent event) {

    }

    public void clear() {
        spellingTextField.clear();
        pronounceTextField.clear();
        meaningTextArea.clear();
    }

}

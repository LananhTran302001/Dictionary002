package controller;

import dictionary.Word;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class DeleteWordPane {

    @FXML
    private Label spellingLabel;

    @FXML
    private Label pronunciationLabel;

    @FXML
    private Label meaningLabel;


    @FXML
    private Button cancelButton;

    @FXML
    private Button deleteButton;

    public void clear() {
        spellingLabel.setText(null);
        pronunciationLabel.setText(null);
        meaningLabel.setText(null);
    }

    public void setText(Word word) {
        this.clear();
        spellingLabel.setText(word.getSpelling());
        pronunciationLabel.setText(word.getPronunciation());
        meaningLabel.setText(word.getMeaning());
    }

    @FXML
    private void clickCancelButton(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void clickDeleteButton(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/displaySearch.fxml"));
        Parent scene = (Parent) fxmlLoader.load();
        SearchPane searchManagement = fxmlLoader.getController();
        searchManagement.deleteWord();
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

}

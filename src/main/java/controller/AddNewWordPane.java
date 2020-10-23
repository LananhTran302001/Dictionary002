package controller;

import dictionary.Word;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;



public class AddNewWordPane {

    Word newAddedWord = null;

    public Word getAcceptWord() {

        newAddedWord = null;

        final Stage addWindow = new Stage();

        addWindow.initModality(Modality.APPLICATION_MODAL);
        addWindow.setTitle("Add word");
        addWindow.setMinWidth(300);

        Label label = new Label("ADD NEW WORD");
        label.setStyle("-fx-font: 18px System");

        Label ques = new Label("Do you want to add this word ?\n");

        TextField newSpelling = new TextField();
        newSpelling.setPromptText("Spelling");

        TextField newPronunciation = new TextField();
        newPronunciation.setPromptText("Pronunciation");

        TextField newMeaning = new TextField();
        newMeaning.setPromptText("Meaning");


        Button addButton = new Button("Add");
        addButton.setOnAction(event -> {
            newAddedWord = new Word(newSpelling.getText(),
                                    newPronunciation.getText(),
                                    newMeaning.getText());
            if (newAddedWord.isEmpty()) {
                AlertBox alert = new AlertBox();
                alert.display("Notification","Word is empty! Can not add!");
            }
            addWindow.close();
        });

        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(event -> addWindow.close());

        HBox buttons = new HBox(10);
        buttons.setAlignment(Pos.CENTER);
        buttons.getChildren().addAll(cancelButton, addButton);

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, ques, newSpelling, newPronunciation, newMeaning, buttons);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(15, 20, 15, 20));

        Scene scene = new Scene(layout);
        scene.getStylesheets().add("/style/editPanesStyle.css");
        addWindow.setScene(scene);
        addWindow.showAndWait();

        return newAddedWord;
    }
}

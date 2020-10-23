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



public class UpgradeWordPane {

    Word upgradedWord = null;

    public Word getAcceptWord(Word currentWord) {

        upgradedWord = null;

        final Stage addWindow = new Stage();

        addWindow.initModality(Modality.APPLICATION_MODAL);
        addWindow.setTitle("Upgrade word");
        addWindow.setMinWidth(300);

        Label label = new Label("UPGRADE WORD");
        label.setStyle("-fx-font: 18px System");

        Label ques = new Label("Do you want to upgrade this word ?\n");

        TextField newSpelling = new TextField();
        newSpelling.setPromptText("Spelling");
        newSpelling.setText(currentWord.getSpelling());

        TextField newPronunciation = new TextField();
        newPronunciation.setPromptText("Pronunciation");
        newPronunciation.setText(currentWord.getPronunciation());

        TextField newMeaning = new TextField();
        newMeaning.setPromptText("Meaning");
        newMeaning.setText(currentWord.getMeaning());


        Button upgradeButton = new Button("Upgrade");
        upgradeButton.setOnAction(event -> {
            upgradedWord = new Word(newSpelling.getText(),
                                    newPronunciation.getText(),
                                    newMeaning.getText());
            if (upgradedWord.isEmpty()) {
                AlertBox alert = new AlertBox();
                alert.display("Notification","Word is empty! Can not upgrade!");
            }
            addWindow.close();
        });

        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(event -> addWindow.close());

        HBox buttons = new HBox(10);
        buttons.setAlignment(Pos.CENTER);
        buttons.getChildren().addAll(cancelButton, upgradeButton);

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, ques, newSpelling, newPronunciation, newMeaning, buttons);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(15, 20, 15, 20));

        Scene scene = new Scene(layout);
        addWindow.setScene(scene);
        addWindow.showAndWait();

        return upgradedWord;
    }
}

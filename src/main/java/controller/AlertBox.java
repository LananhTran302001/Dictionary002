package controller;

import dictionary.Word;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;



public class AlertBox {


    public void display(String title, String message, String button) {
        final Stage alertWindow = new Stage();

        alertWindow.initModality(Modality.APPLICATION_MODAL);
        alertWindow.setTitle(title);
        alertWindow.setMinWidth(300);

        Label label = new Label();
        label.setText(message);

        Button closeButton = new Button(button);
        closeButton.setOnAction(event -> alertWindow.close());


        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, closeButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(10, 10, 10, 10));

        Scene scene = new Scene(layout);
        alertWindow.setScene(scene);
        alertWindow.showAndWait();
    }


    public void display(String title, String message) {
        this.display(title, message, "Close");
    }





    public void display(String title, String message, String button1, String button2) {

        boolean leftOption = false;
        final Stage alertWindow = new Stage();

        alertWindow.initModality(Modality.APPLICATION_MODAL);
        alertWindow.setTitle(title);
        alertWindow.setMinWidth(300);

        Label label = new Label();
        label.setText(message);


        Button leftButton = new Button(button1);
        leftButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                alertWindow.close();
            }
        });

        Button rightButton = new Button(button2);
        rightButton.setOnAction(event -> alertWindow.close());

        HBox buttons = new HBox(10);
        buttons.getChildren().addAll(leftButton, rightButton);
        buttons.setAlignment(Pos.CENTER);

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, buttons);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(10, 10, 10, 10));

        Scene scene = new Scene(layout);
        alertWindow.setScene(scene);
        alertWindow.showAndWait();

    }


    public void displayDeleteAlert(String title, String message, Word word) {
        final Stage alertWindow = new Stage();

        alertWindow.initModality(Modality.APPLICATION_MODAL);
        alertWindow.setTitle(title);
        alertWindow.setMinWidth(300);

        Label label = new Label();
        label.setText(message);

        Label spellingLabel = new Label();
        spellingLabel.setText(word.getSpelling());

        Label pronunciationLabel = new Label();
        pronunciationLabel.setText(word.getPronunciation());

        Label meaningLabel = new Label();
        meaningLabel.setText(word.getMeaning());

        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(event -> alertWindow.close());

        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(event -> alertWindow.close());

        HBox buttons = new HBox(10);
        buttons.getChildren().addAll(cancelButton, deleteButton);
        buttons.setAlignment(Pos.CENTER);


        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, spellingLabel, pronunciationLabel, meaningLabel, buttons);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(10, 10, 10, 10));

        Scene scene = new Scene(layout);
        alertWindow.setScene(scene);
        alertWindow.showAndWait();
    }

}

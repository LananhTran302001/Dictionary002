package controller;


import dictionary.Word;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class DeleteWordPane {

    boolean deleteAccept = false;

    public boolean acceptDelete(Word word) {
        deleteAccept = false;
        final Stage deleteWindow = new Stage();

        deleteWindow.initModality(Modality.APPLICATION_MODAL);
        deleteWindow.setTitle("Confirm delete");
        deleteWindow.setMinWidth(300);

        Label label = new Label("DELETE WORD");
        label.setStyle("-fx-font: 18px System");

        Label ques = new Label("Are you sure to delete this word ?\n" + word.toString());

        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(event -> deleteWindow.close());

        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(event -> {
            deleteAccept = true;
            deleteWindow.close();
        });

        HBox buttons = new HBox(10);
        buttons.setAlignment(Pos.CENTER);
        buttons.getChildren().addAll(cancelButton, deleteButton);

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, ques, buttons);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(15, 20, 15, 20));

        Scene scene = new Scene(layout);
        scene.getStylesheets().add("/style/editPanesStyle.css");
        deleteWindow.setScene(scene);
        deleteWindow.showAndWait();

        return deleteAccept;
    }
}

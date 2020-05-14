package com.codecool.snake;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;

public class PopupScreen {
    public static void display(String message)
    {
        Stage popupwindow = new Stage();

        popupwindow.initModality(Modality.APPLICATION_MODAL);
        popupwindow.setTitle("Game Over");


        Label label1= new Label(message);


        Button buttonRestart= new Button("Restart - BETA");
        Button buttonExit= new Button("Exit game");



        buttonRestart.setOnAction(e -> System.out.println("Restart"));
        buttonExit.setOnAction(e -> Platform.exit());



        VBox layout= new VBox(10);


        layout.getChildren().addAll(label1, buttonRestart, buttonExit);

        layout.setAlignment(Pos.CENTER);

        Scene scene1= new Scene(layout, 300, 250);

        popupwindow.setScene(scene1);

        popupwindow.show();


    }

}

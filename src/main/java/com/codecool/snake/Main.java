package com.codecool.snake;

import com.codecool.snake.entities.snakes.Snake;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        BorderPane bp = new BorderPane();
        Game game = new Game();
        Scene mainScene = new Scene(bp, Globals.WINDOW_WIDTH, Globals.WINDOW_HEIGHT);

        primaryStage.setTitle("Snake Game");
        primaryStage.setScene(mainScene);
        Pane p =new Pane();
        p.setPrefWidth(50);
        p.setPrefHeight(50);
        p.setBackground(new Background(new BackgroundFill(Color.rgb(255,255,153), CornerRadii.EMPTY, Insets.EMPTY)));
        bp.setCenter(game);
        bp.setTop(p);
        Rectangle healthbar1 = new Rectangle();
        healthbar1.setX(10);
        healthbar1.setY(10);
        healthbar1.setWidth(200);
        healthbar1.setHeight(30);
//        healthbar1.setStyle("-fx-border-style: solid; -fx-border-width: 5; -fx-border-color: red;");
        Rectangle healthbar2 = new Rectangle();
        healthbar2.setX(15);
        healthbar2.setY(15);
        healthbar2.setWidth(2*70); //!!!!!!!!!!!!!!!!!!!!!!!!
        healthbar2.setHeight(20);
        healthbar2.setFill(Color.BLUE);
        p.getChildren().addAll(healthbar1, healthbar2);

        primaryStage.show();



        game.start();
    }

    @Override
    public void stop() throws Exception {
        System.out.println("Exiting..");
    }
}

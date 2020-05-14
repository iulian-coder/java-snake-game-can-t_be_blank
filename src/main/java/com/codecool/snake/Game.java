package com.codecool.snake;

import com.codecool.snake.entities.enemies.ChasingEnemy;
import com.codecool.snake.entities.enemies.CirclingEnemy;
import com.codecool.snake.entities.enemies.SimpleEnemy;
import com.codecool.snake.entities.powerups.DoubleLengthPowerUp;
import com.codecool.snake.entities.powerups.SimplePowerUp;
import com.codecool.snake.entities.powerups.SpeedBoostPowerUp;
import com.codecool.snake.entities.snakes.Snake;
import com.codecool.snake.eventhandler.ClickRestartHandler;
import com.codecool.snake.eventhandler.InputHandler;

import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;


public class Game extends Pane {
    private List<Snake> snakes = new ArrayList<>();
    private GameTimer gameTimer = new GameTimer();

    private int numberOfPlayers = 2;


    Rectangle healthBarSnake = new Rectangle();


    public Game() {
        Globals.getInstance().game = this;
        Globals.getInstance().display = new Display(this);
        Globals.getInstance().setupResources();
        init();
    }

    public Rectangle getHealthBarSnake() {
        return healthBarSnake;
    }

    public int getSnakeHealth() {
        return snake.getHealth();
    }

    public void init() {
        spawnSnake();
        spawnEnemies(4);
        spawnPowerUps(4);

        GameLoop gameLoop = new GameLoop(snakes);
        Globals.getInstance().setGameLoop(gameLoop);
        gameTimer.setup(gameLoop::step);
        gameTimer.play();
        Rectangle healthBarBackGround = new Rectangle();
        healthBarBackGround.setX(10);
        healthBarBackGround.setY(10);
        healthBarBackGround.setWidth(210);
        healthBarBackGround.setHeight(30);
        healthBarSnake.setX(15);
        healthBarSnake.setY(15);
        healthBarSnake.setWidth(2*snake.getHealth());

        healthBarSnake.setHeight(20);
        healthBarSnake.setFill(Color.BLUE);
        getChildren().addAll(healthBarBackGround, healthBarSnake);
    }

    public void start() {
        setupInputHandling();
        setRestartButton(Globals.getInstance().game);
        Globals.getInstance().startGame();
    }

    private void spawnSnake()
    {
        switch (numberOfPlayers){
            default:
                snakes.add(new Snake(new Point2D(500, 500), 0, "SnakeHead"));
                break;
            case 2:
                snakes.add(new Snake(new Point2D(500, 500), 0, "SnakeHead"));
                snakes.add(new Snake(new Point2D(400, 400), 1, "SnakeHead2"));
                break;
        }

    }

    public List<Snake> getSnakes() {
        return snakes;
    }

    private void spawnEnemies(int numberOfEnemies) {
        new SimpleEnemy();
        for(int i = 0; i < numberOfEnemies/2; ++i) new ChasingEnemy();
        for(int i = 0; i < numberOfEnemies/2; ++i) new CirclingEnemy();
    }

    private void spawnPowerUps(int numberOfPowerUps) {
        for(int i = 0; i < numberOfPowerUps; ++i) new SimplePowerUp();
        for (int i = 0; i < numberOfPowerUps/2; i++) new DoubleLengthPowerUp();
        for (int i = 0; i <numberOfPowerUps/4 ; i++) new SpeedBoostPowerUp();
    }

    private void setupInputHandling() {
        Scene scene = getScene();
        scene.setOnKeyPressed(event -> InputHandler.getInstance().setKeyPressed(event.getCode()));
        scene.setOnKeyReleased(event -> InputHandler.getInstance().setKeyReleased(event.getCode()));
    }


    public void checkGameOver(){
        int counter = numberOfPlayers;
        for (Snake snake : snakes){
            if (snake.getHead().isOutOfBounds() || snake.getHealth() <= 0){
                snake.setHealth(0);
                counter -=1;
            }
        }
        if (counter == 0){
            Globals.getInstance().stopGame();
            PopupScreen.display("Snake 1 Score " + snakes.get(0).getLengthBodyPartsTotal() +" ft." +"\n"+
                                "Snake 2 Score " + snakes.get(1).getLengthBodyPartsTotal() + " ft.");
        }
    }

    public void setRestartButton (Game game) {
        Button button = new Button("Restart");
        // CSS inline
        button.setStyle("-fx-background-color: yellow; -fx-text-fill: aqua");
        game.getChildren().add(button);
        ClickRestartHandler clickHandler = new ClickRestartHandler();
        button.setOnAction(clickHandler);
        game.requestFocus();
    }


}

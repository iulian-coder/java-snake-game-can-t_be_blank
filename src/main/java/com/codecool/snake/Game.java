package com.codecool.snake;

import com.codecool.snake.entities.enemies.MouseEnemy;
import com.codecool.snake.entities.enemies.SimpleEnemy;
import com.codecool.snake.entities.powerups.SimplePowerUp;
import com.codecool.snake.entities.powerups.TreePowerUp;
import com.codecool.snake.entities.snakes.Snake;
import com.codecool.snake.eventhandler.InputHandler;

import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;


public class Game extends Pane {
    private Snake snake = null;
    private GameTimer gameTimer = new GameTimer();


    public Game() {
        Globals.getInstance().game = this;
        Globals.getInstance().display = new Display(this);
        Globals.getInstance().setupResources();

        init();
    }

    public void init() {
        spawnSnake();
        spawnEnemies(4, 2);
        spawnPowerUps(4, 1);

        GameLoop gameLoop = new GameLoop(snake);
        Globals.getInstance().setGameLoop(gameLoop);
        gameTimer.setup(gameLoop::step);
        gameTimer.play();
    }

    public void start() {
        setupInputHandling();
        Globals.getInstance().startGame();
    }

    private void spawnSnake() {
        snake = new Snake(new Point2D(500, 500));
    }

    private void spawnEnemies(int numberOfSimpleEnemies, int numberOfMouseEnemies) {
        for(int i = 0; i < numberOfSimpleEnemies; ++i)
            new SimpleEnemy();

        for(int i = 0; i < numberOfMouseEnemies; ++i)
            new MouseEnemy();
    }

    private void spawnPowerUps(int numberOfPowerUps, int numberTreePowerUps) {

        for(int i = 0; i < numberOfPowerUps; ++i) new SimplePowerUp();
        for(int i = 0; i < numberTreePowerUps; ++i) new TreePowerUp();
    }

    private void setupInputHandling() {
        Scene scene = getScene();
        scene.setOnKeyPressed(event -> InputHandler.getInstance().setKeyPressed(event.getCode()));
        scene.setOnKeyReleased(event -> InputHandler.getInstance().setKeyReleased(event.getCode()));
    }
}

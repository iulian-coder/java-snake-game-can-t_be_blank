package com.codecool.snake.entities.snakes;

import com.codecool.snake.DelayedModificationList;
import com.codecool.snake.Globals;
import com.codecool.snake.PopupScreen;
import com.codecool.snake.entities.Animatable;
import com.codecool.snake.entities.GameEntity;
import com.codecool.snake.eventhandler.InputHandler;

import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;


public class Snake implements Animatable {
    private static float speed = 2;
    private int health = 100;
    private int id;

    private SnakeHead head;
    private DelayedModificationList<GameEntity> body;
    private int lengthBodyPartsTotal = 0;


    public Snake(Point2D position, int id) {
        head = new SnakeHead(this, position);
        body = new DelayedModificationList<>();
        this.id = id;
        addPart(4);
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        if (health > 100){
            health=100;
        }
        this.health = health;
    }

    public void step() {
        SnakeControl turnDir = getUserInput();
        head.updateRotation(turnDir, speed);

        updateSnakeBodyHistory();
        checkForGameOverConditions();

        body.doPendingModifications();
    }

    private SnakeControl getUserInput() {
        SnakeControl turnDir = SnakeControl.INVALID;
        if(InputHandler.getInstance().isKeyPressed(KeyCode.LEFT)) turnDir = SnakeControl.TURN_LEFT;
        if(InputHandler.getInstance().isKeyPressed(KeyCode.RIGHT)) turnDir = SnakeControl.TURN_RIGHT;
        return turnDir;
    }

    public void addPart(int numParts) {

        GameEntity parent = getLastPart();
        Point2D position = parent.getPosition();

        for (int i = 0; i < numParts; i++) {
            SnakeBody newBodyPart = new SnakeBody(position);
            body.add(newBodyPart);
            this.lengthBodyPartsTotal++;
        }
        Globals.getInstance().display.updateSnakeHeadDrawPosition(head);
    }

    public void changeHealth(int diff) {
        health += diff;
        if (health>100){
            health =100;
        }
    }

    public int getLengthBodyPartsTotal() {
        return lengthBodyPartsTotal;
    }

    public static float getSpeed() {
        return speed;
    }

    public static void setSpeed(float speed) {
        Snake.speed = speed;
    }

    private void checkForGameOverConditions() {
        if (head.isOutOfBounds() || health <= 0) {
            System.out.println("Game Over");
            Globals.getInstance().stopGame();
            PopupScreen.display("Snake length " + this.lengthBodyPartsTotal + " ft.");
        }
    }

    private void updateSnakeBodyHistory() {
        GameEntity prev = head;
        for(GameEntity currentPart : body.getList()) {
            currentPart.setPosition(prev.getPosition());
            prev = currentPart;
        }
    }

    private GameEntity getLastPart() {
        GameEntity result = body.getLast();

        if(result != null) return result;
        return head;
    }
}

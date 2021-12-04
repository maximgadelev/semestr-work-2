package com.company;

import javafx.animation.AnimationTimer;
import  javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;

public class Game extends Application {
    public static ArrayList<Block> platforms = new ArrayList<>();
    private HashMap<KeyCode, Boolean> keys = new HashMap<>();
    public static ArrayList<Character> characters = new ArrayList<>();
    public static ArrayList<Bonus> bonuses = new ArrayList<>();

    Image backgroundImg = new Image(getClass().getResourceAsStream("list.png"));
    public static final int BLOCK_SIZE = 45;
    public static final int MARIO_SIZE = 40;
    public static Pane appRoot = new Pane();
    public static Pane gameRoot = new Pane();

    public Character player;

    int levelNumber = 0;
    private int levelWidth;

    private void initContent() {
        ImageView backgroundIV = new ImageView(backgroundImg);
        backgroundIV.setFitHeight(1000);
        backgroundIV.setFitWidth(1000);

        levelWidth = LevelData.levels[levelNumber][0].length() * BLOCK_SIZE;
        for (int i = 0; i < LevelData.levels[levelNumber].length; i++) {
            String line = LevelData.levels[levelNumber][i];
            for (int j = 0; j < line.length(); j++) {
                switch (line.charAt(j)) {
                    case '0':
                        break;
                    case '1':
                        Block platformFloor = new Block(Block.BlockType.PLATFORM, j * BLOCK_SIZE, i * BLOCK_SIZE);
                        break;
                    case '2':
                        Block brick = new Block(Block.BlockType.BRICK, j * BLOCK_SIZE, i * BLOCK_SIZE);
                        break;
                    case '3':
                        Block bonus = new Block(Block.BlockType.BONUS, j * BLOCK_SIZE, i * BLOCK_SIZE);
                        break;
                    case '4':
                        Block stone = new Block(Block.BlockType.STONE, j * BLOCK_SIZE, i * BLOCK_SIZE);
                        break;
                    case '5':
                        Block PipeTopBlock = new Block(Block.BlockType.PIPE_TOP, j * BLOCK_SIZE, i * BLOCK_SIZE);
                        break;
                    case '6':
                        Block PipeBottomBlock = new Block(Block.BlockType.PIPE_BOTTOM, j * BLOCK_SIZE, i * BLOCK_SIZE);
                        break;
                    case '*':
                        Block InvisibleBlock = new Block(Block.BlockType.INVISIBLE_BLOCK, j * BLOCK_SIZE, i * BLOCK_SIZE);
                        break;
                }
            }

        }

        player = new Character();
        player.setTranslateX(0);
        player.setTranslateY(400);
        characters.add(player);
        Bonus bonus = new Bonus(300,500,40,40,"HP_BONUS",Color.RED);
        Bonus bonus1 = new Bonus(400,500,40,40,"DAMAGE_BONUS",Color.BLUE);
        bonuses.add(bonus);
        bonuses.add(bonus1);
        player.translateXProperty().addListener((obs, old, newValue) -> {
            int offset = newValue.intValue();
            if (offset > 640 && offset < levelWidth - 640) {
                gameRoot.setLayoutX(-(offset - 640));

            }
        });
        gameRoot.getChildren().add(bonus);
        gameRoot.getChildren().add(bonus1);

        gameRoot.getChildren().add(player);
        appRoot.getChildren().addAll(backgroundIV, gameRoot);

    }

    private void update() {
        if (player.rect != null) {
            if (isPressed(KeyCode.UP) && player.getTranslateY() >= 5 && !player.isJumped()) {
                player.setJumped(true);;
                player.jumpPlayer();
            }
            if (isPressed(KeyCode.LEFT) && player.getTranslateX() >= 5) {
                player.setScaleX(-1);
                player.setSide(0);
                player.moveX(-5);
            }
            if (isPressed(KeyCode.RIGHT) && player.getTranslateX() + 40 <= levelWidth) {
                player.setScaleX(1);
                player.setSide(1);
                player.moveX(5);
            }
            if (isPressed(KeyCode.SPACE) && !player.isShoot()) {
                player.setShoot(true);
                if (player.getSide() == 0) {
              player.getWeapon().ShootPistol(player.getTranslateX() - 50, player.getTranslateY(), player.getSide());
                } else {
                    player.getWeapon().ShootPistol(player.getTranslateX() + 50, player.getTranslateY(), player.getSide());
                }
            }
            if (player.playerVelocity.getY() < 10) {
                player.playerVelocity = player.playerVelocity.add(0, 1);
            }
            player.moveY((int) player.playerVelocity.getY());
        }
    }

    private boolean isPressed(KeyCode key) {
        return keys.getOrDefault(key, false);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        initContent();
        Scene scene = new Scene(appRoot, 1200, 620);
        scene.setOnKeyPressed(event -> keys.put(event.getCode(), true));

        scene.setOnKeyReleased(event -> {
            if (event.getCode().equals(KeyCode.UP)) {
                player.setJumped(false);
            }
            if (event.getCode().equals(KeyCode.SPACE)) {
                player.setShoot(false);
            }
            keys.put(event.getCode(), false);
        });
        primaryStage.setTitle("My game");
        primaryStage.setScene(scene);
        primaryStage.show();
            AnimationTimer timer = new AnimationTimer() {
                @Override
                public void handle(long now) {
                    update();
                }
            };
            timer.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}


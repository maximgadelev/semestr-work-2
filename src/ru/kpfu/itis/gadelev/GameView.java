package ru.kpfu.itis.gadelev;

import javafx.animation.AnimationTimer;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import ru.kpfu.itis.gadelev.models.Block;
import ru.kpfu.itis.gadelev.models.Bonus;
import ru.kpfu.itis.gadelev.models.Character;
import ru.kpfu.itis.gadelev.server.Client;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class GameView extends View {
    public static ArrayList<Block> platforms = new ArrayList<>();
    private  static HashMap<KeyCode, Boolean> keys = new HashMap<>();
    public static ArrayList<Character> characters = new ArrayList<>();
    public static ArrayList<Bonus> bonuses = new ArrayList<>();

    Image backgroundImg = new Image(new FileInputStream("src/ru/kpfu/itis/gadelev/images/list.png"));
    public static final int BLOCK_SIZE = 45;
    public static final int MARIO_SIZE = 70;
    public static Pane appRoot = new Pane();
    public static Pane gameRoot = new Pane();

    public Character player;
    int levelNumber = 0;
    private static int levelWidth;

    private Pane pane=null;

    private final Game application = getApplication();

    static Client client;


    public static GameView gameView;

    static {
        try {
            gameView = new GameView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public GameView() throws Exception {}

    @Override
    public Parent getView() {
        return pane;
    }

    @Override
    public String getTitle() {
        return null;
    }
    private void initContent() throws IOException {
        client=new Client();
        client.start();
        ImageView backgroundIV = new ImageView(backgroundImg);
        backgroundIV.setFitHeight(640);
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
        player = new Character("run","First");
        player.setTranslateX(0);
        player.setTranslateY(250);
        characters.add(player);
        Bonus bonus1 = new Bonus(400,500,"SHOTGUN_BONUS");
        Bonus bonus2 = new Bonus(500,500,"TWO_BONUS");
        Bonus bonus3=new Bonus(600,500,"HP_BONUS");
//        player.translateXProperty().addListener((obs, old, newValue) -> {
//            int offset = newValue.intValue();
//            if (offset > 640 && offset < levelWidth - 640) {
//                gameRoot.setLayoutX(-(offset - 640));
//
//            }
//        });
        gameRoot.getChildren().addAll(characters);
        appRoot.getChildren().addAll(backgroundIV, gameRoot);

    }

    public void createView() throws IOException {
        initContent();
        Scene scene = new Scene(appRoot);
        scene.setOnKeyPressed(event -> keys.put(event.getCode(), true));

        scene.setOnKeyReleased(event -> {
            if (event.getCode().equals(KeyCode.UP)) {
                player.setJumped(false);
            }
            if (event.getCode().equals(KeyCode.SPACE)) {
                player.setShoot(false);
            }
            keys.put(event.getCode(), false);
            player.spriteAnimation.stop();
        });
        application.getStage().setScene(scene);
        application.getStage().show();
        application.getStage().setWidth(1000);
        application.getStage().setHeight(800);

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                player.updatePlayer();
            }
        };
        timer.start();
    }


    public static Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public static int getLevelWidth() {
        return levelWidth;
    }

    public static HashMap<KeyCode, Boolean> getKeys() {
        return keys;
    }
}
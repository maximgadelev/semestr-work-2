package ru.kpfu.itis.gadelev.views;

import javafx.animation.AnimationTimer;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import ru.kpfu.itis.gadelev.dao.PlayerDao;
import ru.kpfu.itis.gadelev.dao.impl.PlayerDaoImpl;
import ru.kpfu.itis.gadelev.dataBaseModel.Player;
import ru.kpfu.itis.gadelev.game.Game;
import ru.kpfu.itis.gadelev.game.GameMenu;
import ru.kpfu.itis.gadelev.game.LevelData;
import ru.kpfu.itis.gadelev.models.*;
import ru.kpfu.itis.gadelev.models.Character;
import ru.kpfu.itis.gadelev.server.Client;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameView extends View {
    public String type;
    public static ArrayList<Block> platforms = new ArrayList<>();
    private static HashMap<KeyCode, Boolean> keys = new HashMap<>();
    public static ArrayList<Character> characters = new ArrayList<>();
    public static ArrayList<Bonus> bonuses = new ArrayList<>();
    public static ArrayList<Bot> bots = new ArrayList<>();

    Image backgroundImg = new Image(new FileInputStream("src/ru/kpfu/itis/gadelev/images/list.png"));
    public static final int BLOCK_SIZE = 45;
    public static final int MARIO_SIZE = 70;
    public static Pane appRoot = new Pane();
    public static Pane gameRoot = new Pane();

    public Character player;
    public Character secondPlayer;
    public double secondPlayerX;
    public double secondPlayerY;
    public String secondPlayerName;

Scene scene;

    int levelNumber = 0;
    private static int levelWidth;


    private Pane pane = null;

    private final Game application = getApplication();

    static Client client;

    public BorderPane menu;

    public boolean isCreate = false;

    public static GameView gameView;
    AnimationTimer timer;
    WinMenu winMenu;

    PlayerDao<Player> playerDaoPlayerDao = new PlayerDaoImpl();

    static {
        try {
            gameView = new GameView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public GameView() throws Exception {

    }

    @Override
    public Parent getView() {
        return pane;
    }

    @Override
    public String getTitle() {
        return null;
    }

    private void initContentForMulti() throws Exception {

        appRoot = new Pane();
        ImageView backgroundIV = new ImageView(backgroundImg);
        backgroundIV.setFitHeight(1080);
        backgroundIV.setFitWidth(1920);

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
        player = new Character("first","MULTI");
        player.setTranslateX(160);
        player.setTranslateY(250);
        characters.add(player);
        secondPlayer=new Character("second","MULTI");
        characters.add(secondPlayer);
        gameRoot.getChildren().addAll(characters);
        appRoot.getChildren().addAll(backgroundIV, gameRoot);
    }

    private void initContentForSingle() throws Exception {
        ImageView backgroundIV = new ImageView(backgroundImg);
        appRoot = new Pane();
        backgroundIV.setFitHeight(1080);
        backgroundIV.setFitWidth(1920);

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
        player = new Character("first","SINGLE");

        player.setTranslateX(150);
        player.setTranslateY(250);
        characters.add(player);


        try {
            menu = new GameMenu(this).createMenu();
        } catch (Exception e) {
            e.printStackTrace();
        }
        gameRoot.getChildren().addAll(characters);
        appRoot.getChildren().addAll(backgroundIV, gameRoot);

    }


    public void createView(String type) throws Exception {
        if (type.equals("SINGLE")) {
            this.type="SINGLE";
            initContentForSingle();
        } else {
            this.type="MULTI";
            initContentForMulti();
        }
        scene = new Scene(appRoot);
        if(type.equals("MULTI")){

            application.getClient().sendMessage("bonus"+ " " + 960 + " " + 370 + " " + "SHOTGUN_BONUS" + "\n");
            application.getClient().sendMessage("bonus"+ " " + 500 + " " + 370+ " " + "TWO_BONUS" + "\n");
            application.getClient().sendMessage("bonus"+ " " + 730 + " " + 230 + " " + "HP_BONUS" + "\n");
        }
        scene.setOnKeyPressed(event -> keys.put(event.getCode(), true));


        scene.setOnKeyReleased(event -> {
            if (event.getCode().equals(KeyCode.UP)) {
                player.setJumped(false);
            }
            if (event.getCode().equals(KeyCode.SPACE)) {
                player.setShoot(false);
            }
            if(this.type.equals("SINGLE")) {
                if (event.getCode().equals(KeyCode.ESCAPE)) {
                    if (!appRoot.getChildren().contains(menu)) {
                        timer.stop();
                        if (this.type.equals("SINGLE")) {
                            for (Bot bot : bots
                            ) {
                                bot.getAnimationTimer().stop();
                            }
                        }
                        showMenu();
                    } else {
                        hideMenu();
                        for (Bot bot : bots
                        ) {
                            bot.getAnimationTimer().start();
                        }

                        timer.start();
                    }
                }
            }

            keys.put(event.getCode(), false);
            player.spriteAnimation.stop();
        });

        final Long[] time = {System.currentTimeMillis()};
        AnimationTimer timer = new AnimationTimer() {

            @Override
            public void handle(long now) {

if(type.equals("MULTI")) {
    secondPlayer.setTranslateX(secondPlayerX);
    secondPlayer.setTranslateY(secondPlayerY);
    secondPlayer.setName(secondPlayerName);
    secondPlayer.getNickText().setText(secondPlayerName);
    secondPlayer.getNickText().setLayoutY(secondPlayerY-20);
    secondPlayer.getNickText().setLayoutX(secondPlayerX+10);

}
if(type.equals("SINGLE")){
    if(System.currentTimeMillis()-time[0]>5000){
        try {
            gameRoot.getChildren().removeAll(bonuses);
            bonuses.clear();
            spawnBonuses();
            bots.clear();
            bots.add(new Bot(1000,400));
            bots.add(new Bot(1050,300));
            bots.add(new Bot(800,550));
            gameRoot.getChildren().addAll(bots);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        time[0]=System.currentTimeMillis();
    }
}
                try {
                    updatePlayer();
                } catch (Exception e) {
                    e.printStackTrace();
                }



            }
        };

        timer.start();
        isCreate = true;
        application.getStage().setScene(scene);
        application.getStage().show();
        application.getStage().setWidth(1080);
        application.getStage().setHeight(1920);
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

    public void hideMenu() {
        appRoot.getChildren().remove(menu);
    }

    public void showMenu() {
        appRoot.getChildren().add(menu);
    }

    public synchronized void updatePlayer() throws Exception {
        if (player.imageView != null) {
            if (isPressed(KeyCode.UP) && player.getTranslateY() >= 5 && !player.isJumped()) {
                if (!player.position.equals("jump")) {
                    player.spriteAnimation.stop();
                    player.setSpriteAnimation("jump");
                }
                player.setJumped(true);
                player.jumpPlayer();
                player.spriteAnimation.play();
            }

            if (isPressed(KeyCode.LEFT) && player.getTranslateX() >= 5) {
                if (player.isJumped()) {
                    player.setSpriteAnimation("jump");
                } else {
                    if (!player.position.equals("run")) {
                        player.spriteAnimation.stop();
                        player.setSpriteAnimation("run");
                    }
                }
                player.setScaleX(-1);
                player.setSide(0);
                player.moveX(-5);
                player.spriteAnimation.play();
            }
            if (isPressed(KeyCode.RIGHT) && player.getTranslateX() + 40 <= getLevelWidth()) {
                if (player.isJumped()) {
                    player.setSpriteAnimation("jump");
                } else {
                    if (!player.position.equals("run")) {
                        player.spriteAnimation.stop();
                        player.setSpriteAnimation("run");
                    }
                }

                player.setScaleX(1);
                player.setSide(1);
                player.moveX(5);
                player.spriteAnimation.play();
            }
            if (isPressed(KeyCode.SPACE) && !player.isShoot()) {
                player.setShoot(true);
                if (player.getSide() == 0) {
                    player.getWeapon().Shoot(player.getTranslateX() - 80, player.getTranslateY()+10, player.getSide(),player.getWeapon().getType(),player);
                } else {
                    player.getWeapon().Shoot(player.getTranslateX() + 80, player.getTranslateY()+10,player.getSide(),player.getWeapon().getType(),player);
                }
            }
            if (player.playerVelocity.getY() < 10) {
                player.playerVelocity = player.playerVelocity.add(0, 1);
            }
            player.moveY((int) player.playerVelocity.getY());
        }

    }
    private boolean isPressed(KeyCode key) {
        return getKeys().getOrDefault(key, false);
    }

    public void addBullets(double x,double y,int side,int damage) throws Exception {
        javafx.application.Platform.runLater(()->{
            try {
                Bullet bullet = new Bullet(x,y,side,damage);
                gameRoot.getChildren().add(bullet);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }


 public void deletePlayer(String name){
     CopyOnWriteArrayList<Character> charactersToDelete=new CopyOnWriteArrayList<>();
     for (Character character:characters
          ) {
         if(character.getName().equals(name)){
             charactersToDelete.add(character);
         }
     }
     javafx.application.Platform.runLater(()->{
         gameRoot.getChildren().removeAll(charactersToDelete);
         characters.get(0).setMultiscore(characters.get(0).getMultiscore()+1);
         playerDaoPlayerDao.updateMultiScore(characters.get(0).getIdPlayer(),characters.get(0).getMultiscore());
             application.getClient().sendMessage("win" + " " +characters.get(0).getName()+"\n");
             showWinMenu(application.getStage(),characters.get(0).getName());
     });
 }

 public void showWinMenu(Stage stage, String name){
        winMenu=new WinMenu(stage,name);
    }
   public void spawnBonusToRoot(double x,double y,String type) throws FileNotFoundException {
        Bonus bonus = new Bonus(x,y,type);
        application.getClient().getGame().getGameView().bonuses.add(bonus);
        javafx.application.Platform.runLater(()->{
            application.getClient().getGame().getGameView().gameRoot.getChildren().addAll(bonus);
        });

    }
    public void removeBonus(String type){
        CopyOnWriteArrayList<Bonus> bonuses2 = new CopyOnWriteArrayList<>();
        for (Bonus bonus:bonuses
             ) {
            if(bonus.getType().equals(type)){
                bonuses2.add(bonus);
            }
        }
        javafx.application.Platform.runLater(()->{
            bonuses.removeAll(bonuses2);
            gameRoot.getChildren().removeAll(bonuses2);
        });
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static GameView getGameView() {
        return gameView;
    }
    public void spawnBonuses() throws FileNotFoundException {
        Bonus bonus1 = new Bonus(960,370,"SHOTGUN_BONUS");
        Bonus bonus2 = new Bonus(500,370,"TWO_BONUS");
        Bonus bonus3 = new Bonus(730,230,"HP_BONUS");
        bonuses.add(bonus1);
        bonuses.add(bonus2);
        bonuses.add(bonus3);
        gameRoot.getChildren().addAll(bonuses);
    }

}





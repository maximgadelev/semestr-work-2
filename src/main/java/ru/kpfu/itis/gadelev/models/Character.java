package ru.kpfu.itis.gadelev.models;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import ru.kpfu.itis.gadelev.dao.PlayerDao;
import ru.kpfu.itis.gadelev.dao.impl.PlayerDaoImpl;
import ru.kpfu.itis.gadelev.dataBaseModel.Player;
import ru.kpfu.itis.gadelev.game.Game;
import ru.kpfu.itis.gadelev.views.GameView;
import ru.kpfu.itis.gadelev.helpers.SpriteAnimation;
import ru.kpfu.itis.gadelev.views.WinMenuSingle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static ru.kpfu.itis.gadelev.views.View.getApplication;


public class Character extends Pane {
    private int side;
    public Point2D playerVelocity = new Point2D(0, 0);

    private boolean canJump = true;
    private boolean isJumped = false;
    private boolean isShoot = false;
    Game game = getApplication();
    Weapon weapon;
    private int hp;
    Bonus currentBonus = null;
    String name;
    int singleScore;
    int multiscore;
    int idPlayer;


    public SpriteAnimation spriteAnimation;
    Image runImage = new Image(new FileInputStream("src/main/java/ru/kpfu/itis/gadelev/images/run.png"));
    Image jumpImage = new Image(new FileInputStream("src/main/java/ru/kpfu/itis/gadelev/images/jump.png"));
    public ImageView imageView;
    public String position;

    PlayerDao<Player> playerDaoPlayerDao = new PlayerDaoImpl();

    ImageView hpView;
    Text hpText=new Text();
    Text nickText=new Text();

    public boolean isDead=false;

    public String type;
    public int getSide() {
        return side;
    }
String typeOfMulti;
    public void setSide(int side) {
        this.side = side;
    }

    public Character(String type,String typeOfMulti) throws Exception {
        this.multiscore=playerDaoPlayerDao.getByNickName(game.getCurrentPlayer().getNickName()).getMultiScore();
        this.singleScore=playerDaoPlayerDao.getByNickName(game.getCurrentPlayer().getNickName()).getSingleScore();
        this.side = 1;
        this.typeOfMulti=typeOfMulti;
        this.idPlayer=game.getCurrentPlayer().getId();
        this.type=type;
        this.hp=3;
        this.weapon = new Weapon("PISTOL",this);
        this.position="run";
        this.singleScore=0;
        if(type.equals("first")) {
            initHp();
            this.name=game.getCurrentPlayer().getNickName();
            this.nickText.setText(game.getCurrentPlayer().getNickName());
            this.nickText.setTranslateY(this.getTranslateY()-20);
            this.nickText.setTranslateY(this.getTranslateX());
            this.nickText.setFont(Font.font(15));
            GameView.gameRoot.getChildren().add(nickText);
        }else{
            this.nickText.setText(game.getCurrentPlayer().getNickName());
            this.nickText.setTranslateY(this.getTranslateY()-20);
            this.nickText.setTranslateY(this.getTranslateX());
            this.nickText.setFont(Font.font(15));
            GameView.gameRoot.getChildren().add(nickText);
        }
        setSpriteAnimation(position);
    }

    public void moveX(int value) {
        if(this.typeOfMulti.equals("MULTI")) {
            game.getClient().sendMessage("move" + " " + this.getTranslateX() + " " + this.getTranslateY() + " " + this.getName() + "\n");
        }
        boolean movingRight = value > 0;
        for (int i = 0; i < Math.abs(value); i++) {
            for (Node platform : GameView.platforms) {
                if (this.getBoundsInParent().intersects(platform.getBoundsInParent())) {
                    if (movingRight) {
                        if (this.getTranslateX() + GameView.MARIO_SIZE == platform.getTranslateX()) {
                            this.setTranslateX(this.getTranslateX() - 1);
                            return;
                        }
                    } else {
                        if (this.getTranslateX() == platform.getTranslateX() + GameView.BLOCK_SIZE) {
                            this.setTranslateX(this.getTranslateX() + 1);
                            return;
                        }
                    }
                }
            }
            this.setTranslateX(this.getTranslateX() + (movingRight ? 1 : -1));
            isBonusEaten();
        }
    }

    public void moveY(int value) {
        if(this.typeOfMulti.equals("MULTI")) {
            game.getClient().sendMessage("move" + " " + this.getTranslateX() + " " + this.getTranslateY() + " " + this.getName() + "\n");
        }
        boolean movingDown = value > 0;
        for (int i = 0; i < Math.abs(value); i++) {
            for (Block platform : GameView.platforms) {
                if (getBoundsInParent().intersects(platform.getBoundsInParent())) {
                    if (movingDown) {
                        if (this.getTranslateY() + GameView.MARIO_SIZE == platform.getTranslateY()) {
                            this.setTranslateY(this.getTranslateY() - 1);
                            canJump = true;
                            return;
                        }
                    } else {
                        if (this.getTranslateY() == platform.getTranslateY() + GameView.BLOCK_SIZE) {
                            this.setTranslateY(this.getTranslateY() + 1);
                            playerVelocity = new Point2D(0, 10);
                            return;
                        }
                    }
                }
            }
            this.setTranslateY(this.getTranslateY() + (movingDown ? 1 : -1));
            isBonusEaten();
            if (this.getTranslateY() > 640) {
                this.setTranslateX(0);
                this.setTranslateY(400);
                GameView.gameRoot.setLayoutX(0);
            }
        }

    }

    public void jumpPlayer() {
        if (canJump) {
            playerVelocity = playerVelocity.add(0, -30);
            canJump = false;
        }
    }

    public boolean isJumped() {
        return isJumped;
    }

    public void setJumped(boolean jumped) {
        isJumped = jumped;
    }

    public boolean isShoot() {
        return isShoot;
    }

    public void setShoot(boolean shoot) {
        isShoot = shoot;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public synchronized void getDamage(int damage) throws Exception {
        if (hp <= 1) {
            hp=0;
            getChildren().remove(imageView);
            imageView = null;
            this.isDead=true;
            GameView.gameRoot.getChildren().remove(imageView);
            currentBonus = null;
            weapon.setType("PISTOL");
            if(typeOfMulti.equals("MULTI")) {
                game.getClient().sendMessage("died" + " " + this.getName() + "\n");
            }
            if(typeOfMulti.equals("SINGLE")){
                showWin(this.getName());
            }
        } else {
            hp = hp - damage;
        }
        hpText.setText(String.valueOf(this.hp));
    }

    public  void  isBonusEaten() {
        GameView.bonuses.forEach((rect) -> {
                    if (this.getBoundsInParent().intersects(rect.getBoundsInParent()) && rect.getType().equals("HP_BONUS") && this.getHp()>=3 && this.getHp()<5 && typeOfMulti.equals("MULTI")) {
                        this.setHp(this.hp+2);
                        this.hpText.setText(String.valueOf(this.hp));
                        currentBonus = rect;
                    }else if(this.getBoundsInParent().intersects(rect.getBoundsInParent()) && rect.getType().equals("HP_BONUS") && typeOfMulti.equals("SINGLE")){
                        this.setHp(this.hp+2);
                        this.hpText.setText(String.valueOf(this.hp));
                        currentBonus = rect;
                    }
                    if (this.getBoundsInParent().intersects(rect.getBoundsInParent()) && rect.getType().equals("SHOTGUN_BONUS")) {
                        this.getWeapon().setType("SHOTGUN");
                        this.getWeapon().setWeaponImageView(rect.bonusImageView);
                        currentBonus = rect;
                    }
                    if (this.getBoundsInParent().intersects(rect.getBoundsInParent()) && rect.getType().equals("TWO_BONUS")) {
                        this.getWeapon().setType("TWO_BONUS");
                        this.getWeapon().setWeaponImageView(rect.bonusImageView);
                        currentBonus = rect;
                    }
                }
        );

        if(currentBonus!=null) {
            if (game.getGameView().type.equals("MULTI")) {
                game.getClient().sendMessage("taken" + " " + currentBonus.type + "\n");
                javafx.application.Platform.runLater(() -> {
                            GameView.gameRoot.getChildren().remove(currentBonus);
                            GameView.bonuses.remove(currentBonus);
                        }
                );
            }else{
                GameView.gameRoot.getChildren().remove(currentBonus);
                GameView.bonuses.remove(currentBonus);
            }
            }
    }

    public Weapon getWeapon() {
        return weapon;
    }


    public void setSpriteAnimation(String animationType) {
        if (animationType.equals("run")) {
            if (this.imageView != null) {
                javafx.application.Platform.runLater(()->{
                    getChildren().remove(imageView);
                });
                this.imageView.setImage(runImage);
            } else {
                this.imageView = new ImageView(runImage);
            }
            this.position=animationType;
            this.imageView.setFitHeight(70);
            this.imageView.setFitWidth(70);
            this.imageView.setViewport(new Rectangle2D(0, 0, 45, 52));
            this.spriteAnimation = new SpriteAnimation(this.imageView, Duration.millis(1000), 6, 6, 0, 0, 45, 52);

            javafx.application.Platform.runLater(()-> {
                getChildren().add(imageView);
                this.imageView.setImage(runImage);
            });
        } else {
            if (animationType.equals("jump")) {
                if (this.imageView != null) {
                    javafx.application.Platform.runLater(()-> {

                        getChildren().remove(this.imageView);
                        this.imageView.setImage(this.jumpImage);
                    });

                } else {
                    javafx.application.Platform.runLater(()-> {
                        this.imageView = new ImageView(this.jumpImage);
                    });
                }
                this.position=animationType;
                this.imageView.setFitHeight(70);
                this.imageView.setFitWidth(70);
                this.imageView.setViewport(new Rectangle2D(0, 0, 35, 52));
                this.spriteAnimation = new SpriteAnimation(this.imageView, Duration.millis(500), 1, 1, 144, 0, 35, 52);

                javafx.application.Platform.runLater(()-> {
                    getChildren().add(this.imageView);
                });
            }
        }
    }
    public synchronized void initHp() throws FileNotFoundException {
        hpText.setText(String.valueOf(this.hp));
        hpView=new ImageView(new Image(new FileInputStream("src/main/java/ru/kpfu/itis/gadelev/images/HPP.png")));
        hpView.setViewport(new Rectangle2D(0,0,2000,2000));
        hpView.setFitWidth(50);
        hpView.setFitHeight(50);
        hpView.setLayoutX(0);
        hpView.setLayoutY(50);
        hpText.setLayoutX(60);
        hpText.setLayoutY(80);
        hpText.setFont(Font.font(30));
        javafx.application.Platform.runLater(()->{
                    GameView.gameRoot.getChildren().add(hpText);
                    GameView.gameRoot.getChildren().add(hpView);
                }
                );

    }

    public String getName() {
        return name;
    }




    public double getX() {
        return this.getTranslateX();
    }
    public double getY() {
        return this.getTranslateY();
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public boolean isDead() {
        return isDead;
    }

    public String getType() {
        return type;
    }

    public int getMultiscore() {
        return multiscore;
    }

    public int getSingleScore() {
        return singleScore;
    }

    public void setMultiscore(int multiscore) {
        this.multiscore = multiscore;
    }

    public int getIdPlayer() {
        return idPlayer;
    }

    public Text getNickText() {
        return nickText;
    }

    public void setNickText(Text nickText) {
        this.nickText = nickText;
    }
    public void showWin(String winner) throws Exception {
        WinMenuSingle winMenuSingle = new WinMenuSingle(game.getStage(),winner);
    }
}






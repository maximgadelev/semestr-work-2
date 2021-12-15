package ru.kpfu.itis.gadelev.models;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import ru.kpfu.itis.gadelev.Game;
import ru.kpfu.itis.gadelev.GameView;
import ru.kpfu.itis.gadelev.SpriteAnimation;

import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class Character extends Pane {

    private int side;
    public Point2D playerVelocity = new Point2D(0, 0);

    private boolean canJump = true;
    private boolean isJumped = false;
    private boolean isShoot = false;

    Weapon weapon;
    private int hp = 3;
    Bonus currentBonus = null;
    String name;


    public SpriteAnimation spriteAnimation;
    Image runImage = new Image(new FileInputStream("src/ru/kpfu/itis/gadelev/images/run.png"));
    Image jumpImage = new Image(new FileInputStream("src/ru/kpfu/itis/gadelev/images/jump.png"));
    public ImageView imageView;
    public String position;


    ImageView hpView;
    Text hpText=new Text();

    public int getSide() {
        return side;
    }

    public void setSide(int side) {
        this.side = side;
    }

    public Character(String position,String name) throws FileNotFoundException {
        this.side = 1;
        this.weapon = new Weapon("PISTOL");
        this.position=position;
        this.name=name;
        initHp();
        setSpriteAnimation(position);
    }

    public void moveX(int value) {
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

    public void getDamage(int damage) {
        if (hp <= 1) {
            getChildren().remove(imageView);
            imageView = null;
            GameView.gameRoot.getChildren().remove(imageView);
            currentBonus = null;
            weapon.setType("PISTOL");
        } else {
            hp = hp - damage;
        }
        hpText.setText(String.valueOf(this.hp));
    }

    public void isBonusEaten() {
        GameView.bonuses.forEach((rect) -> {
                    if (this.getBoundsInParent().intersects(rect.getBoundsInParent()) && rect.getType().equals("HP_BONUS")) {
                        this.setHp(this.getHp() + 2);
                        hpText.setText(String.valueOf(this.getHp()));
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

        GameView.gameRoot.getChildren().remove(currentBonus);
        GameView.bonuses.remove(currentBonus);
    }

    public Weapon getWeapon() {
        return weapon;
    }


    public void setSpriteAnimation(String animationType) {
        if (animationType.equals("run")) {
            if (imageView != null) {
                getChildren().remove(imageView);
                this.imageView.setImage(runImage);
            } else {
                this.imageView = new ImageView(runImage);
            }
            this.position=animationType;
            this.imageView.setFitHeight(70);
            this.imageView.setFitWidth(70);
            this.imageView.setViewport(new Rectangle2D(0, 0, 45, 52));
            this.spriteAnimation = new SpriteAnimation(this.imageView, Duration.millis(1000), 6, 6, 0, 0, 45, 52);
            getChildren().add(imageView);
        } else {
            if (animationType.equals("jump")) {
                if (imageView != null) {
                    getChildren().remove(imageView);
                    this.imageView.setImage(jumpImage);
                } else {
                    this.imageView = new ImageView(jumpImage);
                }
                this.position=animationType;
                this.imageView.setFitHeight(70);
                this.imageView.setFitWidth(70);
                this.imageView.setViewport(new Rectangle2D(0, 0, 35, 52));
                this.spriteAnimation = new SpriteAnimation(this.imageView, Duration.millis(500), 1, 1, 144, 0, 35, 52);
                getChildren().add(imageView);
            }
        }
    }
    public void initHp() throws FileNotFoundException {
        hpText.setText(String.valueOf(this.hp));
        hpView=new ImageView(new Image(new FileInputStream("src/ru/kpfu/itis/gadelev/images/HPP.png")));
        hpView.setViewport(new Rectangle2D(0,0,2000,2000));
        hpView.setFitWidth(50);
        hpView.setFitHeight(50);
        hpView.setLayoutX(0);
        hpView.setLayoutY(50);
        hpText.setLayoutX(60);
        hpText.setLayoutY(80);
        hpText.setFont(Font.font(30));
        GameView.gameRoot.getChildren().add(hpText);
        GameView.gameRoot.getChildren().add(hpView);
    }

    public String getName() {
        return name;
    }
    public void updatePlayer(){
        if (this.imageView != null) {
            if (isPressed(KeyCode.UP) && this.getTranslateY() >= 5 && !this.isJumped()) {
                if (!this.position.equals("jump")) {
                    this.spriteAnimation.stop();
                    this.setSpriteAnimation("jump");
                }
                this.setJumped(true);
                this.jumpPlayer();
                this.spriteAnimation.play();
            }

            if (isPressed(KeyCode.LEFT) && this.getTranslateX() >= 5) {
                if (this.isJumped()) {
                    this.setSpriteAnimation("jump");
                } else {
                    if (!this.position.equals("run")) {
                        this.spriteAnimation.stop();
                        this.setSpriteAnimation("run");
                    }
                }
                this.setScaleX(-1);
                this.setSide(0);
                this.moveX(-5);
                this.spriteAnimation.play();
            }
            if (isPressed(KeyCode.RIGHT) && this.getTranslateX() + 40 <= GameView.gameView.getLevelWidth()) {
                if (this.isJumped()) {
                    this.setSpriteAnimation("jump");
                } else {
                    if (!this.position.equals("run")) {
                        this.spriteAnimation.stop();
                        this.setSpriteAnimation("run");
                    }
                }

                this.setScaleX(1);
                this.setSide(1);
                this.moveX(5);
                this.spriteAnimation.play();
            }
            if (isPressed(KeyCode.SPACE) && !this.isShoot()) {
                this.setShoot(true);
                if (this.getSide() == 0) {
                    this.getWeapon().Shoot(this.getTranslateX() - 80, this.getTranslateY()+10, this.getSide(),this.getWeapon().getType());
                    GameView.gameView.getClient().sendMessage(name,this.getTranslateX(),this.getTranslateY());
                } else {
                    this.getWeapon().Shoot(this.getTranslateX() + 80, this.getTranslateY()+10, this.getSide(),this.getWeapon().getType());
                    GameView.gameView.getClient().sendMessage(name,this.getTranslateX(),this.getTranslateY());
                }
            }
            if (this.playerVelocity.getY() < 10) {
                this.playerVelocity = this.playerVelocity.add(0, 1);
            }
            this.moveY((int) this.playerVelocity.getY());
//            GameView.gameView.getClient().sendMessage(name,this.getTranslateX(),this.getTranslateY());

        }
    }

    private boolean isPressed(KeyCode key) {
        return GameView.gameView.getKeys().getOrDefault(key, false);
    }

}






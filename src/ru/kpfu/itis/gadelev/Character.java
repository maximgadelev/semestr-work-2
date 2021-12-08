package ru.kpfu.itis.gadelev;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;


public class Character extends Pane {

    private int side;
    public Point2D playerVelocity = new Point2D(0, 0);

    private boolean canJump = true;
    private boolean isJumped = false;
    private boolean isShoot = false;

    Weapon weapon;
    private int hp = 3;
    Bonus currentBonus = null;


    SpriteAnimation spriteAnimation;
    Image runImage = new Image(getClass().getResourceAsStream("run.png"));
    Image jumpImage = new Image(getClass().getResourceAsStream("jump.png"));
    ImageView imageView;
    String position;


    ImageView hpView;
    Text hpText=new Text();
    public int getSide() {
        return side;
    }

    public void setSide(int side) {
        this.side = side;
    }

    public Character(String position) {
        this.side = 123;
        this.weapon = new Weapon("PISTOL");
        this.position=position;
initHp();
        setSpriteAnimation(position);
    }

    public void moveX(int value) {
        boolean movingRight = value > 0;
        for (int i = 0; i < Math.abs(value); i++) {
            for (Node platform : Game.platforms) {
                if (this.getBoundsInParent().intersects(platform.getBoundsInParent())) {
                    if (movingRight) {
                        if (this.getTranslateX() + Game.MARIO_SIZE == platform.getTranslateX()) {
                            this.setTranslateX(this.getTranslateX() - 1);
                            return;
                        }
                    } else {
                        if (this.getTranslateX() == platform.getTranslateX() + Game.BLOCK_SIZE) {
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
            for (Block platform : Game.platforms) {
                if (getBoundsInParent().intersects(platform.getBoundsInParent())) {
                    if (movingDown) {
                        if (this.getTranslateY() + Game.MARIO_SIZE == platform.getTranslateY()) {
                            this.setTranslateY(this.getTranslateY() - 1);
                            canJump = true;
                            return;
                        }
                    } else {
                        if (this.getTranslateY() == platform.getTranslateY() + Game.BLOCK_SIZE) {
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
                Game.gameRoot.setLayoutX(0);
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
            Game.gameRoot.getChildren().remove(imageView);
            currentBonus = null;
            weapon.setType("PISTOL");
            hpText.setText(String.valueOf(this.hp));
        } else {
            hp = hp - damage;
            hpText.setText(String.valueOf(this.hp));
        }
    }

    public void isBonusEaten() {
        Game.bonuses.forEach((rect) -> {
                    if (this.getBoundsInParent().intersects(rect.getBoundsInParent()) && rect.getType().equals("HP_BONUS")) {
                        this.setHp(this.getHp() + 2);
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

        Game.gameRoot.getChildren().remove(currentBonus);
        Game.bonuses.remove(currentBonus);
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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
    public void initHp(){
        hpText.setText(String.valueOf(this.hp));
        hpView=new ImageView(new Image(getClass().getResourceAsStream("HPP.png")));
        hpView.setViewport(new Rectangle2D(0,0,2000,2000));
        hpView.setFitWidth(50);
        hpView.setFitHeight(50);
        hpView.setLayoutX(0);
        hpView.setLayoutY(50);
        hpText.setLayoutX(60);
        hpText.setLayoutY(80);
        hpText.setFont(Font.font(30));
        Game.gameRoot.getChildren().add(hpText);
        Game.gameRoot.getChildren().add(hpView);
    }
}





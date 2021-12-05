package ru.kpfu.itis.gadelev;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;


public class Character extends Pane {

    private int side;
    public Point2D playerVelocity = new Point2D(0, 0);

    private boolean canJump = true;
    private boolean isJumped = false;
    private boolean isShoot = false;

    Weapon weapon;
    private int hp = 3;
    Rectangle currentBonus = null;

    int count = 3;
    int columns = 3;
    int offsetX = 80;
    int offsetY = 0;
    int width = 16;
    int height = 32;
    SpriteAnimation spriteAnimation;
    Image characterImage = new Image(getClass().getResourceAsStream("mario1.png"));
    ImageView imageView = new ImageView(characterImage);

    public int getSide() {
        return side;
    }

    public void setSide(int side) {
        this.side = side;
    }

    public Character() {
        this.side = 123;
        imageView.setFitHeight(40);
        imageView.setFitWidth(40);
        imageView.setViewport(new Rectangle2D(offsetX, offsetY, width, height));
        spriteAnimation = new SpriteAnimation(this.imageView, Duration.millis(200), count, columns, offsetX, offsetY, width, height);
        getChildren().addAll(imageView);
        this.weapon = new Weapon("PISTOL");
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

    public boolean isCanJump() {
        return canJump;
    }

    public void setCanJump(boolean canJump) {
        this.canJump = canJump;
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
        } else {
            hp = hp - damage;
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
                        currentBonus = rect;
                    }
                    if (this.getBoundsInParent().intersects(rect.getBoundsInParent()) && rect.getType().equals("TWO_BONUS")) {
                        this.getWeapon().setType("TWO_BONUS");
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

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }
}





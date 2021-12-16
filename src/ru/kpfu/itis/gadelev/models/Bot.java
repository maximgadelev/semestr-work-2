package ru.kpfu.itis.gadelev.models;


import javafx.animation.AnimationTimer;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.shape.Rectangle;
import ru.kpfu.itis.gadelev.game.Game;
import ru.kpfu.itis.gadelev.views.GameView;

public class Bot extends Rectangle {
    private int hp = 3;
    Rectangle rectangle;

    public Bot(double x, double y) {
        super(x, y, 40, 40);
        rectangle=this;
        for (Node platform : GameView.platforms) {
            if (this.getBoundsInParent().intersects(platform.getBoundsInParent())) {
                if (this.getTranslateX() + 40 == platform.getTranslateX()) {
                    this.setTranslateX(this.getTranslateX() - 1);
                }
            }
                    if (this.getTranslateX() == platform.getTranslateX() + GameView.BLOCK_SIZE) {
                        this.setTranslateX(this.getTranslateX() + 1);
                    }
                if (this.getTranslateY() + 40 == platform.getTranslateY()) {
                    this.setTranslateY(this.getTranslateY() - 1);
                }
            if (this.getTranslateY() == platform.getTranslateY() + GameView.BLOCK_SIZE) {
                this.setTranslateY(this.getTranslateY() + 1);
            }
                }
            }
            public void getDamage(){
                if (hp <= 1) {
                    GameView.bots.remove(rectangle);
                    GameView.gameRoot.getChildren().remove(rectangle);
                    rectangle=null;
                } else {
                    hp = hp - 1;
                }
            }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }
}



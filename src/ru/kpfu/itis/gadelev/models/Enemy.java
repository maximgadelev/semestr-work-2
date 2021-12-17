package ru.kpfu.itis.gadelev.models;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import ru.kpfu.itis.gadelev.helpers.SpriteAnimation;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Enemy extends Pane {
    Image runImage = new Image(new FileInputStream("src/ru/kpfu/itis/gadelev/images/run.png"));
    Image jumpImage = new Image(new FileInputStream("src/ru/kpfu/itis/gadelev/images/jump.png"));
    String name;
    SpriteAnimation spriteAnimation;
    public int hp;

    public Enemy(String name) throws FileNotFoundException {
        this.setHeight(70);
        this.setWidth(70);
        this.name=name;
        this.hp=3;
    }
    public synchronized void move(double x,double y,int hp){
        this.hp=hp;
        setTranslateX(x);
        setTranslateY(y);
    }
    public synchronized void changeAnimation(int animationIndex, int animationColumns,
                                             int animationWidth, int animationHeight, int animationOffsetX, int animationOffsetY) {
        javafx.application.Platform.runLater(() -> {
            spriteAnimation.setParameters(animationIndex, animationColumns, animationWidth,
                    animationHeight, animationOffsetX, animationOffsetY);
            spriteAnimation.setX(this.getTranslateX());
            spriteAnimation.setY(this.getTranslateY());
        });
    }

}

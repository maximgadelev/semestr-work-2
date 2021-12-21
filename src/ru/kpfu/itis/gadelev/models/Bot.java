package ru.kpfu.itis.gadelev.models;


import javafx.animation.AnimationTimer;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import ru.kpfu.itis.gadelev.views.GameView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Bot extends Pane {
    private int hp = 3;
    Image botImage;
    boolean isDead=false;
    ImageView botImageView;
    public  AnimationTimer animationTimer;
    public Bot(double x, double y) throws FileNotFoundException {
        this.setTranslateX(x);
        this.setTranslateY(y);
this.botImage=new Image(new FileInputStream("src/ru/kpfu/itis/gadelev/images/bot.png"));
this.botImageView=new ImageView(botImage);
botImageView.setFitWidth(50);
botImageView.setFitHeight(50);
botImageView.setViewport(new Rectangle2D(0,0,324,267));
getChildren().add(botImageView);
Bot bot = this;
 animationTimer = new AnimationTimer() {
    @Override
    public void handle(long l) {
        if (botImageView!=null) {
            for (Character character : GameView.characters) {
                if (bot.getBoundsInParent().intersects(character.getBoundsInParent())) {
                    this.stop();
                    GameView.gameRoot.getChildren().remove(bot);
                    GameView.bots.remove(bot);
                    GameView.gameRoot.getChildren().remove(botImageView);
                    try {
                        character.getDamage(1);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            }

            for (Node platform : GameView.platforms) {
                if (bot.getBoundsInParent().intersects(platform.getBoundsInParent())) {
                    setTranslateY(getTranslateY() - 5);
                }
            }
            if(bot.getTranslateX()<=0){
                GameView.gameRoot.getChildren().remove(bot);
                GameView.gameRoot.getChildren().remove(bot.botImageView);
                getChildren().remove(bot);
                getChildren().remove(bot.botImageView);
                GameView.bots.remove(bot);
                botImageView=null;
            }
            setTranslateX(getTranslateX() - 5);
        }
    }

};
animationTimer.start();
    }

    public void dead(Bot bot) {
        getChildren().remove(bot.botImageView);
        bot.botImageView = null;
        isDead=true;
        GameView.gameRoot.getChildren().remove(bot.botImageView);
    }

    public  AnimationTimer getAnimationTimer() {
        return animationTimer;
    }
}



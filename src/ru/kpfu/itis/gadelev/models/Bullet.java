package ru.kpfu.itis.gadelev.models;
import javafx.animation.AnimationTimer;
import javafx.scene.Node;
import javafx.scene.shape.Rectangle;
import ru.kpfu.itis.gadelev.game.Game;
import ru.kpfu.itis.gadelev.views.GameView;

import java.util.concurrent.CopyOnWriteArrayList;

import static ru.kpfu.itis.gadelev.views.View.getApplication;

public  class Bullet extends Rectangle {
    Game game = getApplication();
public static int killed=0;
public static AnimationTimer animationTimer;
    public Bullet(double x, double y,int side,int damage) throws Exception {
        super(x,y,10,3);
CopyOnWriteArrayList<Bot> bots1 = new CopyOnWriteArrayList<>();
        Bullet bullet=this;


         animationTimer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                for (Node platform : GameView.platforms) {
                    if (bullet.getBoundsInParent().intersects(platform.getBoundsInParent())) {
                        this.stop();
                        javafx.application.Platform.runLater(()->{   GameView.gameRoot.getChildren().remove(bullet);});
                        GameView.gameRoot.getChildren().remove(bullet);
                    }
                }
                    for(Character character:GameView.characters){
                        if(bullet.getBoundsInParent().intersects(character.getBoundsInParent())) {
                            this.stop();
                            javafx.application.Platform.runLater(()->{
                                GameView.gameRoot.getChildren().remove(bullet);
                            });
                            if(game.getCurrentPlayer().getNickName().equals(character.getName())){
                                try {
                                    character.getDamage(damage);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }

                        GameView.bots.forEach((rect)->{
                            if(bullet.getBoundsInParent().intersects(rect.getBoundsInParent()) && !rect.isDead){
                                GameView.gameRoot.getChildren().remove(bullet);
                                System.out.println("Коснулся" );
                                this.stop();
                                GameView.gameRoot.getChildren().remove(rect);
                                GameView.gameRoot.getChildren().remove(rect.botImageView);
                                rect.dead(rect);
                                killed++;
                            }
                        }
                        );

if(side==1){
    setX(getX()+10);
}else {
   setX(getX()-10);
}
            }
        };
        animationTimer.start();
    }


}

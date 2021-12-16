package ru.kpfu.itis.gadelev.models;
import javafx.animation.AnimationTimer;
import javafx.scene.Node;
import javafx.scene.shape.Rectangle;
import ru.kpfu.itis.gadelev.views.GameView;
public  class Bullet extends Rectangle {
    Character bulletCharacter;
    public Bullet(double x, double y,int side,int damage,Character bulletCharacter){
        super(x,y,10,3);
   Bullet bullet=this;
   this.bulletCharacter = bulletCharacter;

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                for (Node platform : GameView.platforms) {
                    if (bullet.getBoundsInParent().intersects(platform.getBoundsInParent())) {
                        this.stop();
                        GameView.gameRoot.getChildren().remove(bullet);
                    }
                }
                    for(Character character:GameView.characters){
                        if(bullet.getBoundsInParent().intersects(character.getBoundsInParent())) {
                            this.stop();
                            GameView.gameRoot.getChildren().remove(bullet);
                            if(bullet.getBulletCharacter().getName().equals(character.getName())){
                                character.getDamage(damage);
                            }
                        }
                    }
if(side==1){
    setX(getX()+2);
}else {
   setX(getX() - 10);
}
            }
        };
        timer.start();
    }

    public Character getBulletCharacter() {
        return bulletCharacter;
    }
}

package ru.kpfu.itis.gadelev;




import javafx.animation.AnimationTimer;
import javafx.scene.Node;
import javafx.scene.shape.Rectangle;


public  class Bullet extends Rectangle {
    public Bullet(double x, double y,int side,int damage){
        super(x,y,10,3);
   Bullet bullet=this;

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                for (Node platform : Game.platforms) {
                    if (bullet.getBoundsInParent().intersects(platform.getBoundsInParent())) {
                        this.stop();
                        Game.gameRoot.getChildren().remove(bullet);
                    }
                }
                    for(Character character:Game.characters){
                        if(bullet.getBoundsInParent().intersects(character.getBoundsInParent())) {
                            this.stop();
                            Game.gameRoot.getChildren().remove(bullet);
                            character.getDamage(damage);
                        }
                    }
if(side==1){
    setX(getX()+10);
}else {
   setX(getX() - 10);
}
            }
        };
        timer.start();
    }
}

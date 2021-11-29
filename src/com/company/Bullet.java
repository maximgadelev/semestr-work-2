package com.company;

import javafx.animation.AnimationTimer;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public  class Bullet extends Rectangle {
    private static final double width=20;
    private static final double height=10;



    public Bullet(double x, double y, double width, double height, int side){
        super(x,y,width,height);
        setFill(Color.RED);
        Bullet bullet=this;
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                for (Node platform : Game.platforms) {
                    if(bullet.getBoundsInParent().intersects(platform.getBoundsInParent())){
                        this.stop();
                        Game.gameRoot.getChildren().remove(bullet);
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

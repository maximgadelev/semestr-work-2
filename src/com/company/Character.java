package com.company;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
    public class Character extends Pane{
       Rectangle rect;
        public Point2D playerVelocity = new Point2D(0,0);
        private boolean canJump = true;

        public Character() {
            rect=new Rectangle(40,40, Color.BLACK);
            getChildren().addAll(rect);
        }
        public void moveX(int value){
            boolean movingRight = value > 0;
            for(int i = 0; i<Math.abs(value); i++) {
                for (Node platform : Game.platforms) {
                    if(this.getBoundsInParent().intersects(platform.getBoundsInParent())) {
                        if (movingRight) {
                            if (this.getTranslateX() + Game.MARIO_SIZE == platform.getTranslateX()){
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
            }
        }
        public void moveY(int value){
            boolean movingDown = value >0;
            for(int i = 0; i < Math.abs(value); i++){
                for(Block platform :Game.platforms){
                    if(getBoundsInParent().intersects(platform.getBoundsInParent())){
                        if(movingDown){
                            if(this.getTranslateY()+ Game.MARIO_SIZE == platform.getTranslateY()){
                                this.setTranslateY(this.getTranslateY()-1);
                                canJump = true;
                                return;
                            }
                        }
                        else{
                            if(this.getTranslateY() == platform.getTranslateY()+ Game.BLOCK_SIZE){
                                this.setTranslateY(this.getTranslateY()+1);
                                playerVelocity = new Point2D(0,10);
                                return;
                            }
                        }
                    }
                }
                this.setTranslateY(this.getTranslateY() + (movingDown?1:-1));
                if(this.getTranslateY()>640){
                    this.setTranslateX(0);
                    this.setTranslateY(400);
                    Game.gameRoot.setLayoutX(0);
                }
            }
        }
        public void jumpPlayer(){
            if(canJump){
                playerVelocity = playerVelocity.add(0,-30);
                canJump = false;
            }
        }
    }


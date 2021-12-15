package ru.kpfu.itis.gadelev.models;


import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ru.kpfu.itis.gadelev.Game;
import ru.kpfu.itis.gadelev.GameView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Weapon {
    String type;
    int damage;
    ImageView weaponImageView;

    public Weapon(String type) throws FileNotFoundException {
        this.type=type;
        this.damage=1;
        this.weaponImageView=new ImageView(new Image(new FileInputStream("src/ru/kpfu/itis/gadelev/images/pn.png")));
        weaponImageView.setFitHeight(40);
        weaponImageView.setFitWidth(40);
        weaponImageView.setViewport(new Rectangle2D(0,0,580,580));
        GameView.gameRoot.getChildren().add(weaponImageView);
    }

    public void Shoot(double x, double y, int side, String type){
        if(type.equals("SHOTGUN")){
            Bullet bullet1=new Bullet(x,y,side,damage);
            Bullet bullet2=new Bullet(x,y-30,side,damage);
            Bullet bullet3=new Bullet(x,y+20,side,damage);
            GameView.gameRoot.getChildren().add(bullet1);
            GameView.gameRoot.getChildren().add(bullet2);
            GameView.gameRoot.getChildren().add(bullet3);
        }
        if(type.equals("PISTOL")){
            Bullet bullet1 = new Bullet(x,y, side,damage);
            GameView.gameRoot.getChildren().add(bullet1);
        }
        if(type.equals("TWO_BONUS")){
            Bullet bullet1=new Bullet(x,y,side,damage);
            Bullet bullet2=new Bullet(x,y-30,side,damage);
            GameView.gameRoot.getChildren().add(bullet1);
            GameView.gameRoot.getChildren().add(bullet2);
        }
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }



    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public ImageView getWeaponImageView() {
        return weaponImageView;
    }

    public void setWeaponImageView(ImageView weaponImageView) {
        GameView.gameRoot.getChildren().remove(this.weaponImageView);
        this.weaponImageView = weaponImageView;
        GameView.gameRoot.getChildren().add(weaponImageView);
    }
}
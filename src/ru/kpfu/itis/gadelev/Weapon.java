package ru.kpfu.itis.gadelev;


import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Weapon {
    String type;
    int damage;
    ImageView weaponImageView;

    public Weapon(String type){
        this.type=type;
        this.damage=1;
        this.weaponImageView=new ImageView(new Image(getClass().getResourceAsStream("pn.png")));
        weaponImageView.setFitHeight(40);
        weaponImageView.setFitWidth(40);
        weaponImageView.setViewport(new Rectangle2D(0,0,580,580));
        Game.gameRoot.getChildren().add(weaponImageView);
    }

    void Shoot(double x,double y,int side,String type){
        if(type.equals("SHOTGUN")){
            Bullet bullet1=new Bullet(x,y,side,damage);
            Bullet bullet2=new Bullet(x,y-30,side,damage);
            Bullet bullet3=new Bullet(x,y+20,side,damage);
            Game.gameRoot.getChildren().add(bullet1);
            Game.gameRoot.getChildren().add(bullet2);
            Game.gameRoot.getChildren().add(bullet3);
        }
        if(type.equals("PISTOL")){
            Bullet bullet1 = new Bullet(x,y, side,damage);
            Game.gameRoot.getChildren().add(bullet1);
        }
        if(type.equals("TWO_BONUS")){
            Bullet bullet1=new Bullet(x,y,side,damage);
            Bullet bullet2=new Bullet(x,y-30,side,damage);
            Game.gameRoot.getChildren().add(bullet1);
            Game.gameRoot.getChildren().add(bullet2);
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
        Game.gameRoot.getChildren().remove(this.weaponImageView);
        this.weaponImageView = weaponImageView;
        Game.gameRoot.getChildren().add(weaponImageView);
    }
}

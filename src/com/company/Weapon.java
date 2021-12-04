package com.company;

public class Weapon {
    String type;
    Bullet bullet;
    int damage;

    public Weapon(String type){
        this.type=type;
        this.damage=1;
    }
    void ShootPistol(double x, double y, double width, double height, int side){
        this.bullet = new Bullet(x,y,width,height,side,damage);
        Game.gameRoot.getChildren().add(bullet);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Bullet getBullet() {
        return bullet;
    }

    public void setBullet(Bullet bullet) {
        this.bullet = bullet;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }
}

package ru.kpfu.itis.gadelev;



public class Weapon {
    String type;
    int damage;

    public Weapon(String type){
        this.type=type;
        this.damage=1;
    }

    void Shoot(double x,double y,int side,String type){
        if(type.equals("SHOTGUN")){
            Bullet bullet1=new Bullet(x,y,20,20,side,damage);
            Bullet bullet2=new Bullet(x,y-30,20,20,side,damage);
            Bullet bullet3=new Bullet(x,y+20,20,20,side,damage);
            Game.gameRoot.getChildren().add(bullet1);
            Game.gameRoot.getChildren().add(bullet2);
            Game.gameRoot.getChildren().add(bullet3);
        }
        if(type.equals("PISTOL")){
            Bullet bullet1 = new Bullet(x,y, 20, 20,side,damage);
            Game.gameRoot.getChildren().add(bullet1);
        }
        if(type.equals("TWO_BONUS")){
            Bullet bullet1=new Bullet(x,y,20,20,side,damage);
            Bullet bullet2=new Bullet(x,y-30,20,20,side,damage);
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
}

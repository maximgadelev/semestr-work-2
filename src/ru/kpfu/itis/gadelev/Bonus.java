package ru.kpfu.itis.gadelev;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;


public class Bonus extends Pane {
    String type;
    Image bonusImage;
    ImageView bonusImageView;
    public Bonus(double x, double y,String type){
        this.type=type;
        if(type.equals("SHOTGUN_BONUS")){
            bonusImage=new Image(getClass().getResourceAsStream("shotgun.png"));
            bonusImageView=new ImageView(bonusImage);
            setTranslateX(x);
            setTranslateY(y);
            bonusImageView.setFitWidth(40);
            bonusImageView.setFitHeight(40);
            bonusImageView.setViewport(new Rectangle2D(0, 0, 512, 512));
        }
        if(type.equals("TWO_BONUS")){
            bonusImage=new Image(getClass().getResourceAsStream("two.png"));
           bonusImageView=new ImageView(bonusImage);
           setTranslateX(x);
           setTranslateY(y);
           bonusImageView.setFitWidth(60);
            bonusImageView.setFitHeight(40);
            bonusImageView.setViewport(new Rectangle2D(0, 0, 1023, 385));
        }
        getChildren().add(bonusImageView);
        Game.bonuses.add(this);
        Game.gameRoot.getChildren().add(this);
        System.out.println(Game.bonuses.size());
    }

    public String getType() {
        return type;
    }

    public ImageView getBonusImageView() {
        return bonusImageView;
    }

    public void setBonusImageView(ImageView bonusImageView) {
        this.bonusImageView = bonusImageView;
    }
}

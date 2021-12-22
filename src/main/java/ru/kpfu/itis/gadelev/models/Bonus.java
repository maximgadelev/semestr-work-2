package ru.kpfu.itis.gadelev.models;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import ru.kpfu.itis.gadelev.views.GameView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class Bonus extends Pane {
    String type;
    Image bonusImage;
    ImageView bonusImageView;
    public Bonus(double x, double y,String type) throws FileNotFoundException {
        this.type=type;
        if(type.equals("SHOTGUN_BONUS")){
            bonusImage=new Image(new FileInputStream("src/main/java/ru/kpfu/itis/gadelev/images/shotgun.png"));
            bonusImageView=new ImageView(bonusImage);
            setTranslateX(x);
            setTranslateY(y);
            bonusImageView.setFitWidth(40);
            bonusImageView.setFitHeight(40);
            bonusImageView.setViewport(new Rectangle2D(0, 0, 512, 512));
        }
        if(type.equals("TWO_BONUS")){
            bonusImage=new Image(new FileInputStream("src/main/java/ru/kpfu/itis/gadelev/images/two.png"));
           bonusImageView=new ImageView(bonusImage);
           setTranslateX(x);
           setTranslateY(y);
           bonusImageView.setFitWidth(60);
            bonusImageView.setFitHeight(40);
            bonusImageView.setViewport(new Rectangle2D(0, 0, 1023, 385));
        }if(type.equals("HP_BONUS")){
            bonusImage=new Image(new FileInputStream("src/main/java/ru/kpfu/itis/gadelev/images/HPP.png"));
            bonusImageView=new ImageView(bonusImage);
            setTranslateX(x);
            setTranslateY(y);
            bonusImageView.setFitWidth(40);
            bonusImageView.setFitHeight(40);
            bonusImageView.setViewport(new Rectangle2D(0, 0, 2000, 2000));
        }
        javafx.application.Platform.runLater(()->{
            getChildren().add(bonusImageView);
        });
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

package com.company;


import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;


public class Bonus extends Rectangle {
    String type;
    javafx.scene.paint.Paint color;
    public Bonus(double x, double y, double width, double height, String type, javafx.scene.paint.Paint color){
        super(x,y,width,height);
        this.color=color;
        setFill(color);
        this.type=type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Paint getColor() {
        return color;
    }

    public void setColor(Paint color) {
        this.color = color;
    }
}

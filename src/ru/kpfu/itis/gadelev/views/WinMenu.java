package ru.kpfu.itis.gadelev.views;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;


public class WinMenu {
    String title = "WinMenu";
    AnchorPane anchorPane=null;
    VBox vbox;
    Button button;
    Stage stage;
    String winner;

    public WinMenu(Stage stage,String winner){
        this.stage=stage;
        this.winner=winner;
        init();
    }
    private final EventHandler<javafx.event.ActionEvent> closeEvent = new EventHandler<>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            if (button == actionEvent.getSource()) {
                stage.close();
            }
        }
    };
    public void init(){
        javafx.application.Platform.runLater(()-> {
            anchorPane = new AnchorPane();
            vbox=new VBox(15);
            Font font =Font.font("Courier New", FontWeight.BLACK,20);
            button=new Button("End");
            button.setMaxHeight(100);
            button.setMaxWidth(200);
            button.setFont(font);
            button.setOnAction(closeEvent);
            Label label=new Label(winner + "WIN!!!");
            label.setFont(font);
            label.setTextFill(Color.RED);
            vbox.getChildren().add(label);
            vbox.getChildren().add(button);
            AnchorPane.setTopAnchor(vbox,5.0);
            AnchorPane.setLeftAnchor(vbox,10.0);
            AnchorPane.setRightAnchor(vbox,10.0);
            anchorPane.getChildren().add(vbox);
            Scene scene=new Scene(anchorPane,2,100);
            stage.setScene(scene);
            stage.show();
        } );
    }


}

package ru.kpfu.itis.gadelev.views;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import ru.kpfu.itis.gadelev.game.Game;

import java.io.IOException;

public class MenuView extends View {

    private String title = "Menu";
    private AnchorPane pane = null;
    private VBox vBox;
    public Button singlePlayer;
    public Button multiPlayer;
    public Button scores;

    public String getTitle() {
        return title;
    }

    private final EventHandler<ActionEvent> singlePlayerEvent = new EventHandler<>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            if (singlePlayer == actionEvent.getSource()) {
                application.startSingleGame();
            }
        }
    };
    private final EventHandler<ActionEvent> multiPlayerEvent=new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            if(multiPlayer==actionEvent.getSource()){
                application.startmultiGame();
            }
        }
    };

    private final EventHandler<ActionEvent> scoresEvent = new EventHandler<>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            if (scores == actionEvent.getSource()) {
                ScoresView scoresView = null;
                try {
                    scoresView = new ScoresView();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    scoresView.createView();
                    application.setView(scoresView);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    };



    private final Game application = getApplication();

    public MenuView() throws Exception {
    }


    @Override
    public Parent getView() {
        if (pane == null) {
            this.createView();
        }
        return pane;
    }

    public void createView() {
        pane = new AnchorPane();

        vBox = new VBox(5);


        Font font = Font.font("Courier New", FontWeight.BOLD, 20);

        singlePlayer = new Button("singlePlayer");
        singlePlayer.setOnAction(singlePlayerEvent);

        singlePlayer.setMaxWidth(1000);
        singlePlayer.setMaxHeight(2000);
        singlePlayer.setFont(font);

        scores = new Button("scores");
        scores.setOnAction(scoresEvent);

        scores.setMaxWidth(1000);
        scores.setMaxHeight(2000);
        scores.setFont(font);

        multiPlayer=new Button("multiPlayer");
        multiPlayer.setOnAction(multiPlayerEvent);
        multiPlayer.setMaxWidth(1000);
        multiPlayer.setMaxHeight(2000);
        multiPlayer.setFont(font);
//
//        chat = new Button("chat");
//        chat.setOnAction(chatEvent);
//
//        chat.setMaxWidth(1000);
//        chat.setMaxHeight(2000);
//        chat.setFont(font);
//
//        skin = new Button("skin");
//        skin.setOnAction(skinEvent);
//
//        skin.setMaxWidth(1000);
//        skin.setMaxHeight(2000);
//        skin.setFont(font);
//
//        exit = new Button("exit");
//        exit.setOnAction(exitEvent);
//
//        exit.setMaxWidth(1000);
//        exit.setMaxHeight(2000);
//        exit.setFont(font);
//
        vBox.getChildren().addAll(singlePlayer, scores,multiPlayer);
        AnchorPane.setTopAnchor(vBox, 5.0);
        AnchorPane.setLeftAnchor(vBox, 10.0);
        AnchorPane.setRightAnchor(vBox, 10.0);
        pane.getChildren().add(vBox);
        application.getStage().setWidth(600);
        application.getStage().setHeight(600);
    }


}

package ru.kpfu.itis.gadelev;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class MenuView extends View {

    private String title = "Menu";
    private AnchorPane pane = null;
    private VBox vBox;
    public Button singlePlayer;

    public String getTitle() {
        return title;
    }

    private final EventHandler<ActionEvent> singlePlayerEvent = new EventHandler<>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            if (singlePlayer == actionEvent.getSource()) {
                application.startGame();
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

//        multiplayer = new Button("multiplayer");
//        multiplayer.setOnAction(multiplayerEvent);
//
//        multiplayer.setMaxWidth(1000);
//        multiplayer.setMaxHeight(2000);
//        multiplayer.setFont(font);
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
//        vBox.getChildren().addAll(singlePlayer, multiplayer, chat, skin, exit);
//
vBox.getChildren().add(singlePlayer);
        AnchorPane.setTopAnchor(vBox, 5.0);
        AnchorPane.setLeftAnchor(vBox, 10.0);
        AnchorPane.setRightAnchor(vBox, 10.0);
        pane.getChildren().add(vBox);
        application.getStage().setWidth(600);
        application.getStage().setHeight(600);
    }


}

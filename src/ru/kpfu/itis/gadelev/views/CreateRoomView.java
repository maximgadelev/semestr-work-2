package ru.kpfu.itis.gadelev.views;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import ru.kpfu.itis.gadelev.game.Game;
import ru.kpfu.itis.gadelev.server.Client;
import ru.kpfu.itis.gadelev.server.GameServer;
import ru.kpfu.itis.gadelev.server.GameStarter;

public class CreateRoomView extends View {

    public String title = "Create room";
    private int lvlNumber = 1 ;

    private AnchorPane pane = null;

    private VBox createRoomBox;

    private Button createRoomButton;
    private TextArea port;
    private final Game application = getApplication();


    private final EventHandler<ActionEvent> createRoomEvent = new EventHandler<>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            if (createRoomButton == actionEvent.getSource()) {


                GameServer gameServer = new GameServer(Integer.parseInt(port.getText()));
                application.setGameServer(gameServer);
                new Thread(new GameStarter(gameServer)).start();
                application.client = new Client(application);

                try {
                    application.startClient(Integer.parseInt(port.getText()));
                    application.setView(new WaitOpponentView(Integer.parseInt(port.getText())));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    };

    public CreateRoomView() throws Exception {
    }

    @Override
    public Parent getView() {
        if (pane == null) {
            this.createView();
        }

        return pane;
    }

    @Override
    public String getTitle() {
        return title;
    }

    private void createView() {
        createRoomButton = new Button("create room");
        createRoomButton.setOnAction(createRoomEvent);
        pane = new AnchorPane();
        createRoomBox = new VBox(5);
        port = new TextArea();
        port.setPromptText("port");
        createRoomBox.getChildren().addAll( port, createRoomButton);
        AnchorPane.setLeftAnchor(createRoomBox, 30.0);
        AnchorPane.setTopAnchor(createRoomBox, 30.0);

        pane.getChildren().add(createRoomBox);


        application.getStage().setWidth(450);
        application.getStage().setHeight(400);

    }

}


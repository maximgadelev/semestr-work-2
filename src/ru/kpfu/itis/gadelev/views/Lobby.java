package ru.kpfu.itis.gadelev.views;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ru.kpfu.itis.gadelev.game.Game;
import ru.kpfu.itis.gadelev.server.Client;

import java.io.IOException;

public class Lobby extends View {
    public String title = "Lobby";

    private AnchorPane pane = null;
    private HBox hBox;
    private Button createRoomButton;
    private Button connectButton;
    private VBox createRoomBox;
    private TextArea portTextArea;

    private final Game application = getApplication();

    private final EventHandler<ActionEvent> createRoomEvent = new EventHandler<>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            if (createRoomButton == actionEvent.getSource()) {
                try {
                    application.setView(new CreateRoomView());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    };

    private final EventHandler<ActionEvent> connectButtonEvent = new EventHandler<>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            if (connectButton == actionEvent.getSource()) {

                application.client = new Client(application);

                try {
                    application.startClient(Integer.parseInt(portTextArea.getText()));
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                AnimationTimer timer = new AnimationTimer() {
                    @Override
                    public void handle(long now) {

                        try {
                            application.startmultiGame();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        this.stop();
                    }
                };
                timer.start();

            }

        }
    };



    public Lobby() throws Exception {
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
        pane = new AnchorPane();

        createRoomBox = new VBox(5);
        createRoomBox.setMinWidth(150);
        createRoomBox.setMaxWidth(150);
        AnchorPane.setLeftAnchor(createRoomBox, 15.0);
        AnchorPane.setTopAnchor(createRoomBox, 15.0);

        createRoomButton = new Button("Create room");
        createRoomButton.setOnAction(createRoomEvent);

        connectButton = new Button("connect");
        connectButton.setOnAction(connectButtonEvent);
        portTextArea = new TextArea();
        portTextArea.setPromptText("port");

        portTextArea.setMaxHeight(25);
        portTextArea.setMinHeight(25);
        createRoomBox.getChildren().addAll(createRoomButton, portTextArea, connectButton);
        pane.getChildren().add(createRoomBox);


        application.getStage().setWidth(450);
        application.getStage().setHeight(200);
    }
}

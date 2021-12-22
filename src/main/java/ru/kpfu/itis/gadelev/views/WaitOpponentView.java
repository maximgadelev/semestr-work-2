package ru.kpfu.itis.gadelev.views;

import javafx.animation.AnimationTimer;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import ru.kpfu.itis.gadelev.game.Game;

class WaitOpponentView extends View {

    public String title = "Wait opponent";


    private AnchorPane pane = null;

    private VBox createRoomBox;

    private Text wait;

    private final Game application = getApplication();

    private int port;


    public WaitOpponentView(int port) throws Exception {
        this.port = port;
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

        wait = new Text("Waiting for opponent \nPort " + port);
        createRoomBox.getChildren().addAll(wait);
        AnchorPane.setLeftAnchor(createRoomBox, 30.0);
        AnchorPane.setTopAnchor(createRoomBox, 30.0);

        pane.getChildren().add(createRoomBox);


        application.getStage().setWidth(450);
        application.getStage().setHeight(400);


        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {

                if (application.gameServer.getClients().size() == 2) {
                    try {
                        application.startmultiGame();
                        this.stop();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        timer.start();

    }
}

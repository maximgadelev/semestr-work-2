package ru.kpfu.itis.gadelev.views;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import ru.kpfu.itis.gadelev.game.Game;

import java.io.IOException;

public class ScoresView extends View {
    private String title = "Scores";
    private AnchorPane pane = null;
    private Button backButton;
    private final Game application = getApplication();
    @Override
    public Parent getView() {
        if(pane==null){
            try {
                this.createView();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return pane;
    }

    @Override
    public String getTitle() {
        return title;
    }

    public ScoresView() throws Exception {
    }
    private final EventHandler<ActionEvent> backEvent = new EventHandler<>() {
        public void handle(ActionEvent actionEvent) {
            if (backButton == actionEvent.getSource()) {
                try {
                    application.backToMenu();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    };
    void createView() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/playerList.fxml"));
        pane=loader.load();
        Scene scene = new Scene(pane);
        backButton = new Button("Back");
        backButton.setOnAction(backEvent);
        pane.getChildren().add(backButton);
       application.getStage().setTitle(getTitle());
        application.getStage().setScene(scene);

    }
}

package ru.kpfu.itis.gadelev.views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import ru.kpfu.itis.gadelev.game.Game;

import java.io.FileInputStream;
import java.io.IOException;

public class ScoresView extends View {
    private String title = "Scores";
    private AnchorPane pane = null;
    private final Game application = getApplication();
    @Override
    public Parent getView() {
        return null;
    }

    @Override
    public String getTitle() {
        return title;
    }

    public ScoresView() throws Exception {
    }

    void createView() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("playerList.fxml"));
        pane=loader.load();
        Scene scene = new Scene(pane);
        scene.getStylesheets().add("/users.css");
       application.getStage().setTitle(getTitle());
        application.getStage().setScene(scene);
        application.getStage().show();
    }
}

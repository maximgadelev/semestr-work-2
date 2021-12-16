package ru.kpfu.itis.gadelev.game;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import ru.kpfu.itis.gadelev.views.GameView;
import ru.kpfu.itis.gadelev.views.MenuView;
import ru.kpfu.itis.gadelev.views.View;

import java.io.IOException;


public class Game extends Application {
    Stage stage;

    private BorderPane rootLayout;


    private MenuView menuView;

    private GameView gameView;


    public MenuView getMenuView() {
        return menuView;
    }

    public Stage getStage() {
        return stage;
    }

    public Scene scene;

    public void setApplicationSize(int width, int height) {
        this.getStage().setWidth(width);
        this.getStage().setHeight(height);
    }

    public void setApplicationWidth(int width) {
        this.getStage().setWidth(width);
    }

    public void setApplicationHeight(int height) {
        this.getStage().setHeight(height);
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        this.stage.setTitle("Log in");
        this.stage.setOnCloseRequest(e -> System.exit(0));

        View.setApplication(this);

        this.menuView = new MenuView();
        this.gameView = new GameView();
        this.initLayout();

    }

    private void initLayout() {
        rootLayout = new BorderPane();
        scene = new Scene(rootLayout, 400, 600);
        stage.setScene(scene);
        stage.show();
        this.setView(getMenuView());
    }

    public void startGame() {
        this.stage.setTitle("Game");
        try {
            if (gameView.isCreate) {
                GameView.gameRoot.getChildren().clear();
                GameView.appRoot.getChildren().clear();
                GameView.characters.clear();
                gameView.player=null;
                GameView.appRoot.getChildren().removeAll(GameView.gameRoot);
            }
            gameView.createView();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setView(View view) {
        this.stage.setTitle(view.getTitle());
        rootLayout.setCenter(view.getView());
    }

    public void endGame() throws Exception {
        this.gameView=null;
        GameView.gameRoot.getChildren().clear();
        GameView.appRoot.getChildren().clear();
        GameView.appRoot=new Pane();
        GameView.gameRoot=new Pane();
        GameView.characters.clear();
        this.gameView = new GameView();
        stage.setScene(scene);
        stage.setWidth(220);
        stage.setHeight(285);
        stage.show();
        this.setView(getMenuView());
    }


    public static void main(String[] args) {
        launch(args);
    }


}


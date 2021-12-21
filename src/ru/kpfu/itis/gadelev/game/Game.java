package ru.kpfu.itis.gadelev.game;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import ru.kpfu.itis.gadelev.dataBaseModel.Player;
import ru.kpfu.itis.gadelev.models.Bullet;
import ru.kpfu.itis.gadelev.server.Client;
import ru.kpfu.itis.gadelev.server.GameServer;
import ru.kpfu.itis.gadelev.views.*;

import java.io.IOException;


public class Game extends Application {
    Stage stage;

    private BorderPane rootLayout;


    private MenuView menuView;

    private GameView gameView;

    SigninView signinView;

    ScoresView scoresView;

    public MenuView getMenuView() {
        return menuView;
    }

    public Stage getStage() {
        return stage;
    }

    public Scene scene;

    Player currentPlayer;

    public Client client;

    public GameServer gameServer;
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
        this.signinView=new SigninView();
        this.scoresView=new ScoresView();
        this.initLayout();

    }

    private void initLayout() {
        rootLayout = new BorderPane();
        scene = new Scene(rootLayout, 400, 600);
        stage.setScene(scene);
        stage.show();
        this.setView(signinView);
    }

    public void startSingleGame() {
        this.stage.setTitle("Game");
        try {
            if (gameView.isCreate) {
                GameView.gameRoot.getChildren().clear();
                GameView.appRoot.getChildren().clear();
                GameView.gameRoot.getChildren().removeAll(GameView.bonuses);
                GameView.bonuses.clear();
                GameView.bots.clear();
                GameView.characters.clear();
                gameView.player=null;
                GameView.appRoot.getChildren().removeAll(GameView.gameRoot);
                GameView.appRoot=null;
                GameView.gameRoot=new Pane();
                GameView.appRoot=new Pane();
            }
            gameView.createView("SINGLE");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void startmultiGame() throws Exception {
        this.stage.setTitle("Game");
        try {
            if (gameView.isCreate) {
                GameView.gameRoot.getChildren().clear();
                GameView.appRoot.getChildren().clear();
                GameView.characters.clear();
                gameView.player=null;
                GameView.appRoot.getChildren().removeAll(GameView.gameRoot);
            }

            gameView.createView("MULTI");
            gameView.setType("MULTI");
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
        Bullet.killed=0;
        GameView.bots.clear();
        this.gameView = new GameView();
        stage.setScene(scene);
        stage.setWidth(220);
        stage.setHeight(285);
        stage.show();
        gameView.isCreate=false;
        this.setView(getMenuView());
    }

public void backToMenu(){
      this.scoresView=null;
      stage.setScene(scene);
      stage.show();
    this.getStage().setWidth(600);
    this.getStage().setHeight(600);
        this.setView(getMenuView());
}
    public static void main(String[] args) {
        launch(args);
    }

    public SigninView getSigninView() {
        return signinView;
    }

    public  Player getCurrentPlayer() {
        return currentPlayer;
    }

    public  void setCurrentPlayer(Player currentPlayer) {
      this.currentPlayer
                = currentPlayer;
    }
    public GameView getGameView() {
        return gameView;
    }

    public Client getClient() {
        return client;
    }

    public void setGameServer(GameServer gameServer) {
        this.gameServer = gameServer;
    }
    public void startClient(int port) throws IOException {
        getClient().start(port);
    }

}


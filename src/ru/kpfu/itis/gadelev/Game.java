package ru.kpfu.itis.gadelev;
import  javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.FileNotFoundException;


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



    public void setApplicationSize(int width, int height){
        this.getStage().setWidth(width);
        this.getStage().setHeight(height);
    }

    public void setApplicationWidth(int width){
        this.getStage().setWidth(width);
    }

    public void setApplicationHeight(int height){
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

        Scene scene = new Scene(rootLayout, 400, 600);
        stage.setScene(scene);
        stage.show();

        this.setView(getMenuView());
//        startGame();
    }

    public void startGame(){
        this.stage.setTitle("Game");
        try {
            gameView.createView();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void setView(View view) {
        this.stage.setTitle(view.getTitle());
        rootLayout.setCenter(view.getView());
    }


    public static void main(String[] args) {
        launch(args);
    }



}


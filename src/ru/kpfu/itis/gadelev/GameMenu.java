package ru.kpfu.itis.gadelev;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import static ru.kpfu.itis.gadelev.View.getApplication;

public class GameMenu extends Pane {
    private final Game game = getApplication();
    private GameView gameView;
    private BorderPane menu;

    Button exit;
    Button continueLvl;
    Button restart;
    VBox vBox;

    Font font = Font.font("Courier New", FontWeight.BOLD, 30);

    public GameMenu(GameView gameView) throws Exception {
        this.gameView=gameView;
    }
    private final EventHandler<ActionEvent> exitButtonEvent = new EventHandler<>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            if (exit == actionEvent.getSource()) {
                try {
                    game.endGame();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    };

    private final EventHandler<ActionEvent> continueLvlButtonEvent = new EventHandler<>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            if (continueLvl == actionEvent.getSource()) {
                gameView.hideMenu();
            }
        }
    };

    private final EventHandler<ActionEvent> restartButtonEvent = new EventHandler<>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            if (restart == actionEvent.getSource()) {
               game.startGame();
            }
        }
    };
    public BorderPane createMenu() {

        exit = new Button("exit");
        exit.setFont(font);
        exit.setPrefSize(250, 50);
        exit.setOnAction(exitButtonEvent);

        continueLvl = new Button("continue");
        continueLvl.setFont(font);
        continueLvl.setPrefSize(250, 50);
        continueLvl.setOnAction(continueLvlButtonEvent);

        restart = new Button("restart");
        restart.setFont(font);
        restart.setPrefSize(250, 50);
        restart.setOnAction(restartButtonEvent);

        vBox = new VBox(15);

        vBox.getChildren().addAll(restart, continueLvl, exit);
        vBox.setAlignment(Pos.CENTER);

        menu = new BorderPane(vBox);
        menu.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);");

        menu.setPrefSize(1100, 686);

        return menu;
    }

}

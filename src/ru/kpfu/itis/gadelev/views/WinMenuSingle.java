package ru.kpfu.itis.gadelev.views;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import ru.kpfu.itis.gadelev.dao.PlayerDao;
import ru.kpfu.itis.gadelev.dao.impl.PlayerDaoImpl;
import ru.kpfu.itis.gadelev.dataBaseModel.Player;
import ru.kpfu.itis.gadelev.game.Game;
import ru.kpfu.itis.gadelev.models.Bullet;

public class WinMenuSingle {
    String title = "WinMenu";
    AnchorPane anchorPane=null;
    VBox vbox;
    Button button;
    Stage stage;
    Game game = View.getApplication();
    String winner;
    PlayerDao<Player> playerPlayerDao = new PlayerDaoImpl();
    public WinMenuSingle(Stage stage,String winner) throws Exception {
        this.stage=stage;
        this.winner=winner;
        init();
    }
    private final EventHandler<ActionEvent> closeEvent = new EventHandler<>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            if (button == actionEvent.getSource()) {
                try {
                    game.getStage().close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    };
    public void init(){
        javafx.application.Platform.runLater(()-> {
            anchorPane = new AnchorPane();
            vbox=new VBox(15);
            Font font =Font.font("Courier New", FontWeight.BLACK,20);
            button=new Button("End");
            button.setMaxHeight(100);
            button.setMaxWidth(200);
            button.setFont(font);
            button.setOnAction(closeEvent);
            Player player=playerPlayerDao.getByNickName(winner);
            playerPlayerDao.updateSingleScore(player.getId(), Bullet.killed+player.getSingleScore());
            Label label=new Label("Good!You killed " + Bullet.killed + " bots" );
            label.setFont(font);
            label.setTextFill(Color.RED);
            vbox.getChildren().add(label);
            vbox.getChildren().add(button);
            AnchorPane.setTopAnchor(vbox,5.0);
            AnchorPane.setLeftAnchor(vbox,10.0);
            AnchorPane.setRightAnchor(vbox,10.0);
            anchorPane.getChildren().add(vbox);
            Scene scene=new Scene(anchorPane,2,100);
            stage.setScene(scene);
            stage.show();
        } );
    }

}

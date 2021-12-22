package ru.kpfu.itis.gadelev.views;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import ru.kpfu.itis.gadelev.dao.PlayerDao;
import ru.kpfu.itis.gadelev.dao.impl.PlayerDaoImpl;
import ru.kpfu.itis.gadelev.dataBaseModel.Player;
import ru.kpfu.itis.gadelev.dto.PlayerDto;
import ru.kpfu.itis.gadelev.game.Game;
import ru.kpfu.itis.gadelev.service.PlayerService;
import ru.kpfu.itis.gadelev.service.impl.PlayerServiceImpl;


public class SigninView extends View {
    private String title = "Sign in";
    private AnchorPane pane = null;
    private TextField nicknameField;
    private Button signInButton;
    private VBox vBox;
    private final Game application = getApplication();
    private final PlayerService<PlayerDto> playerService=new PlayerServiceImpl();
    private final PlayerDao<Player> playerPlayerDao=new PlayerDaoImpl();

    public SigninView() throws Exception {
    }
    private final EventHandler<ActionEvent> signInEvent = new EventHandler<>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            if (signInButton == actionEvent.getSource()) {
                PlayerDto player = playerService.getByNickName(nicknameField.getText());
                if (player == null) {
                    System.out.println( playerService.save(new Player(nicknameField.getText())));
                    application.setCurrentPlayer(playerPlayerDao.getByNickName(nicknameField.getText()));
                    application.setApplicationHeight(230);
                    application.setView(application.getMenuView());
                } else {
                    application.setCurrentPlayer(playerPlayerDao.getByNickName(nicknameField.getText()));
                    application.setView(application.getMenuView());
                    nicknameField.setText("");
                }
            }

            }
        };

    @Override
    public Parent getView() {
       if(pane==null){
           this.createView();
       }
       return pane;
    }

    @Override
    public String getTitle() {
        return null;
    }
    private void createView() {
        pane = new AnchorPane();

        vBox = new VBox(5);

        Label nicknameLabel = new Label("Nickname");
        nicknameField = new TextField();



        signInButton = new Button("Go");
        signInButton.setOnAction(signInEvent);

        vBox.getChildren().addAll(nicknameLabel, nicknameField, signInButton);

        AnchorPane.setTopAnchor(vBox, 5.0);
        AnchorPane.setLeftAnchor(vBox, 10.0);
        AnchorPane.setRightAnchor(vBox, 10.0);
        pane.getChildren().add(vBox);
        application.setApplicationSize(235, 210);

    }

}

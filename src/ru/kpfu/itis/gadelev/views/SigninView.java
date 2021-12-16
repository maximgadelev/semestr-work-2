package ru.kpfu.itis.gadelev.views;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import ru.kpfu.itis.gadelev.dataBaseModel.Player;
import ru.kpfu.itis.gadelev.dto.PlayerDto;
import ru.kpfu.itis.gadelev.game.Game;
import ru.kpfu.itis.gadelev.service.PlayerService;
import ru.kpfu.itis.gadelev.service.impl.PlayerServiceImpl;


public class SigninView extends View {
    private String title = "Sign in";
    private AnchorPane pane = null;
    private TextField nicknameField;
    private Text signInExceptionArea;
    private Button signInButton;
    private VBox vBox;
    private final Game application = getApplication();
    private final PlayerService<PlayerDto> playerService=new PlayerServiceImpl();

    public SigninView() throws Exception {
    }
    private final EventHandler<ActionEvent> signInEvent = new EventHandler<>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            if (signInButton == actionEvent.getSource()) {
                PlayerDto player = playerService.getByNickName(nicknameField.getText());
                if (player == null) {
                    signInExceptionArea.setText("No player with this nickname.");
                    System.out.println( playerService.save(new Player(nicknameField.getText())));
                    System.out.println(playerService.getByNickName(nicknameField.getText()));
                    application.setApplicationHeight(230);
                    application.setView(application.getMenuView());
                } else {
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
        signInExceptionArea = new Text();



        signInButton = new Button("start");
        signInButton.setOnAction(signInEvent);

        vBox.getChildren().addAll(nicknameLabel, nicknameField, signInButton, signInExceptionArea);

        AnchorPane.setTopAnchor(vBox, 5.0);
        AnchorPane.setLeftAnchor(vBox, 10.0);
        AnchorPane.setRightAnchor(vBox, 10.0);

        pane.getChildren().add(vBox);

        application.setApplicationSize(235, 210);

    }

}

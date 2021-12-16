package ru.kpfu.itis.gadelev.helpers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import ru.kpfu.itis.gadelev.dataBaseModel.Player;

import java.util.stream.Collectors;

public class PlayerController {
    @FXML
    private TextField nickname;

    @FXML
    private Button search;

    @FXML
    private TableView tableView;

    @FXML
    private Label label;

    @FXML
    private VBox users;

    private final ObservableList<Player> userList = FXCollections.observableArrayList();
    private ObservableList<Player> resultList = FXCollections.observableArrayList();

    public PlayerController() {
        userList.add(new Player( 1,"Ivan",50, 50));
        userList.add(new Player( 2,"Petr", 50,60));
        userList.add(new Player( 3,"Artem", 4,70));
    }

    @FXML
    private void initialize() {
        search.setText("Search");
        search.setStyle("-fx-background-color: #ff0000");

        search.setOnAction(event -> loadData());

        nickname.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                loadData();
            }
        });

        nickname.textProperty().addListener(((observable, oldValue, newValue) ->
                label.setText(newValue)
        ));

        initTable();
    }

    private void loadData() {
        String searchText = nickname.getText();

        Task<ObservableList<Player>> task = new Task<ObservableList<Player>>() {
            @Override
            protected ObservableList<Player> call() {
                return FXCollections.observableArrayList(
                        userList.stream()
                                .filter(u -> u.getNickName().toLowerCase().contains(searchText.toLowerCase()))
                                .collect(Collectors.toList())
                );
            }
        };


        task.setOnSucceeded(event -> {
            resultList = task.getValue();
            tableView.setItems(resultList);
        });

        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    private void initTable() {
        tableView = new TableView(FXCollections.observableList(userList));
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn id = new TableColumn("ID");
        id.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn nickname = new TableColumn("NICKNAME");
        nickname.setCellValueFactory(new PropertyValueFactory<>("nickName"));

        TableColumn points = new TableColumn("POINTS");
        points.setCellValueFactory(new PropertyValueFactory<>("singleScore"));

        tableView.getColumns().addAll(id, nickname, points);

        users.getChildren().add(tableView);
    }
}

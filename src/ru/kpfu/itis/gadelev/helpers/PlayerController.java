package ru.kpfu.itis.gadelev.helpers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import ru.kpfu.itis.gadelev.dao.PlayerDao;
import ru.kpfu.itis.gadelev.dao.impl.PlayerDaoImpl;
import ru.kpfu.itis.gadelev.dataBaseModel.Player;
import ru.kpfu.itis.gadelev.dto.PlayerDto;
import ru.kpfu.itis.gadelev.service.PlayerService;
import ru.kpfu.itis.gadelev.service.impl.PlayerServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PlayerController {
    PlayerService<PlayerDto> playerService = new PlayerServiceImpl();
    PlayerDao<Player> playerPlayerDao = new PlayerDaoImpl();
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
        List<Player> players = playerPlayerDao.getAll();
        userList.addAll(players);
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
if(label!=null) {
    nickname.textProperty().addListener(((observable, oldValue, newValue) ->
            label.setText(newValue)
    ));
}

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

        TableColumn singleScore= new TableColumn("singleScore");
        singleScore.setCellValueFactory(new PropertyValueFactory<>("singleScore"));

        TableColumn multiScore = new TableColumn("multiScore");
        multiScore.setCellValueFactory(new PropertyValueFactory<>("multiScore"));
        tableView.getColumns().addAll(id, nickname, singleScore,multiScore);

        users.getChildren().add(tableView);
    }
}

package ru.kpfu.itis.gadelev.dao.impl;

import ru.kpfu.itis.gadelev.dao.PlayerDao;
import ru.kpfu.itis.gadelev.dataBaseModel.Player;
import ru.kpfu.itis.gadelev.helpers.PostgresConnectionHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PlayerDaoImpl implements PlayerDao<Player> {
    private final Connection connection = PostgresConnectionHelper.getConnection();

    @Override
    public boolean save(Player player) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO player (nickname) values (?);");
            preparedStatement.setString(1,player.getNickName());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }


    @Override
    public Player getByNickName(String nickname) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM player where nickname = ?");
            preparedStatement.setString(1, nickname);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Player player = new Player(
                        resultSet.getInt("id"),
                        resultSet.getString("nickname"),
                        resultSet.getInt("single_score"),
                        resultSet.getInt("multi_score")
                );
                return player;
            }
            return null;
        } catch (SQLException e) {
        }
        return null;
    }

    @Override
    public void updateSingleScore(int id, int score) {
        String sql="UPDATE player SET single_score = ? where id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,score);
            preparedStatement.setInt(2,id);
            preparedStatement.executeUpdate();
        }catch (SQLException sqlException){

        }
    }

    @Override
    public void updateMultiScore(int id, int score) {
        String sql="UPDATE player SET multi_score = ? where id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,score);
            preparedStatement.setInt(2,id);
            preparedStatement.executeUpdate();
        }catch (SQLException sqlException){

        }
    }
}

package ru.kpfu.itis.gadelev.service.impl;

import ru.kpfu.itis.gadelev.dao.PlayerDao;
import ru.kpfu.itis.gadelev.dao.impl.PlayerDaoImpl;
import ru.kpfu.itis.gadelev.dataBaseModel.Player;
import ru.kpfu.itis.gadelev.dto.PlayerDto;
import ru.kpfu.itis.gadelev.service.PlayerService;

import java.util.List;
import java.util.stream.Collectors;

public class PlayerServiceImpl implements PlayerService {
    private final PlayerDao<Player> playerDao = new PlayerDaoImpl();
    @Override
    public boolean save(Player player) {
        return playerDao.save(new Player(
                player.getId(),
                player.getNickName(),
                player.getSingleScore(),
                player.getMultiScore()));
    }

    @Override
    public PlayerDto getByNickName(String nickname) {
        Player player= playerDao.getByNickName(nickname);
        if(player !=null){
            return new PlayerDto(player.getId(),
                    player.getNickName(),
                    player.getSingleScore(),
                    player.getMultiScore());
        }else{
            return null;
        }
    }

    @Override
    public void updateSingleScore(int id, int score) {
playerDao.updateSingleScore(id,score);
    }

    @Override
    public void updateMultiScore(int id, int score) {
playerDao.updateMultiScore(id, score);
    }

    @Override
    public List<PlayerDto> getAll() {
        List<Player> players=playerDao.getAll();
        return players.stream().map(player -> new PlayerDto(
                player.getId(),
                player.getNickName(),
                player.getSingleScore(),
                player.getMultiScore()
        )).collect(Collectors.toList());
    }
}

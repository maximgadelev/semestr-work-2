package ru.kpfu.itis.gadelev.service;

import ru.kpfu.itis.gadelev.dataBaseModel.Player;
import ru.kpfu.itis.gadelev.dto.PlayerDto;

import java.util.List;

public interface PlayerService<T> {
    boolean save(Player player);
    PlayerDto getByNickName(String nickname);
    void updateSingleScore(int id,int score);
    void updateMultiScore(int id,int score);
    List<PlayerDto> getAll();
}

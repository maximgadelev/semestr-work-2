package ru.kpfu.itis.gadelev.dto;

import ru.kpfu.itis.gadelev.dataBaseModel.Player;

public class PlayerDto {
    private int id;
    private String nickName;
    private int singleScore;
    private int multiScore;

    public PlayerDto(int id, String nickName, int singleScore, int multiScore) {
        this.id = id;
        this.nickName = nickName;
        this.singleScore = singleScore;
        this.multiScore = multiScore;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getSingleScore() {
        return singleScore;
    }

    public void setSingleScore(int singleScore) {
        this.singleScore = singleScore;
    }

    public int getMultiScore() {
        return multiScore;
    }

    public void setMultiScore(int multiScore) {
        this.multiScore = multiScore;
    }
}

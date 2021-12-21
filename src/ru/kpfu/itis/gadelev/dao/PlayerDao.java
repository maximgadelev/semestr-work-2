package ru.kpfu.itis.gadelev.dao;

import java.util.List;

public interface PlayerDao<T> {
    boolean save(T t);
    T getByNickName(String nickname);
    void updateSingleScore(int id,int score);
    void updateMultiScore(int id,int score);
    List<T> getAll();

}

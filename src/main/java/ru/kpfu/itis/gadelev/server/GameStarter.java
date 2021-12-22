package ru.kpfu.itis.gadelev.server;

import java.io.IOException;

public class GameStarter implements Runnable {
    GameServer gameServer;

    public GameStarter(GameServer gameServer) {
        this.gameServer = gameServer;
    }

    @Override
    public void run() {
        try {
            gameServer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

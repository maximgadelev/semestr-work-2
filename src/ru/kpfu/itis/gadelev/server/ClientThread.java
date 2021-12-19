package ru.kpfu.itis.gadelev.server;

import ru.kpfu.itis.gadelev.game.Game;
import ru.kpfu.itis.gadelev.views.GameView;

import javax.xml.namespace.QName;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class ClientThread implements Runnable {
    private final BufferedReader input;
    private final BufferedWriter output;
    private final Client client;
    private GameView gameView = GameView.gameView;

    public ClientThread(BufferedReader input, BufferedWriter output, Client client) {
        this.input = input;
        this.output = output;
        this.client = client;
    }

    public BufferedReader getInput() {
        return input;
    }

    public BufferedWriter getOutput() {
        return output;
    }

    @Override
    public void run() {
        try {
            while (true) {
                String message = input.readLine();
                if (message != null) {
                    String[] directions = message.split(" ");
                    if (directions[0].equals("move")) {
                        client.getGame().getGameView().secondPlayerX = Double.parseDouble(directions[1]);
                        client.getGame().getGameView().secondPlayerY = Double.parseDouble(directions[2]);
                        client.getGame().getGameView().secondPlayerName = directions[3];
                    }
                    if (directions[0].equals("bullet")) {
                        client.getGame().getGameView().addBullets(Double.parseDouble(directions[1]), Double.parseDouble(directions[2]),Integer.parseInt(directions[3]),Integer.parseInt(directions[4]));

                    }
                    if(directions[0].equals("died")){
                      client.getGame().getGameView().deletePlayer(directions[1]);

                    }
                    if(directions[0].equals("win")){
                     client.getGame().getGameView().showWinMenu(client.game.getStage(),directions[1]);
                        System.out.println(directions[1]);
                    }
                    if(directions[0].equals("bonus")){
                        client.getGame().getGameView().spawnBonusToRoot(Double.parseDouble(directions[1]),Double.parseDouble(directions[2]),directions[3]);
                    }
                    if(directions[0].equals("taken")){
                        client.getGame().getGameView().removeBonus(directions[1]);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
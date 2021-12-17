package ru.kpfu.itis.gadelev.server;

import ru.kpfu.itis.gadelev.views.GameView;

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
                if(message!=null){
                    String [] directions =message.split(" ");
                if(directions[0].equals("new")){
gameView.createNewGamer(directions[1]);
                }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
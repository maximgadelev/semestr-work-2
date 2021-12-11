package ru.kpfu.itis.gadelev.server;

import ru.kpfu.itis.gadelev.GameView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class GameThread implements Runnable {
    private final BufferedReader input;
    private final BufferedWriter output;
    private final Client client;
    private final GameView game = GameView.getInstance();

    public GameThread(BufferedReader input, BufferedWriter output, Client client) {
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

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
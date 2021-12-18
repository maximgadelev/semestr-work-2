package ru.kpfu.itis.gadelev.server;
import ru.kpfu.itis.gadelev.game.Game;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Client {
     private Socket socket;
    private ClientThread clientThread;
    public Game game;

    public Game getGame() {
        return game;
    }
    public Client(Game game){
        this.game=game;
    }

    public void sendMessage(String name) {
        try {
            clientThread.getOutput().write(name);
            clientThread.getOutput().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() throws IOException {
        socket = new Socket("127.0.0.1", 5555);

        BufferedWriter output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
        BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));

        clientThread = new ClientThread(input, output, this);

        new Thread(clientThread).start();
    }
}

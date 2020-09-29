package server;

import java.net.ServerSocket;
import java.util.concurrent.Executors;

public class server {
	public static void main(String[] args) throws Exception {
        try (var listener = new ServerSocket(58901)) {
            System.out.println("Game Server is Running...");
            var pool = Executors.newFixedThreadPool(200);
            while (true) {
                Game game = new Game("Box");
                pool.execute(game.new Player(1, 1, 2, 2, listener.accept(), 'A'));
                pool.execute(game.new Player(40, 40, 41, 41, listener.accept(), 'B'));
            }
        }
    }
}
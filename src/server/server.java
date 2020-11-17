package server;

import java.net.ServerSocket;
import java.util.concurrent.Executors;

import server.Game.Player;
import template.Player1;
import template.Player2;

public class server {
	
	public static void main(String[] args) throws Exception {
        try (var listener = new ServerSocket(8080)) {
            System.out.println("Game Server is Running...");
            var pool = Executors.newFixedThreadPool(200);
            while (true) {
                Game game = new Game();
                Player p1 = new Player1(game, listener.accept());
                p1.setupPlayerTemplate();
                pool.execute(p1);
                Player p2 = new Player2(game, listener.accept());
                p2.setupPlayerTemplate();
                pool.execute(p2);
                
                game.attach(p1);
                game.attach(p2);
            }
        }
    }
}
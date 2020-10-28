package server;

import java.net.ServerSocket;
import java.util.concurrent.Executors;

import looks.BaseLooks;
import looks.*;
import looks.color.Blue;
import looks.color.Red;
import looks.color.White;
import server.Game.Player;

public class server {
	
	public static void main(String[] args) throws Exception {
        try (var listener = new ServerSocket(8080)) {
            System.out.println("Game Server is Running...");
            var pool = Executors.newFixedThreadPool(200);
            while (true) {
                Game game = new Game();
                Player p1 = game.new Player(1, 1, 4, 4, listener.accept(), 'A');
                pool.execute(p1);
                Player p2 = game.new Player(40, 40, 43, 43, listener.accept(), 'B');
                pool.execute(p2);
                
                game.attach(p1);
                game.attach(p2);

                p1.setLooks(new CornersDecorator(new CenterDecorator (new BaseLooks(game.former, p1), new White()), new Blue()));
                p2.setLooks(new CornersDecorator(new CenterDecorator (new BaseLooks(game.former, p2), new Blue()), new Red()));
            }
        }
    }
}
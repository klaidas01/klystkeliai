package state;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.Executors;

import server.Game;
import server.Player;
import template.Player1;
import template.Player2;

public class GameManager {

	public static void runServer() throws IOException {
		try (var listener = new ServerSocket(8080)) {
			System.out.println("Game Server is Running...");
            var pool = Executors.newFixedThreadPool(200);
            while (true) { 
            	Player p1 = new Player1(listener.accept());
            	p1.output.println("WELCOME P1");
            	p1.output.println("WAITING");
    			Player p2 = new Player2(listener.accept());
    			p2.output.println("WELCOME P2");
    			p2.output.println("WAITING");
    			Game game = new Game();
    			GameContextMediator mediator = new GameContextMediator();
    			GameContext context = new GameContext(pool, game, mediator);
    			mediator.setState(new LevelSelectState(p1,p2));
				mediator.execute(context);
            }
		}
	}

}

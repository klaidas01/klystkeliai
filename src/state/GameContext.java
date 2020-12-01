package state;

import java.io.IOException;
import java.util.concurrent.ExecutorService;

import server.Game;
import server.IObserver;

public class GameContext implements IObserver {
	private State state;
	public Game game;
	public ExecutorService pool;
	
	public GameContext(ExecutorService es, Game g) {
		pool = es;
		game = g;
	}
	
	public void setState(State newState) {
        state = newState;
    }
	
	public void execute() throws IOException {
		state.Handle(this);
	}

	@Override
	public void update(String msg) {
		var p1 = game.player1;
		var p2 = game.player1.opponent;
		if (msg == "GAME OVER") {
			game.shutdown = true;
			p1.output.println("WAITING");
			p2.output.println("WAITING");
			System.out.println(game.shutdown);
			System.out.println(p1.game.shutdown);
			System.out.println(p2.game.shutdown);
			game.timer.timer.cancel();
		}
		else if (msg == p1.name) {
			try {
				game = new Game();
				state = new LevelSelectState(p1, p2);
				execute();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}

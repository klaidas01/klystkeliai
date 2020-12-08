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
		if (msg == "GAME OVER") {
			try {
				state = new GameOverState();
				execute();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else if (msg == game.player1.name) {
			try {
				state = new InitNewGameState();
				execute();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}

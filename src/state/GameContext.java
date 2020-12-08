package state;

import java.io.IOException;
import java.util.concurrent.ExecutorService;

import server.Game;
import server.IObserver;

public class GameContext implements IObserver {
	private State state;
	public Game game;
	public ExecutorService pool;
	public InterfaceMediator gameMediator;

	public GameContext(ExecutorService es, Game g, InterfaceMediator mediator) {
		gameMediator=mediator;
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
		gameMediator.notify(this, msg, game);
	}
}

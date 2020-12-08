package state;

import java.io.IOException;

import server.Game;

public class InitNewGameState implements State {

	@Override
	public void Handle(GameContext context) throws IOException {
		var p1 = context.game.player1;
		var p2 = context.game.player1.opponent;
		try {
			context.game = new Game();
			context.setState(new LevelSelectState(p1, p2));
			context.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

package state;

import java.io.IOException;

public class GameOverState implements State {

	@Override
	public void Handle(GameContext context) throws IOException {
		var p1 = context.game.player1;
		var p2 = context.game.player1.opponent;
		context.game.shutdown = true;
		p1.output.println("WAITING");
		p2.output.println("WAITING");
		context.game.timer.timer.cancel();
	}

}

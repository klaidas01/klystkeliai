package state;

import java.io.IOException;

import server.Player;

public class RunGameState implements State {
	
	private Player player1;
	private Player player2;
	
	public RunGameState(Player p1, Player p2)
	{
		player1 = p1;
		player2 = p2;
	}

	@Override
	public void Handle(GameContext context) throws IOException {
		System.out.println("Running game");
		context.game.attach(context);
		player1.setGame(context.game);
		player2.setGame(context.game);
		player1.setupPlayerTemplate();
		player2.setupPlayerTemplate();
		context.pool.execute(player1);
		context.pool.execute(player2);
		context.game.attach(player1);
		context.game.attach(player2);
		player1.output.println("PLAY");
		player2.output.println("PLAY");
	}

}

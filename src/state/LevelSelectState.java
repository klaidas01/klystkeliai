package state;

import java.io.IOException;
import java.util.Scanner;

import server.Player;

public class LevelSelectState implements State {
	
	private Player player1;
	private Player player2;
	
	public LevelSelectState(Player p1, Player p2)
	{
		player1 = p1;
		player2 = p2;
	}

	@Override
	public void Handle(GameContext context) throws IOException {
		try {
		player1.output.println("LEVEL_SELECT");
		String levelName = "BOX";
		while(true)
		{
			System.out.println("Waiting for level");
			var input = new Scanner(player1.socket.getInputStream());
			levelName = input.next();
			System.out.println("Level received: " + levelName);
			if (levelName.contains("BOX") || levelName.contains("IBOX") || levelName.contains("CROSS"))
				break;
		}
		context.game.setLevel(levelName);
		context.setState(new RunGameState(player1, player2));
		context.execute();
		} catch (Exception e) {}
		}
	}

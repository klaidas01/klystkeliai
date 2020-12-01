package timing;

import java.util.TimerTask;

import movementStrategy.MovementNormalSpeed;
import server.Player;

public class RevertScoreMultiplier extends TimerTask {
	
	private Player player;
	
	public RevertScoreMultiplier(Player _player)
	{
		player = _player;
	}
	
	@Override
	public void run() {
		player.scoreCount -= 1;
		if(player.scoreCount == 0)
		player.scoreMultiplier = 1;
	}

}

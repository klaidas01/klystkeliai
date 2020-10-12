package timing;

import java.util.TimerTask;

import movementStrategy.MovementNormalSpeed;
import server.Game.Player;

public class RevertSpeed extends TimerTask {
	
	private Player player;
	
	public RevertSpeed(Player _player)
	{
		player = _player;
	}
	
	@Override
	public void run() {
		player.setMovementStrategy(new MovementNormalSpeed());
	}

}

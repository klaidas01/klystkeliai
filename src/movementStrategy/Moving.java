package movementStrategy;

import levels.ILevel;
import server.Game.Player;

public class Moving {
	private IMovementStrategy strategy;
	
	public Moving(IMovementStrategy s)
	{
		strategy = s;
	}
	
	public void move(ILevel level, Player player, char direction){
	      strategy.move(level, player, direction);
	}
}

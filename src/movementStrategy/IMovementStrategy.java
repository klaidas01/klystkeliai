package movementStrategy;

import levels.ILevel;
import server.Game.Player;

public interface IMovementStrategy {
	public void move(ILevel level, Player player, char direction);
}

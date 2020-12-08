package movementStrategy;

import levels.ILevel;
import server.Player;

public interface IMovementStrategy {
	public void move(ILevel level, Player player, char direction);
}

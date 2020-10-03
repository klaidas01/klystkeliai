package movementStrategy;

import levels.ILevel;
import server.Game.Player;
import server.Collision;

public class MovementNormalSpeed implements IMovementStrategy {

	@Override
	public void move(ILevel level, Player player, char direction) {
		if (player.opponent == null) {
            throw new IllegalStateException("Can't move without an opponent");
        } else if (direction == 'U') {
            player.northWestCoord.Y -= 2;
            player.southEastCoord.Y -= 2;
            if(Collision.doesCollideWithAny(level.getWalls(), player) || Collision.doesCollide(player, player.opponent))
            {
            	player.northWestCoord.Y += 1;
                player.southEastCoord.Y += 1;
                if(Collision.doesCollideWithAny(level.getWalls(), player) || Collision.doesCollide(player, player.opponent))
                {
                	player.northWestCoord.Y += 1;
                    player.southEastCoord.Y += 1;
                	//Reduce player score
                }
            }
        } else if (direction == 'D') {
        	player.northWestCoord.Y += 2;
            player.southEastCoord.Y += 2;
            if(Collision.doesCollideWithAny(level.getWalls(), player) || Collision.doesCollide(player, player.opponent))
            {
            	player.northWestCoord.Y -= 1;
                player.southEastCoord.Y -= 1;
                if(Collision.doesCollideWithAny(level.getWalls(), player) || Collision.doesCollide(player, player.opponent))
                {
                	player.northWestCoord.Y -= 1;
                    player.southEastCoord.Y -= 1;
                	//Reduce player score
                }
            }
        } else if (direction == 'R') {
        	player.northWestCoord.X += 2;
            player.southEastCoord.X += 2;
            if(Collision.doesCollideWithAny(level.getWalls(), player) || Collision.doesCollide(player, player.opponent))
            {
            	player.northWestCoord.X -= 1;
                player.southEastCoord.X -= 1;
                if(Collision.doesCollideWithAny(level.getWalls(), player) || Collision.doesCollide(player, player.opponent))
                {
                	player.northWestCoord.X -= 1;
                    player.southEastCoord.X -= 1;
                	//Reduce player score
                }
            }
        } else if (direction == 'L') {
        	player.northWestCoord.X -= 2;
            player.southEastCoord.X -= 2;
            if(Collision.doesCollideWithAny(level.getWalls(), player) || Collision.doesCollide(player, player.opponent))
            {
            	player.northWestCoord.X += 1;
                player.southEastCoord.X += 1;
                if(Collision.doesCollideWithAny(level.getWalls(), player) || Collision.doesCollide(player, player.opponent))
                {
                	player.northWestCoord.X += 1;
                    player.southEastCoord.X += 1;
                	//Reduce player score
                }
            }
        }
	}

}

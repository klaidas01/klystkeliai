package movementStrategy;

import levels.ILevel;
import moveCommand.Down;
import moveCommand.Left;
import moveCommand.RemoteControl;
import moveCommand.Right;
import moveCommand.Up;
import server.Player;
import server.Collision;

public class MovementHalfSpeed implements IMovementStrategy {
	
	private RemoteControl movement = new RemoteControl();

	@Override
	public void move(ILevel level, Player player, char direction) {
		if (player.opponent == null) {
            throw new IllegalStateException("Can't move without an opponent");
        } else if (direction == 'U') {
        	movement.addCommand(new Up(player, 1));
            if(Collision.doesCollideWithAny(level.getWalls(), player) || Collision.doesCollide(player, player.opponent))
            {
            	movement.undo();
                player.Score = (player.Score > 0) ? player.Score - 1 : player.Score;
                player.output.println("SCORE " + player.Score + ';' + player.opponent.Score);
        		player.opponent.output.println("SCORE " + player.opponent.Score + ';' + player.Score);
            }
        } else if (direction == 'D') {
        	movement.addCommand(new Down(player, 1));
            if(Collision.doesCollideWithAny(level.getWalls(), player) || Collision.doesCollide(player, player.opponent))
            {
            	movement.undo();
                player.Score = (player.Score > 0) ? player.Score - 1 : player.Score;
                player.output.println("SCORE " + player.Score + ';' + player.opponent.Score);
        		player.opponent.output.println("SCORE " + player.opponent.Score + ';' + player.Score);
            }
        } else if (direction == 'R') {
        	movement.addCommand(new Right(player, 1));
            if(Collision.doesCollideWithAny(level.getWalls(), player) || Collision.doesCollide(player, player.opponent))
            {
            	movement.undo();
                player.Score = (player.Score > 0) ? player.Score - 1 : player.Score;
                player.output.println("SCORE " + player.Score + ';' + player.opponent.Score);
        		player.opponent.output.println("SCORE " + player.opponent.Score + ';' + player.Score);
            }
        } else if (direction == 'L') {
        	movement.addCommand(new Left(player, 1));
            if(Collision.doesCollideWithAny(level.getWalls(), player) || Collision.doesCollide(player, player.opponent))
            {
            	movement.undo();
                player.Score = (player.Score > 0) ? player.Score - 1 : player.Score;
                player.output.println("SCORE " + player.Score + ';' + player.opponent.Score);
        		player.opponent.output.println("SCORE " + player.opponent.Score + ';' + player.Score);
            }
        }
	}

}

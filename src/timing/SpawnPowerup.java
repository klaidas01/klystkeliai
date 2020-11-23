package timing;

import java.util.List;
import java.util.Random;
import java.util.TimerTask;

import boardObjects.BoardObject;
import boardObjects.Food;
import boardObjects.PowerUpAbstractFactory;
import composite.BoardObjectComposite;
import enums.Constants;
import levels.ILevel;
import server.Collision;
import server.Game;
import server.Game.Player;
import server.MessageFormer;

public class SpawnPowerup extends TimerTask {
	
	private Game game;
	private PowerUpAbstractFactory factory;
	
	public SpawnPowerup(Game _game, PowerUpAbstractFactory _factory)
	{
		game = _game;
		factory = _factory;
	}
	
	@Override
	public void run() {
		if (game.powerupCount < Constants.POWERUP_COUNT)
    	{
    		int size = 1;
    		int x = game.rand.nextInt(Constants.ROWS_VALUE);
    		int y = game.rand.nextInt(Constants.ROWS_VALUE);
    		int random = game.rand.nextInt(2);
    		BoardObject newPowerUp;
    		if (random == 0) newPowerUp = factory.getNegativePowerUp(x, y, x, y);
    		else newPowerUp = factory.getPositivePowerUp(x, y, x, y);
    		if (!(Collision.doesCollideWithAny(game.currentLevel.getWalls(), newPowerUp) 
    				|| game.objectComposite.doesCollide(newPowerUp) != null))
    		{
    			game.objectComposite.add(newPowerUp);
    			game.powerupCount++;
    		}
    		game.former.newMessage();
    		game.objectComposite.draw(game.former);
    		game.player1.draw(game.former);
    		game.player1.opponent.draw(game.former);
    		game.player1.output.println("POS " + game.former.message);
    		game.player1.opponent.output.println("POS " + game.former.message);
    	}
	}

}

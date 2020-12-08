package timing;

import java.util.Random;
import java.util.TimerTask;

import boardObjects.Food;
import composite.BoardObjectComposite;
import enums.Constants;
import levels.ILevel;
import server.Collision;
import server.Game;
import server.Player;
import server.MessageFormer;

public class SpawnFood extends TimerTask {
	
	private Game game;
	
	public SpawnFood(Game _game)
	{
		game = _game;
	}

	@Override
	public void run() {
		if (game.foodCount < Constants.FOOD_COUNT)
    	{
    		int size = game.rand.nextInt(3);
    		int x = game.rand.nextInt(Constants.ROWS_VALUE);
    		int y = game.rand.nextInt(Constants.ROWS_VALUE);
    		Food newFood = new Food(x, y, x + size, y + size, size + 1);
    		if (!(Collision.doesCollideWithAny(game.currentLevel.getWalls(), newFood)
    				|| game.objectComposite.doesCollide(newFood) != null))
    		{
    			game.objectComposite.add(newFood);
    			game.foodCount++;
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

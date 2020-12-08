package timing;

import java.util.TimerTask;

import boardObjects.Food;
import boardObjects.MementoMoriPoison;
import enums.Constants;
import server.Collision;
import server.Game;

public class SpawnPoison extends TimerTask {
	private Game game;
	
	public SpawnPoison(Game _game)
	{
		game = _game;
	}

	@Override
	public void run() {
		if (game.poisonCount < Constants.FOOD_COUNT)
    	{
    		int x = game.rand.nextInt(Constants.ROWS_VALUE);
    		int y = game.rand.nextInt(Constants.ROWS_VALUE);
    		MementoMoriPoison newPoison = new MementoMoriPoison(x, y, x, y);
    		if (!(Collision.doesCollideWithAny(game.currentLevel.getWalls(), newPoison)
    				|| game.objectComposite.doesCollide(newPoison) != null))
    		{
    			game.objectComposite.add(newPoison);
    			game.poisonCount++;
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
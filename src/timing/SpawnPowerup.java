package timing;

import java.util.List;
import java.util.Random;
import java.util.TimerTask;

import boardObjects.BoardObject;
import boardObjects.Food;
import boardObjects.PowerUpAbstractFactory;
import enums.Constants;
import levels.ILevel;
import server.Collision;
import server.Game.Player;
import server.MessageFormer;

public class SpawnPowerup extends TimerTask {
	
	private List<BoardObject> powerupList;
	private List<Food> foodList;
	private Random rand;
	private ILevel currentLevel;
	private Player player;
	private MessageFormer former;
	private PowerUpAbstractFactory factory;
	
	public SpawnPowerup(List<BoardObject> _powerups, List<Food> _foodList, Random _rand, ILevel _level, Player _player, MessageFormer _former, PowerUpAbstractFactory _factory)
	{
		powerupList = _powerups;
		foodList = _foodList;
		rand = _rand;
		currentLevel = _level;
		player = _player;
		former = _former;
		factory = _factory;
	}
	
	@Override
	public void run() {
		if (powerupList.size() < Constants.POWERUP_COUNT)
    	{
    		int size = 1;
    		int x = rand.nextInt(Constants.ROWS_VALUE);
    		int y = rand.nextInt(Constants.ROWS_VALUE);
    		int random = rand.nextInt(2);
    		BoardObject newPowerUp;
    		if (random == 0) newPowerUp = factory.getNegativePowerUp(x, y, x, y);
    		else newPowerUp = factory.getPositivePowerUp(x, y, x, y);
    		if (!(Collision.doesCollideWithAny(currentLevel.getWalls(), newPowerUp) 
    				|| Collision.doesCollideWithAny(powerupList.toArray(new BoardObject[0]), newPowerUp)
    				|| Collision.doesCollideWithAny(foodList.toArray(new Food[0]), newPowerUp)))
    		{
    			powerupList.add(newPowerUp);
    		}
    		former.newMessage();
    		for (BoardObject o : powerupList)
        	{
        		former.AddObject(o);
        	}
    		for (Food f : foodList)
        	{
        		former.AddObject(f);
        	}
            former.AddObject(player);
            former.AddObject(player.opponent);
            player.output.println("POS " + former.message);
            player.opponent.output.println("POS " + former.message);
    	}
	}

}

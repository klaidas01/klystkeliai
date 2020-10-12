package timing;

import java.util.List;
import java.util.Random;
import java.util.TimerTask;

import boardObjects.BoardObject;
import boardObjects.Food;
import enums.Constants;
import levels.ILevel;
import server.Collision;
import server.Game.Player;
import server.MessageFormer;

public class SpawnFood extends TimerTask {
	
	private List<Food> foodList;
	private List<BoardObject> powerUpList;
	private Random rand;
	private ILevel currentLevel;
	private Player player;
	private MessageFormer former;
	
	public SpawnFood(List<Food> _food, List<BoardObject> _powerups, Random _rand, ILevel _level, Player _player, MessageFormer _former)
	{
		foodList = _food;
		powerUpList = _powerups;
		rand = _rand;
		currentLevel = _level;
		player = _player;
		former = _former;
	}
	
	@Override
	public void run() {
		if (foodList.size() < Constants.FOOD_COUNT)
    	{
    		int size = rand.nextInt(3);
    		int x = rand.nextInt(Constants.ROWS_VALUE);
    		int y = rand.nextInt(Constants.ROWS_VALUE);
    		Food newFood = new Food(x, y, x + size, y + size, size + 1);
    		if (!(Collision.doesCollideWithAny(currentLevel.getWalls(), newFood)
    				|| Collision.doesCollideWithAny(powerUpList.toArray(new BoardObject[0]), newFood)
    				|| Collision.doesCollideWithAny(foodList.toArray(new Food[0]), newFood)))
    		{
    			foodList.add(newFood);
    		}
    		former.newMessage();
    		for (BoardObject o : powerUpList)
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

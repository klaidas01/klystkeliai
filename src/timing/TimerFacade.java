package timing;

import java.util.List;
import java.util.Random;
import java.util.Timer;

import boardObjects.BoardObject;
import boardObjects.Food;
import boardObjects.PointFactory;
import boardObjects.SpeedFactory;
import composite.BoardObjectComposite;
import enums.Constants;
import levels.ILevel;
import server.MessageFormer;
import server.Game;
import server.Player;

public class TimerFacade {
	
	public Timer timer;
	
	public TimerFacade()
	{
		this.timer = new Timer(true);
	}
	
	public void setupSpawnTimers(Game game)
	{
		this.timer = new Timer(true);
		var foodTask = new SpawnFood(game);
		var speedTask = new SpawnPowerup(game, new SpeedFactory());
		var pointsTask = new SpawnPowerup(game, new PointFactory());
		timer.schedule(foodTask, 0, Constants.FOOD_DELAY);
        timer.schedule(speedTask, 0, Constants.POWERUP_DELAY);
        timer.schedule(pointsTask, 0, Constants.POWERUP_DELAY);
	}
	
	public void CollectedPowerUp(Player player, String powerUpType)
	{
		switch (powerUpType) {
			case "speed":
				timer.schedule(new RevertSpeed(player), Constants.POWERUP_DURATION);
				break;
			case "points":
				timer.schedule(new RevertScoreMultiplier(player), Constants.POWERUP_DURATION);
				break;
		}
	}
}

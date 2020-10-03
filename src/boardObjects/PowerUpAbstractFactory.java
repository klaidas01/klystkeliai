package boardObjects;

public class PowerUpAbstractFactory {
	
	public static PowerUp GetPowerUp(int x1, int y1, int x2, int y2, String family, String effect) throws Exception
	{
		switch(family) {
		case "POINTS":
			return PointFactory.GetPointPowerUp(x1, y1, x2, y2, effect);
		case "SPEED":
			return SpeedFactory.GetSpeedPowerUp(x1, y1, x2, y2, effect);
		default:
			throw new Exception("Unknown power up family");
		}
	}
}

package boardObjects;

public class SpeedFactory {
	public static PowerUp GetSpeedPowerUp(int x1, int y1, int x2, int y2, String type)
	{
		switch(type) {
			case "POSITIVE":
				return new DoubleSpeed(x1, y1, x2, y2);
			case "NEGATIVE":
				return new HalfSpeed(x1, y1, x2, y2);
			default:
				return new DoubleSpeed(x1, y1, x2, y2);
		}
	}
}

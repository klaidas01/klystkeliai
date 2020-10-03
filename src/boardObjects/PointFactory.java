package boardObjects;

public class PointFactory {
	public static PowerUp GetPointPowerUp(int x1, int y1, int x2, int y2, String type)
	{
		switch(type) {
			case "POSITIVE":
				return new DoublePoints(x1, y1, x2, y2);
			case "NEGATIVE":
				return new ZeroPoints(x1, y1, x2, y2);
			default:
				return new DoublePoints(x1, y1, x2, y2);
		}
	}
}

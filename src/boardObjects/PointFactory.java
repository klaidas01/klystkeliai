package boardObjects;

public class PointFactory implements PowerUpAbstractFactory {

	@Override
	public PositivePowerUp getPositivePowerUp(int x1, int y1, int x2, int y2) {
		return new DoublePoints(x1, y1, x2, y2);
	}

	@Override
	public NegativePowerUp getNegativePowerUp(int x1, int y1, int x2, int y2) {
		return new ZeroPoints(x1, y1, x2, y2);
	}
}

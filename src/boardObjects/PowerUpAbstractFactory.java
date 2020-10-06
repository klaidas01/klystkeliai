package boardObjects;

public interface PowerUpAbstractFactory {
	public PositivePowerUp getPositivePowerUp(int x1, int y1, int x2, int y2);
	public NegativePowerUp getNegativePowerUp(int x1, int y1, int x2, int y2);
}

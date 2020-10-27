package boardObjects;

public class NegativeObjectFactory {
	public static NegativeObject GetNegativeObject(int x1, int y1, int x2, int y2, String type)
	{
		switch(type) {
			case "BLACK_WALL":
				return new BlackWall(x1, y1, x2, y2);
			case "WHITE_WALL":
				return new WhiteWall(x1, y1, x2, y2);
			case "FAKE_WALL":
				return new FakeWall(x1, y1, x2, y2);
			default:
				return new BlackWall(x1, y1, x2, y2);
		}
	}
}

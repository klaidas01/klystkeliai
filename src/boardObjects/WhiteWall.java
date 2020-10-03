package boardObjects;

public class WhiteWall extends NegativeObject {
	public WhiteWall(int x1, int y1, int x2, int y2)
	{
		super(x1, y1, x2, y2);
	}
	
	public String getName() {
		return "WHITE_WALL";
	}
}

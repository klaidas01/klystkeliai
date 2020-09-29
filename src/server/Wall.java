package server;

public class Wall extends BoardObject {
	public Wall(int x1, int y1, int x2, int y2)
	{
		super(x1, y1, x2, y2);
	}
	
	public String getName() {
		return "WALL";
	}
}

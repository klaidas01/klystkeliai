package server;

public abstract class BoardObject {
	Coordinates northWestCoord;
	Coordinates southEastCoord;
	
	public BoardObject(int x1, int y1, int x2, int y2)
	{
		northWestCoord = new Coordinates(x1, y1);
		southEastCoord = new Coordinates(x2, y2);
	}
	
	public abstract String getName();
}

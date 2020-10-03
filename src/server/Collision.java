package server;

import boardObjects.BoardObject;

public class Collision {
	public static boolean doesCollideWithAny(BoardObject[] objects, BoardObject object)
	{
		for (BoardObject o : objects)
		{
			if (doesCollide(o, object)) return true;
		}
		return false;
	}
	
	public static boolean doesCollide(BoardObject object, BoardObject object2)
	{
		return !(object2.northWestCoord.X > object.southEastCoord.X 
				|| object2.southEastCoord.X < object.northWestCoord.X
				|| object2.northWestCoord.Y > object.southEastCoord.Y
				|| object2.southEastCoord.Y < object.northWestCoord.Y);
	}
}

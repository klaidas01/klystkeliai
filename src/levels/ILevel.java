package levels;

import boardObjects.BoardObject;

public interface ILevel {
	public StringBuilder levelString();
	
	public BoardObject[] getWalls();
}

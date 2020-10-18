package levels;

import boardObjects.BoardObject;

public interface ILevel extends Cloneable {
	public StringBuilder levelString();
	
	public BoardObject[] getWalls();
}

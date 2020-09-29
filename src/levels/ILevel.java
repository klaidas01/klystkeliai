package levels;

import server.BoardObject;

public interface ILevel {
	public StringBuilder levelString();
	
	public BoardObject[] getWalls();
}

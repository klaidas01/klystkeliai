package levels;

import enums.Constants;
import server.BoardObject;
import server.MessageFormer;
import server.Wall;

public class Box implements ILevel {
	private StringBuilder levelString;
	BoardObject[] walls;
	
	public Box()
	{
		walls = new BoardObject[] {
				new Wall(0, 0, 49, 0),
				new Wall(0, 0, 0, 49),
				new Wall(49, 0, 49, 49),
				new Wall(0, 49, 49, 49)
				};
		MessageFormer former = new MessageFormer(Constants.ROWS_VALUE);
		for (BoardObject wall : walls)
		{
			former.AddObject(wall);
		}
		levelString = former.message;
	}

	@Override
	public StringBuilder levelString() {
		return levelString;
	}

	@Override
	public BoardObject[] getWalls() {
		return walls;
	}
	
	
}

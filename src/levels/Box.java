package levels;

import boardObjects.BoardObject;
import boardObjects.NegativeObjectFactory;
import enums.Constants;
import server.MessageFormer;

public class Box implements ILevel {
	private StringBuilder levelString;
	BoardObject[] walls;
	
	public Box()
	{
		walls = new BoardObject[] {
				NegativeObjectFactory.GetNegativeObject(0, 0, 99, 0, "BLACK_WALL"),
				NegativeObjectFactory.GetNegativeObject(0, 0, 0, 99, "BLACK_WALL"),
				NegativeObjectFactory.GetNegativeObject(99, 0, 99, 99, "BLACK_WALL"),
				NegativeObjectFactory.GetNegativeObject(0, 99, 99, 99, "BLACK_WALL")
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

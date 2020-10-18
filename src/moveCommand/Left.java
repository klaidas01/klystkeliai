package moveCommand;

import boardObjects.BoardObject;

public class Left implements ICommand {
	
	private BoardObject objectToMove;
	private int distance;
	
	public Left(BoardObject object, int speed)
	{
		objectToMove = object;
		distance = speed;
	}
	
	@Override
	public void move() {
		objectToMove.northWestCoord.X -= distance;
		objectToMove.southEastCoord.X -= distance;		
	}

	@Override
	public void undo() {
		objectToMove.northWestCoord.X += distance;
		objectToMove.southEastCoord.X += distance;	
	}

}

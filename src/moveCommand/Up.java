package moveCommand;

import boardObjects.BoardObject;

public class Up implements ICommand {
	
	private BoardObject objectToMove;
	private int distance;
	
	public Up(BoardObject object, int speed)
	{
		objectToMove = object;
		distance = speed;
	}
	
	@Override
	public void move() {
		objectToMove.northWestCoord.Y -= distance;
		objectToMove.southEastCoord.Y -= distance;		
	}

	@Override
	public void undo() {
		objectToMove.northWestCoord.Y += distance;
		objectToMove.southEastCoord.Y += distance;	
	}

}

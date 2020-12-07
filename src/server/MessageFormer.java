package server;

import boardObjects.BoardObject;
import visitor.MessageBuilderVisitor;
import visitor.Visitor;

public class MessageFormer {
	public StringBuilder message;
	int size;
	StringBuilder level;
	Visitor<StringBuilder> visitor;
	
	public MessageFormer(int boardSize) {
		level = new StringBuilder("o".repeat(boardSize * boardSize));
		message = new StringBuilder(level);
		size = boardSize;
		visitor = new MessageBuilderVisitor(message, size);
	}
	
	public MessageFormer(int boardSize, StringBuilder loadedLevel) {
		level = new StringBuilder(loadedLevel);
		message = new StringBuilder(loadedLevel);
		size = boardSize;
		visitor = new MessageBuilderVisitor(message, size);
	}
	
	public void newMessage() {
		message = new StringBuilder(level);
		visitor = new MessageBuilderVisitor(message, size);
	}
	
	public void AddObject(BoardObject obj) {
		obj.accept(visitor);
		message = visitor.returnResult();
	}
	
	public void AddColor(Coordinates northWestCoord, Coordinates southEastCoord, char color)
	{
		for (int i = northWestCoord.Y; i <= southEastCoord.Y; i++)
	        for (int j = northWestCoord.X; j <= southEastCoord.X; j++)
	            {
	        		if (message.charAt(j + i * size) != 'w')
	        		message.setCharAt(j + i * size, color);
	            }
	}
	
	public void AddPixel(int x, int y, char color)
	{
		if (message.charAt(x + y * size) != 'w')
		message.setCharAt(x + y * size, color);
	}

}

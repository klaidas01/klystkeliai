package server;

import boardObjects.BoardObject;

public class MessageFormer {
	public StringBuilder message;
	int size;
	StringBuilder level;
	
	public MessageFormer(int boardSize) {
		level = new StringBuilder("o".repeat(boardSize * boardSize));
		message = new StringBuilder(level);
		size = boardSize;
	}
	
	public MessageFormer(int boardSize, StringBuilder loadedLevel) {
		level = new StringBuilder(loadedLevel);
		message = new StringBuilder(loadedLevel);
		size = boardSize;
	}
	
	public void newMessage() {
		message = new StringBuilder(level);
	}
	
	public void AddObject(BoardObject obj) {
		for (int i = obj.northWestCoord.Y; i <= obj.southEastCoord.Y; i++)
	        for (int j = obj.northWestCoord.X; j <= obj.southEastCoord.X; j++)
	            {
	        		switch(obj.getName()) {
	        			case "P1":
	        				message.setCharAt(j + i * size, '1');
	        				break;
	        			case "P2":
	        				message.setCharAt(j + i * size, '2');
	        				break;
	        			case "BLACK_WALL":
	        				message.setCharAt(j + i * size, 'w');
	        				break;
	        			case "FOOD":
	        				message.setCharAt(j + i * size, 'f');
	        				break;
	        			case "DOUBLE_SPEED":
	        				message.setCharAt(j + i * size, 's');
	        				break;
	        			case "HALF_SPEED":
	        				message.setCharAt(j + i * size, 's');
	        				break;
	        			case "DOUBLE_POINTS":
	        				message.setCharAt(j + i * size, 'p');
	        				break;
	        			case "ZERO_POINTS":
	        				message.setCharAt(j + i * size, 'p');
	        				break;
	        			default:
	        				message.setCharAt(j + i * size, 'o');
	        				break;
	        		}
	            }
	}

}

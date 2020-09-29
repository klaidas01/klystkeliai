package server;

public class MessageFormer {
	StringBuilder message;
	int size;
	
	public MessageFormer(int boardSize) {
		message = new StringBuilder("o".repeat(boardSize * boardSize));
		size = boardSize;
	}
	
	public void newMessage() {
		message = new StringBuilder("o".repeat(size * size));
	}
	
	public void AddObject(BoardObject obj, String objType) {
		System.out.println("UP_LEFT: " + obj.northWestCoord.X + ";" + obj.northWestCoord.Y);
		System.out.println("DOWN_RIGHT: " + obj.southEastCoord.X + ";" + obj.southEastCoord.Y);
		for (int i = obj.northWestCoord.Y; i <= obj.southEastCoord.Y; i++)
	        for (int j = obj.northWestCoord.X; j <= obj.southEastCoord.X; j++)
	            {
	        		switch(objType) {
	        			case "P1":
	        				message.setCharAt(j + i * size, '1');
	        				break;
	        			case "P2":
	        				message.setCharAt(j + i * size, '2');
	        				break;
	        			case "WALL":
	        				message.setCharAt(j + i * size, 'w');
	        				break;
	        			default:
	        				message.setCharAt(j + i * size, 'o');
	        				break;
	        		}
	            }
	}

}

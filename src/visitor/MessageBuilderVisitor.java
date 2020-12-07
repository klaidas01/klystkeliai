package visitor;

import boardObjects.DoublePoints;
import boardObjects.DoubleSpeed;
import boardObjects.Food;
import boardObjects.HalfSpeed;
import boardObjects.NegativeBoardObject;
import boardObjects.ZeroPoints;
import template.Player1;
import template.Player2;

public class MessageBuilderVisitor implements Visitor<StringBuilder> {
	
	public StringBuilder message;
	public int size;
	
	public MessageBuilderVisitor(StringBuilder msg, int boardSize)
	{
		message = msg;
		size = boardSize;
	}

	@Override
	public StringBuilder returnResult() {
		return message;
	}

	@Override
	public void visit(Player1 p) {
		for (int i = p.northWestCoord.Y; i <= p.southEastCoord.Y; i++)
	        for (int j = p.northWestCoord.X; j <= p.southEastCoord.X; j++)
	            {
	        		if (message.charAt(j + i * size) != 'w')
	        			message.setCharAt(j + i * size, '1');
	            }
	}

	@Override
	public void visit(Player2 p) {
		for (int i = p.northWestCoord.Y; i <= p.southEastCoord.Y; i++)
	        for (int j = p.northWestCoord.X; j <= p.southEastCoord.X; j++)
	            {
	        		if (message.charAt(j + i * size) != 'w')
	        			message.setCharAt(j + i * size, '2');
	            }
	}

	@Override
	public void visit(Food f) {
		for (int i = f.northWestCoord.Y; i <= f.southEastCoord.Y; i++)
	        for (int j = f.northWestCoord.X; j <= f.southEastCoord.X; j++)
	            {
	        		if (message.charAt(j + i * size) != 'w')
	        			message.setCharAt(j + i * size, 'f');
	            }
	}

	@Override
	public void visit(DoubleSpeed ds) {
		for (int i = ds.northWestCoord.Y; i <= ds.southEastCoord.Y; i++)
	        for (int j = ds.northWestCoord.X; j <= ds.southEastCoord.X; j++)
	            {
	        		if (message.charAt(j + i * size) != 'w')
	        			message.setCharAt(j + i * size, 's');
	            }
		
	}

	@Override
	public void visit(HalfSpeed hs) {
		for (int i = hs.northWestCoord.Y; i <= hs.southEastCoord.Y; i++)
	        for (int j = hs.northWestCoord.X; j <= hs.southEastCoord.X; j++)
	            {
	        		if (message.charAt(j + i * size) != 'w')
	        			message.setCharAt(j + i * size, 's');
	            }
	}

	@Override
	public void visit(DoublePoints dp) {
		for (int i = dp.northWestCoord.Y; i <= dp.southEastCoord.Y; i++)
	        for (int j = dp.northWestCoord.X; j <= dp.southEastCoord.X; j++)
	            {
	        		if (message.charAt(j + i * size) != 'w')
	        			message.setCharAt(j + i * size, 'p');
	            }
	}

	@Override
	public void visit(ZeroPoints zp) {
		for (int i = zp.northWestCoord.Y; i <= zp.southEastCoord.Y; i++)
	        for (int j = zp.northWestCoord.X; j <= zp.southEastCoord.X; j++)
	            {
	        		if (message.charAt(j + i * size) != 'w')
	        			message.setCharAt(j + i * size, 'p');
	            }
	}

	@Override
	public void visit(NegativeBoardObject w) {
		if (w.getName() != "WHITE_WALL") {
			for (int i = w.northWestCoord.Y; i <= w.southEastCoord.Y; i++)
		        for (int j = w.northWestCoord.X; j <= w.southEastCoord.X; j++)
		            {
		        			message.setCharAt(j + i * size, 'w');
		            }
		}
	}

}

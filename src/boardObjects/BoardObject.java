package boardObjects;
import server.Collision;
import server.Coordinates;
import server.MessageFormer;
import visitor.Visitor;

public abstract class BoardObject {
	public Coordinates northWestCoord;
	public Coordinates southEastCoord;
	
	public BoardObject(int x1, int y1, int x2, int y2)
	{
		northWestCoord = new Coordinates(x1, y1);
		southEastCoord = new Coordinates(x2, y2);
	}
	
	public BoardObject() {}
	
	public abstract String getName();
	
	public abstract void accept(Visitor<StringBuilder> v);
	
	public void draw(MessageFormer former) {
		former.AddObject(this);
		
	}

	public BoardObject doesCollide(BoardObject obj) {
		return Collision.doesCollide(obj, this) ? obj : null;
	}
}

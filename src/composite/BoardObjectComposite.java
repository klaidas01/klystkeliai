package composite;

import java.util.ArrayList;
import java.util.List;

import boardObjects.BoardObject;
import server.Collision;
import server.MessageFormer;
import visitor.Visitor;

public class BoardObjectComposite extends BoardObject {
	private List<BoardObject> children = new ArrayList<BoardObject>(); 
        
    public void add(BoardObject obj) 
    { 
    	children.add(obj); 
    } 
        
    public void remove(BoardObject obj) 
    { 
    	children.remove(obj); 
    }

	@Override
	public void draw(MessageFormer former) {
		for (BoardObject obj : children)
		{
			obj.draw(former);
		}
		
	}

	@Override
	public BoardObject doesCollide(BoardObject obj) {
		for (BoardObject o : children)
		{
			if (Collision.doesCollide(obj, o)) return o;
		}
		return null;
	}

	@Override
	public String getName() {
		return "COMPOSITE";
	}

	@Override
	public void accept(Visitor<StringBuilder> v) {
		for (BoardObject obj : children)
		{
			obj.accept(v);
		}
	} 
}

package boardObjects;

public abstract class NegativeObject extends BoardObject {
	public NegativeObject(int x1, int y1, int x2, int y2)
	{
		super(x1, y1, x2, y2);
	}
	
	public abstract String getName();
}

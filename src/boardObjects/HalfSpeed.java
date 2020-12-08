package boardObjects;

import visitor.Visitor;

public class HalfSpeed extends NegativePowerUp {
	public HalfSpeed(int x1, int y1, int x2, int y2)
	{
		super(x1, y1, x2, y2);
	}
	
	public String getName() {
		return "HALF_SPEED";
	}

	@Override
	public void accept(Visitor<StringBuilder> v) {
		v.visit(this);
	}
}

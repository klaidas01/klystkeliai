package boardObjects;

import visitor.Visitor;

public class ZeroPoints extends NegativePowerUp {
	public ZeroPoints(int x1, int y1, int x2, int y2)
	{
		super(x1, y1, x2, y2);
	}
	
	public String getName() {
		return "ZERO_POINTS";
	}

	@Override
	public void accept(Visitor<StringBuilder> v) {
		v.visit(this);
	}
}

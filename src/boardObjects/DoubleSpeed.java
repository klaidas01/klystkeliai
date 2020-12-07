package boardObjects;

import visitor.Visitor;

public class DoubleSpeed extends PositivePowerUp {
	public DoubleSpeed(int x1, int y1, int x2, int y2)
	{
		super(x1, y1, x2, y2);
	}
	
	public String getName() {
		return "DOUBLE_SPEED";
	}

	@Override
	public void accept(Visitor<StringBuilder> v) {
		v.visit(this);
	}
}

package boardObjects;

import visitor.Visitor;

public class Food extends BoardObject {
	
	public int Value;

	public Food(int x1, int y1, int x2, int y2, int value) {
		super(x1, y1, x2, y2);
		this.Value = value;
	}

	@Override
	public String getName() {
		return "FOOD";
	}

	@Override
	public void accept(Visitor<StringBuilder> v) {
		v.visit(this);
	}

}

package boardObjects;

import visitor.Visitor;

public class MementoMoriPoison extends BoardObject {
	
	public MementoMoriPoison(int x1, int y1, int x2, int y2) {
		super(x1, y1, x2, y2);
	}
	
	@Override
	public String getName() {
		return "POISON";
	}

	@Override
	public void accept(Visitor<StringBuilder> v) {
		v.visit(this);
	}
	
}
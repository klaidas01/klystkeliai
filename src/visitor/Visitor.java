package visitor;

import boardObjects.BoardObject;
import boardObjects.DoublePoints;
import boardObjects.DoubleSpeed;
import boardObjects.Food;
import boardObjects.HalfSpeed;
import boardObjects.NegativeBoardObject;
import boardObjects.ZeroPoints;
import template.Player1;
import template.Player2;

public interface Visitor<T> {
	void visit(Player1 p);
	void visit(Player2 p);
	void visit(Food f);
	void visit(DoubleSpeed ds);
	void visit(HalfSpeed hs);
	void visit(DoublePoints dp);
	void visit(ZeroPoints zp);
	void visit(NegativeBoardObject w);
	
	T returnResult();
}

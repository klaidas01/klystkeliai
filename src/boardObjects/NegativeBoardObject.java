package boardObjects;

public class NegativeBoardObject extends BoardObject {

	private NegativeObject negativeObj;
	
	public NegativeBoardObject(int x1, int y1, int x2, int y2, NegativeObject negativeObj) {
		super(x1, y1, x2, y2);
		this.negativeObj = negativeObj;
	}

	@Override
	public String getName() {
		return negativeObj.getName();
	}
	
}
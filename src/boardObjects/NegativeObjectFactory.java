package boardObjects;

import java.util.HashMap;

public class NegativeObjectFactory {
	
	private static HashMap <String, NegativeObject> hm = new HashMap<String, NegativeObject>();
	
	public static NegativeObject GetNegativeObject(String type)
	{
		NegativeObject b = null;
		
		if (hm.containsKey(type)) {
			b = hm.get(type);
		}
		else {
			switch(type) {
			case "BLACK_WALL":
				b = new BlackWall();
				break;
			case "WHITE_WALL":
				b = new WhiteWall();
				break;
			case "FAKE_WALL":
				b = new FakeWall();
				break;
			default:
				b = new BlackWall();
				break;
			}
			hm.put(type, b);
		}
		return b;
	}
}

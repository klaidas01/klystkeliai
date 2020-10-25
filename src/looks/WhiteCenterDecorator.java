package looks;

import server.Coordinates;

public class WhiteCenterDecorator extends PlayerDecorator {
	
	public WhiteCenterDecorator(ILooks toBeDecorated) {
		super(toBeDecorated);
	}
	
	@Override
    public void draw() {
        super.draw();
        drawRedCenter();
    }

    private void drawRedCenter() {
    	toBeDecorated.getFormer().AddColor(new Coordinates(toBeDecorated.getPlayer().northWestCoord.X + 1, toBeDecorated.getPlayer().northWestCoord.Y + 1),
        		new Coordinates(toBeDecorated.getPlayer().southEastCoord.X - 1, toBeDecorated.getPlayer().southEastCoord.Y - 1),
        		'o');
    }
}

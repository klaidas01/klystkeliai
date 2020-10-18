package looks;

import server.Coordinates;

public class BlueCornersDecorator extends PlayerDecorator {
	
	public BlueCornersDecorator(ILooks toBeDecorated) {
		super(toBeDecorated);
	}
	
	@Override
    public void draw() {
        super.draw();
        drawRedCenter();
    }

    private void drawRedCenter() {
    	toBeDecorated.getFormer().AddPixel(toBeDecorated.getPlayer().northWestCoord.X, toBeDecorated.getPlayer().northWestCoord.Y, 'B');
    	toBeDecorated.getFormer().AddPixel(toBeDecorated.getPlayer().northWestCoord.X, toBeDecorated.getPlayer().southEastCoord.Y, 'B');
    	toBeDecorated.getFormer().AddPixel(toBeDecorated.getPlayer().southEastCoord.X, toBeDecorated.getPlayer().southEastCoord.Y, 'B');
    	toBeDecorated.getFormer().AddPixel(toBeDecorated.getPlayer().southEastCoord.X, toBeDecorated.getPlayer().northWestCoord.Y, 'B');
    }
}

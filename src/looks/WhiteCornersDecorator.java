package looks;

import server.Coordinates;

public class WhiteCornersDecorator extends PlayerDecorator {
	
	public WhiteCornersDecorator(ILooks toBeDecorated) {
		super(toBeDecorated);
	}
	
	@Override
    public void draw() {
        super.draw();
        drawRedCenter();
    }

    private void drawRedCenter() {
    	toBeDecorated.getFormer().AddPixel(toBeDecorated.getPlayer().northWestCoord.X, toBeDecorated.getPlayer().northWestCoord.Y, 'o');
    	toBeDecorated.getFormer().AddPixel(toBeDecorated.getPlayer().northWestCoord.X, toBeDecorated.getPlayer().southEastCoord.Y, 'o');
    	toBeDecorated.getFormer().AddPixel(toBeDecorated.getPlayer().southEastCoord.X, toBeDecorated.getPlayer().southEastCoord.Y, 'o');
    	toBeDecorated.getFormer().AddPixel(toBeDecorated.getPlayer().southEastCoord.X, toBeDecorated.getPlayer().northWestCoord.Y, 'o');
    }
}

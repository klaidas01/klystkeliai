package looks;

import server.Coordinates;

public class RedCornersDecorator extends PlayerDecorator {
	
	public RedCornersDecorator(ILooks toBeDecorated) {
		super(toBeDecorated);
	}
	
	@Override
    public void draw() {
        super.draw();
        drawRedCenter();
    }

    private void drawRedCenter() {
    	toBeDecorated.getFormer().AddPixel(toBeDecorated.getPlayer().northWestCoord.X, toBeDecorated.getPlayer().northWestCoord.Y, 'R');
    	toBeDecorated.getFormer().AddPixel(toBeDecorated.getPlayer().northWestCoord.X, toBeDecorated.getPlayer().southEastCoord.Y, 'R');
    	toBeDecorated.getFormer().AddPixel(toBeDecorated.getPlayer().southEastCoord.X, toBeDecorated.getPlayer().southEastCoord.Y, 'R');
    	toBeDecorated.getFormer().AddPixel(toBeDecorated.getPlayer().southEastCoord.X, toBeDecorated.getPlayer().northWestCoord.Y, 'R');
    }
}

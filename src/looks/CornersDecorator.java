package looks;

public class CornersDecorator extends PlayerShapeDecorator {

    public CornersDecorator(ILooks toBeDecorated, Color color) {
        super(toBeDecorated, color);
    }
    @Override
    public void draw() {
        super.draw();
        drawCorner();
    }

    private void drawCorner() {
        toBeDecorated.getFormer().AddPixel(toBeDecorated.getPlayer().northWestCoord.X, toBeDecorated.getPlayer().northWestCoord.Y, colorUse.getColor());
        toBeDecorated.getFormer().AddPixel(toBeDecorated.getPlayer().northWestCoord.X, toBeDecorated.getPlayer().southEastCoord.Y, colorUse.getColor());
        toBeDecorated.getFormer().AddPixel(toBeDecorated.getPlayer().southEastCoord.X, toBeDecorated.getPlayer().southEastCoord.Y, colorUse.getColor());
        toBeDecorated.getFormer().AddPixel(toBeDecorated.getPlayer().southEastCoord.X, toBeDecorated.getPlayer().northWestCoord.Y, colorUse.getColor());
    }
}

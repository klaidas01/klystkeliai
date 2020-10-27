package looks;

import server.Coordinates;

public class CenterDecorator extends PlayerShapeDecorator{

    public CenterDecorator(ILooks toBeDecorated, Color color) {
        super(toBeDecorated,color);
    }

    @Override
    public void draw() {
        super.draw();
        drawCenter();
    }

    private void drawCenter() {
        toBeDecorated.getFormer().AddColor(new Coordinates(toBeDecorated.getPlayer().northWestCoord.X + 1, toBeDecorated.getPlayer().northWestCoord.Y + 1),
                new Coordinates(toBeDecorated.getPlayer().southEastCoord.X - 1, toBeDecorated.getPlayer().southEastCoord.Y - 1),
                colorUse.getColor());
    }
}

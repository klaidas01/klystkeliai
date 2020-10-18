package levels;

import boardObjects.BoardObject;
import boardObjects.NegativeObjectFactory;
import enums.Constants;
import server.MessageFormer;

public class Level implements ILevel {
    private StringBuilder levelString;
    BoardObject[] walls;
    MessageFormer former = new MessageFormer(Constants.ROWS_VALUE);

    public Level()
    {
        walls = new BoardObject[] {};

        for (BoardObject wall : walls)
        {
            former.AddObject(wall);
        }
        levelString = former.message;
    }

    public void addObj(BoardObject obj) {
        former.AddObject(obj);
        BoardObject[] newWalls = new BoardObject[walls.length + 1];
        for (int i = 0; i < walls.length; i++) {
            newWalls[i] = walls[i];
        }
        newWalls[walls.length] = obj;
        walls = newWalls;
        levelString = former.message;
    }

    @Override
    public StringBuilder levelString() {
        return levelString;
    }

    @Override
    public BoardObject[] getWalls() {
        return walls;
    }

}

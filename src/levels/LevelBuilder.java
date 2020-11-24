package levels;

import boardObjects.NegativeBoardObject;
import boardObjects.NegativeObjectFactory;

public class LevelBuilder {
    private static LevelConfigurator configurator = new LevelConfigurator();

    public static Level createBoxLevel() {
        return configurator.reset()
                .addWall(new NegativeBoardObject(0, 0, 99, 0, NegativeObjectFactory.GetNegativeObject("BLACK_WALL")))
                .addWall(new NegativeBoardObject(0, 0, 0, 99, NegativeObjectFactory.GetNegativeObject("BLACK_WALL")))
                .addWall(new NegativeBoardObject(99, 0, 99, 99, NegativeObjectFactory.GetNegativeObject("BLACK_WALL")))
                .addWall(new NegativeBoardObject(0, 99, 99, 99, NegativeObjectFactory.GetNegativeObject("BLACK_WALL")))
                .build();
    }

    public static Level createInvisibleBoxLevel() {
        return configurator.reset()
                .addWall(new NegativeBoardObject(0, 0, 99, 0, NegativeObjectFactory.GetNegativeObject("WHITE_WALL")))
                .addWall(new NegativeBoardObject(0, 0, 0, 99, NegativeObjectFactory.GetNegativeObject("WHITE_WALL")))
                .addWall(new NegativeBoardObject(99, 0, 99, 99, NegativeObjectFactory.GetNegativeObject("WHITE_WALL")))
                .addWall(new NegativeBoardObject(0, 99, 99, 99, NegativeObjectFactory.GetNegativeObject("WHITE_WALL")))
                .build();
    }

    public static Level createBibleLevel() {
        return configurator.reset().init(createBoxLevel())
                .addWall(new NegativeBoardObject(45, 49, 55, 51, NegativeObjectFactory.GetNegativeObject("BLACK_WALL")))
                .addWall(new NegativeBoardObject(49, 44, 51, 60, NegativeObjectFactory.GetNegativeObject("FAKE_WALL")))
                .build();
    }

}

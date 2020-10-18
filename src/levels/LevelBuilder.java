package levels;

import boardObjects.NegativeObjectFactory;

public class LevelBuilder {
    private static LevelConfigurator configurator = new LevelConfigurator();

    public static Level createBoxLevel() {
        return configurator.reset()
                .addWall(NegativeObjectFactory.GetNegativeObject(0, 0, 99, 0, "BLACK_WALL"))
                .addWall(NegativeObjectFactory.GetNegativeObject(0, 0, 0, 99, "BLACK_WALL"))
                .addWall(NegativeObjectFactory.GetNegativeObject(99, 0, 99, 99, "BLACK_WALL"))
                .addWall(NegativeObjectFactory.GetNegativeObject(0, 99, 99, 99, "BLACK_WALL"))
                .build();
    }

    public static Level createInvisibleBoxLevel() {
        return configurator.reset()
                .addWall(NegativeObjectFactory.GetNegativeObject(0, 0, 99, 0, "WHITE_WALL"))
                .addWall(NegativeObjectFactory.GetNegativeObject(0, 0, 0, 99, "WHITE_WALL"))
                .addWall(NegativeObjectFactory.GetNegativeObject(99, 0, 99, 99, "WHITE_WALL"))
                .addWall(NegativeObjectFactory.GetNegativeObject(0, 99, 99, 99, "WHITE_WALL"))
                .build();
    }

    public static Level createBibleLevel() {
        return configurator.reset().init(createBoxLevel())
                .addWall(NegativeObjectFactory.GetNegativeObject(45, 49, 55, 51, "BLACK_WALL"))
                .addWall(NegativeObjectFactory.GetNegativeObject(49, 44, 51, 60, "BLACK_WALL"))
                .build();
    }

}

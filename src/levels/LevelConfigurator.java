package levels;

import boardObjects.BoardObject;

public class LevelConfigurator {
    Level level = new Level();

    public LevelConfigurator init(Level initLevel) {
        level = initLevel.clone();
        return this;
    }

    public LevelConfigurator reset() {
        level = new Level();
        return this;
    }

    public Level build() {
        return level;
    }

    public LevelConfigurator addWall(BoardObject obj) {
        level.addObj(obj);
        return this;
    }
}

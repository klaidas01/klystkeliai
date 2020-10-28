package levels;

import boardObjects.BoardObject;

public class LevelConfigurator {
    Level level = new Level();

    public LevelConfigurator init(Level initLevel) {
        level = initLevel.clone();
        System.out.println("Old hash");
        System.out.println(initLevel.hashCode());
        System.out.println("New hash");
        System.out.println(level.hashCode());
//        System.out.println(initLevel.former.hashCode());
//        System.out.println(level.former.hashCode());
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

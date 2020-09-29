package levels;

public class LevelFactory {
	public static ILevel GetLevel(String levelName)
	{
		switch(levelName) {
			case "Box":
				return new Box();
			default:
				return new Box();
		}
	}
}

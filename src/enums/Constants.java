package enums;

public enum Constants {
	DELAY(Constants.MOVE_DELAY),
	ROWS(Constants.ROWS_VALUE),
	FOOD(Constants.FOOD_COUNT),
	FOOD2(Constants.FOOD_DELAY),
	POWERUP(Constants.POWERUP_COUNT),
	POWERUP2(Constants.POWERUP_DELAY),
	BOARD(Constants.BOARD_SIZE);
	
	public static final int MOVE_DELAY = 100;	//delay between move commands in ms
	
	public static final int ROWS_VALUE = 100;
	
	public static final int FOOD_COUNT = 10;	//max number of food objects to be spawned
	public static final int FOOD_DELAY = 5000;	//delay in ms between food object spawns
	
	public static final int POWERUP_COUNT = 5;	//max number of powerup objects to be spawned
	public static final int POWERUP_DELAY = 5000;	//delay in ms between powerup object spawns
	
	public static final int BOARD_SIZE = 800;  //board size in pixels
	
	Constants(int i) {}
}
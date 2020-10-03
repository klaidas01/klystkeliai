package enums;

public enum Constants {
	DELAY(Constants.MOVE_DELAY),
	ROWS(Constants.ROWS_VALUE);
	
	public static final int MOVE_DELAY = 50; //delay between move commands in ms
	public static final int ROWS_VALUE = 50;
	
	Constants(int i) {}
}
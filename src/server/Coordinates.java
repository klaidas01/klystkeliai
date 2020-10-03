package server;

public class Coordinates {
	public int X;
	public int Y;
	
	public Coordinates(int x, int y) {
		this.X = x;
		this.Y = y;
	}
	
	public void Up() {
		this.Y = this.Y - 1;
	}
	public void Down() {
		this.Y = this.Y + 1;
	}
	public void Left() {
		this.X = this.X - 1;
	}
	public void Right() {
		this.X = this.X + 1;
	}
}
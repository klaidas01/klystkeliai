package server;

public class GameTimer extends Thread implements IObserver {

	Game game;
	
    private int time = 30;
    private boolean setting = false;
	
	public GameTimer(Game game) {
		this.game = game;
	}

    public void run() {
        while(!game.shutdown) {
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
            if(!game.shutdown) {
	            game.player1.output.println("TIME " + time);
	            game.player1.opponent.output.println("TIME " + time);
	            
	            this.time -= 1;
	            
	            if(time <= 0) {
	            	game.notifyObs("GAME OVER");
	            }
            }
        }
    }
    
    public synchronized int getTime() {
        while(setting){
            try {
                wait();
            } catch (InterruptedException e) {  }
        }

        return time;
    }

	@Override
	public void update(String msg) {
		this.time = 100;
		//this.setTime(300);
	}
}
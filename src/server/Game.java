package server;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.*;

import boardObjects.BoardObject;
import boardObjects.Food;
import boardObjects.PointFactory;
import boardObjects.SpeedFactory;
import enums.Constants;
import levels.ILevel;
import logging.ConsoleLogAdapter;
import logging.FileLogAdapter;
import looks.ILooks;
import looks.BaseLooks;
import levels.LevelBuilder;
import movementStrategy.IMovementStrategy;
import movementStrategy.MovementDoubleSpeed;
import movementStrategy.MovementHalfSpeed;
import movementStrategy.MovementNormalSpeed;
import timing.RevertScoreMultiplier;
import timing.RevertSpeed;
import timing.SpawnFood;
import timing.SpawnPowerup;
import timing.TimerFacade;
import server.GameTimer;

import java.io.IOException;

public class Game extends Observable {
    public Player player1;
    public ConsoleLogAdapter consoleLogger = new ConsoleLogAdapter();
    public FileLogAdapter fileLogAdapter = new FileLogAdapter();
    public ILevel currentLevel;
    public Random rand;
    public List<Food> foodList;
    public List<BoardObject> powerUpList;
    MessageFormer former;
    GameTimer gameTimer;
    TimerFacade timer;
    
    public Game () throws FileNotFoundException {
//    	currentLevel = LevelBuilder.createBoxLevel();
//    	currentLevel = LevelBuilder.createInvisibleBoxLevel();
    	currentLevel = LevelBuilder.createBibleLevel();
    	rand = new Random();
    	foodList = new ArrayList<Food>();
    	powerUpList = new ArrayList<BoardObject>();
    	timer = new TimerFacade();
    	former = new MessageFormer(Constants.ROWS_VALUE, currentLevel.levelString());
    	this.obs = new ArrayList<>();
    	checkLogs();
    }
    
    public void startTimer() {
    	this.gameTimer = new GameTimer(this);
    	attach(gameTimer);
    	gameTimer.start();
    }

    private void checkLogs() throws FileNotFoundException {
        for(Iterator<String> i = fileLogAdapter.getMessages(); i.hasNext();)
            System.out.printf("ITERATOR RETURNED: %s%n", i.next());
    }
    
    public class Player extends BoardObject implements Runnable, IObserver {
        char mark;
        public Player opponent;
        Socket socket;
        Scanner input;
        public PrintWriter output;
        public int Score;
        IMovementStrategy movementStrategy;
        public int speedCount;
        public int scoreCount;
        public double scoreMultiplier;
        public ILooks looks;

        public Player(int x1, int y1, int x2, int y2, Socket socket, char mark) {
        	super(x1, y1, x2, y2);
            this.socket = socket;
            this.mark = mark;
            this.Score = 0;
            this.movementStrategy = new MovementNormalSpeed();
            this.speedCount = 0;
            this.scoreCount = 0;
            this.scoreMultiplier = 1;
            this.looks = new BaseLooks(former, this);
        }
        
        public void setLooks(ILooks looks)
        {
        	this.looks = looks;
        }
        
        public void setMovementStrategy(IMovementStrategy strategy)
        {
        	this.movementStrategy = strategy;
        }
        
        @Override
        public void run() {
            try {
                setup();
                processCommands();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (opponent != null && opponent.output != null) {
                    opponent.output.println("OTHER_PLAYER_LEFT");
                }
                try {
                    socket.close();
                } catch (IOException e) {
                }
            }
        }
        
        public String getName() {
    		return this.mark == 'A' ? "P1" : "P2";
    	}

        private void setup() throws IOException {
            input = new Scanner(socket.getInputStream());
            output = new PrintWriter(socket.getOutputStream(), true);
            output.println("WELCOME " + mark);
            consoleLogger.logInfo("WELCOME " + mark);
            fileLogAdapter.logInfo("WELCOME " + mark);
            if (mark == 'A') {
                player1 = this;
                output.println("MESSAGE Waiting for opponent to connect");
            } else {
                opponent = player1;
                opponent.opponent = this;
                this.opponent.looks.draw();
                this.looks.draw();
                output.println("POS " + former.message);
                opponent.output.println("POS " + former.message);
                
                startTimer();
                
                timer.setupSpawnTimers(foodList, powerUpList, rand, currentLevel, this, former);
            }
        }

        private void processCommands() {
            while (input.hasNextLine()) {
                var command = input.nextLine();
                processMoveCommand(command.charAt(0));
            }
        }

        private void processMoveCommand(char direction) {
            try {
            	former.newMessage();
            	movementStrategy.move(currentLevel, this, direction);
            	Food collectedFood = (Food)Collision.findObject(foodList.toArray(new Food[0]), this);
            	BoardObject collectedPowerUp = Collision.findObject(powerUpList.toArray(new BoardObject[0]), this);
            	if (collectedFood != null)
            	{
            		foodList.remove(collectedFood);
            		this.Score += collectedFood.Value * this.scoreMultiplier;
            		
            		if(this.Score >= 100) {
            			notifyObs();
            		}
            		
            		output.println("SCORE " + this.Score + ';' + this.opponent.Score);
            		opponent.output.println("SCORE " + this.opponent.Score + ';' + this.Score);
            	}
            	if (collectedPowerUp != null)
            	{
            		powerUpList.remove(collectedPowerUp);
            		switch (collectedPowerUp.getName()) {
            			case "DOUBLE_SPEED":
            				this.setMovementStrategy(new MovementDoubleSpeed());
            				this.speedCount += 1;
            				timer.CollectedPowerUp(this, "speed");
            				break;
            			case "HALF_SPEED":
            				this.setMovementStrategy(new MovementHalfSpeed());
            				this.speedCount += 1;
            				timer.CollectedPowerUp(this, "speed");
            				break;
            			case "DOUBLE_POINTS":
            				this.scoreMultiplier = 2;
            				this.scoreCount += 1;
            				timer.CollectedPowerUp(this, "points");
            				break;
            			case "ZERO_POINTS":
            				this.scoreMultiplier = 0;
            				this.scoreCount += 1;
            				timer.CollectedPowerUp(this, "points");
            				break;
            			default:
            				break;
            		}
            	}
            	for (Food f : foodList)
            	{
            		former.AddObject(f);
            	}
            	for (BoardObject o : powerUpList)
            	{
            		former.AddObject(o);
            	}
                this.looks.draw();
                this.opponent.looks.draw();
                output.println("POS " + former.message);
                opponent.output.println("POS " + former.message);
            } catch (IllegalStateException e) {
                output.println("MESSAGE " + e.getMessage());
            }
        }

		@Override
		public void update() {
			this.Score = 0;
			this.output.println("SCORE " + this.Score + ';' + this.opponent.Score);
		}
    }
}
	
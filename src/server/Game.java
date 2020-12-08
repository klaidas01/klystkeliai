package server;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.*;

import boardObjects.BoardObject;
import boardObjects.Food;
import composite.BoardObjectComposite;
import enums.Constants;
import levels.ILevel;
import logging.*;
import looks.ILooks;
import looks.BaseLooks;
import levels.LevelBuilder;
import movementStrategy.IMovementStrategy;
import movementStrategy.MovementDoubleSpeed;
import movementStrategy.MovementHalfSpeed;
import movementStrategy.MovementNormalSpeed;
import timing.TimerFacade;

import java.io.IOException;

public class Game extends Observable {
    public Player player1;
    public LogHandler fileLogger = new FileLog();
    public LogHandler logHandler;
    public LogHandler consoleLogger2 = new ConsoleLogger();
    public ConsoleLogAdapter consoleLogger = new ConsoleLogAdapter();
    public FileLogAdapter fileLogAdapter = new FileLogAdapter();
    public BoardObjectComposite objectComposite = new BoardObjectComposite();
    public ILevel currentLevel;
    public Random rand;
    public MessageFormer former;
    GameTimer gameTimer;
    public int powerupCount = 0;
    public int foodCount = 0;
    public TimerFacade timer;
    
    public Game () throws FileNotFoundException {
//    	currentLevel = LevelBuilder.createBoxLevel();
//    	currentLevel = LevelBuilder.createInvisibleBoxLevel();
    	currentLevel = LevelBuilder.createBibleLevel();
    	consoleLogger2.setNextChain(fileLogger);
    	fileLogger.setNextChain(null);
    	logHandler=consoleLogger2;
    	rand = new Random();
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
    
    public abstract class Player extends BoardObject implements Runnable, IObserver {
        public Player opponent;
        protected Socket socket;
        protected Scanner input;
        public PrintWriter output;
        public int Score;
        IMovementStrategy movementStrategy;
        public int speedCount;
        public int scoreCount;
        public double scoreMultiplier;
        public ILooks looks;
        public String name;
        protected Game game;

        public Player(Socket socket, Game game) {
        	super();
            this.socket = socket;
            this.Score = 0;
            this.movementStrategy = new MovementNormalSpeed();
            this.speedCount = 0;
            this.scoreCount = 0;
            this.scoreMultiplier = 1;
            this.looks = new BaseLooks(former, this);
            this.game  = game;
        }
        
        @Override
    	public void draw(MessageFormer former) {
    		this.looks.draw();
    		
    	}
        
        public abstract void setLooks();
        
        public abstract void setName();
        
        public abstract void setPosition();
        
        public abstract void setupGame() throws IOException;
        
        public final void setupPlayerTemplate() throws IOException
        {
        	setLooks();
        	setName();
        	setPosition();
        	setupGame();
        }
        
        public void setMovementStrategy(IMovementStrategy strategy)
        {
        	this.movementStrategy = strategy;
        }
        
        public String getName()
        {
        	return this.name;
        }
        
        @Override
        public void run() {
            try {
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
            	BoardObject collectedObject = objectComposite.doesCollide(this);
            	if (collectedObject != null)
            	{
            		objectComposite.remove(collectedObject);
            		if (collectedObject.getName() == "FOOD")
            		{
            			foodCount--;
            			this.Score += ((Food)collectedObject).Value * this.scoreMultiplier;
            			
            			if(this.Score >= 100) {
                			notifyObs();
                		}
            			
            			output.println("SCORE " + this.Score + ';' + this.opponent.Score);
                		opponent.output.println("SCORE " + this.opponent.Score + ';' + this.Score);
            		}
            		else {
            			powerupCount--;
            			switch (collectedObject.getName()) {
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
            	}
            	objectComposite.draw(former);
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
	
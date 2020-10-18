package server;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;
import java.util.Timer;
import java.util.ArrayList;
import java.util.List;

import boardObjects.BoardObject;
import boardObjects.Food;
import boardObjects.PointFactory;
import boardObjects.SpeedFactory;
import enums.Constants;
import levels.ILevel;
import levels.LevelFactory;
import movementStrategy.IMovementStrategy;
import movementStrategy.MovementDoubleSpeed;
import movementStrategy.MovementHalfSpeed;
import movementStrategy.MovementNormalSpeed;
import timing.RevertScoreMultiplier;
import timing.RevertSpeed;
import timing.SpawnFood;
import timing.SpawnPowerup;

import java.io.IOException;

public class Game {
    Player player1;
    Logger logger = Logger.getInstance();
    public ILevel currentLevel;
    Random rand;
    List<Food> foodList;
    List<BoardObject> powerUpList;
    Timer timer;
    
    public Game (String levelName) {
    	currentLevel = LevelFactory.GetLevel(levelName);
    	rand = new Random();
    	foodList = new ArrayList<Food>();
    	powerUpList = new ArrayList<BoardObject>();
    	timer = new Timer(true);
    }

    public class Player extends BoardObject implements Runnable {
        char mark;
        public Player opponent;
        Socket socket;
        Scanner input;
        public PrintWriter output;
        MessageFormer former;
        public int Score;
        IMovementStrategy movementStrategy;
        public int speedCount;
        public int scoreCount;
        public double scoreMultiplier;

        public Player(int x1, int y1, int x2, int y2, Socket socket, char mark) {
        	super(x1, y1, x2, y2);
            former = new MessageFormer(Constants.ROWS_VALUE, currentLevel.levelString());
            this.socket = socket;
            this.mark = mark;
            this.Score = 0;
            this.movementStrategy = new MovementNormalSpeed();
            this.speedCount = 0;
            this.scoreCount = 0;
            this.scoreMultiplier = 1;
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
            logger.logInfo("WELCOME " + mark);
            if (mark == 'A') {
                player1 = this;
                output.println("MESSAGE Waiting for opponent to connect");
            } else {
                opponent = player1;
                opponent.opponent = this;
                former.newMessage();
                former.AddObject(this.opponent);
                former.AddObject(this);
                output.println("POS " + former.message);
                opponent.output.println("POS " + former.message);
                timer.schedule(new SpawnFood(foodList, powerUpList, rand, currentLevel, this, former), 0, Constants.FOOD_DELAY);
                timer.schedule(new SpawnPowerup(powerUpList, foodList, rand, currentLevel, this, former, new SpeedFactory()), 0, Constants.POWERUP_DELAY);
                timer.schedule(new SpawnPowerup(powerUpList, foodList, rand, currentLevel, this, former, new PointFactory()), 0, Constants.POWERUP_DELAY);
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
            				timer.schedule(new RevertSpeed(this), Constants.POWERUP_DURATION);
            				break;
            			case "HALF_SPEED":
            				this.setMovementStrategy(new MovementHalfSpeed());
            				this.speedCount += 1;
            				timer.schedule(new RevertSpeed(this), Constants.POWERUP_DURATION);
            				break;
            			case "DOUBLE_POINTS":
            				this.scoreMultiplier = 2;
            				this.scoreCount += 1;
            				timer.schedule(new RevertScoreMultiplier(this), Constants.POWERUP_DURATION);
            				break;
            			case "ZERO_POINTS":
            				this.scoreMultiplier = 0;
            				this.scoreCount += 1;
            				timer.schedule(new RevertScoreMultiplier(this), Constants.POWERUP_DURATION);
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
                former.AddObject(player1);
                former.AddObject(player1.opponent);
                output.println("POS " + former.message);
                opponent.output.println("POS " + former.message);
            } catch (IllegalStateException e) {
                output.println("MESSAGE " + e.getMessage());
            }
        }


    }
}
	
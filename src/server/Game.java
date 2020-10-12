package server;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

import boardObjects.BoardObject;
import boardObjects.Food;
import enums.Constants;
import levels.ILevel;
import levels.LevelFactory;
import movementStrategy.MovementDoubleSpeed;
import movementStrategy.MovementHalfSpeed;
import movementStrategy.MovementNormalSpeed;
import movementStrategy.Moving;

import java.io.IOException;

public class Game {
    Player player1;
    Logger logger = Logger.getInstance();
    ILevel currentLevel;
    Moving movement;
    Random rand;
    List<Food> foodList;
    
    public Game (String levelName) {
    	currentLevel = LevelFactory.GetLevel(levelName);
    	rand = new Random();
    	foodList = new ArrayList<Food>();
    }

    public class Player extends BoardObject implements Runnable {
        char mark;
        public Player opponent;
        Socket socket;
        Scanner input;
        PrintWriter output;
        MessageFormer former;
        int Score;

        public Player(int x1, int y1, int x2, int y2, Socket socket, char mark) {
        	super(x1, y1, x2, y2);
            former = new MessageFormer(Constants.ROWS_VALUE, currentLevel.levelString());
            this.socket = socket;
            this.mark = mark;
            this.Score = 0;
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
            	movement = new Moving(new MovementNormalSpeed());
            	//movement = new Moving(new MovementHalfSpeed());
            	//movement = new Moving(new MovementDoubleSpeed());
            	former.newMessage();
            	if (foodList.size() < Constants.FOOD_COUNT && rand.nextInt(10) < 1)
            	{
            		int size = rand.nextInt(3);
            		int x = rand.nextInt(Constants.ROWS_VALUE);
            		int y = rand.nextInt(Constants.ROWS_VALUE);
            		Food newFood = new Food(x, y, x + size, y + size, size + 1);
            		if (!(Collision.doesCollideWithAny(currentLevel.getWalls(), newFood) 
            				|| Collision.doesCollideWithAny(foodList.toArray(new Food[0]), newFood)))
            		{
            			foodList.add(newFood);
            		}
            	}
            	movement.move(currentLevel, this, direction);
            	Food collectedFood = (Food)Collision.findObject(foodList.toArray(new Food[0]), this);
            	if (collectedFood != null)
            	{
            		foodList.remove(collectedFood);
            		this.Score += collectedFood.Value;
            		output.println("SCORE " + this.Score + ';' + this.opponent.Score);
            		opponent.output.println("SCORE " + this.opponent.Score + ';' + this.Score);
            	}
            	for (Food f : foodList)
            	{
            		former.AddObject(f);
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
	
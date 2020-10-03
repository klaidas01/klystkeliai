package server;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import boardObjects.BoardObject;
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
    
    public Game (String levelName) {
    	currentLevel = LevelFactory.GetLevel(levelName);
    }

    public class Player extends BoardObject implements Runnable {
        char mark;
        public Player opponent;
        Socket socket;
        Scanner input;
        PrintWriter output;
        MessageFormer former;

        public Player(int x1, int y1, int x2, int y2, Socket socket, char mark) {
        	super(x1, y1, x2, y2);
            former = new MessageFormer(Constants.ROWS_VALUE, currentLevel.levelString());
            this.socket = socket;
            this.mark = mark;
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
            	movement.move(currentLevel, this, direction);
                former.newMessage();
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
	
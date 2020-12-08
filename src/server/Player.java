package server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import Memento.CareTaker;
import Memento.Memento;
import boardObjects.BoardObject;
import boardObjects.Food;
import looks.ILooks;
import movementStrategy.IMovementStrategy;
import movementStrategy.MovementDoubleSpeed;
import movementStrategy.MovementHalfSpeed;
import movementStrategy.MovementNormalSpeed;

public abstract class Player extends BoardObject implements Runnable, IObserver {
        public Player opponent;
        public Socket socket;
        public Scanner input;
        public PrintWriter output;
        public int Score;
        IMovementStrategy movementStrategy;
        public int speedCount;
        public int scoreCount;
        public double scoreMultiplier;
        public ILooks looks;
        public String name;
        public Game game;
        private double lastCollected = 0;
        private CareTaker careTaker = new CareTaker();
        
        public Player(Socket socket) throws IOException {
        	super();
            this.socket = socket;
            this.Score = 0;
            this.movementStrategy = new MovementNormalSpeed();
            this.speedCount = 0;
            this.scoreCount = 0;
            this.scoreMultiplier = 1;
            input = new Scanner(socket.getInputStream());
            output = new PrintWriter(socket.getOutputStream(), true);
        }
        
        public void setGame(Game game) {
        	this.game = game;
        }
        
        @Override
    	public void draw(MessageFormer former) {
    		this.looks.draw();
    		
    	}
        
        public Memento saveStateToMemento() {
        	return new Memento(lastCollected);
        }
        
        public void getStateFromMemento(Memento memento) {
        	lastCollected = memento.getState();
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
                game.notifyObs(name);
            }
        }

        private void processCommands() {
        	try {
        		while (!game.shutdown && input.hasNextLine()) {
            		var command = input.nextLine();
                    processMoveCommand(command.charAt(0));
            	}
            } catch (Exception e) {}
        }

        private void processMoveCommand(char direction) {
            try {
            	game.former.newMessage();
            	movementStrategy.move(game.currentLevel, this, direction);
            	BoardObject collectedObject = game.objectComposite.doesCollide(this);
            	if (collectedObject != null)
            	{
            		game.objectComposite.remove(collectedObject);
            		if (collectedObject.getName() == "FOOD")
            		{
            			game.foodCount--;
            			this.Score += ((Food)collectedObject).Value * this.scoreMultiplier;
            			
            			this.lastCollected = ((Food)collectedObject).Value * this.scoreMultiplier;
            			
            			careTaker.add(saveStateToMemento());
            			
            			if(this.Score >= 10) {
            				game.notifyObs("GAME OVER");
                		}
            			
            			output.println("SCORE " + this.Score + ';' + this.opponent.Score);
                		opponent.output.println("SCORE " + this.opponent.Score + ';' + this.Score);
            		}
            		else if (collectedObject.getName() == "POISON")
            		{
            			game.poisonCount--;
            			
            			Memento mem = careTaker.get(0);
            			
            			if(mem != null) {
            				this.Score -= mem.getState();
            				if(this.Score < 0) {
            					this.Score = 0;
            				}
            				careTaker.delete(0);
            				
                			output.println("SCORE " + this.Score + ';' + this.opponent.Score);
                    		opponent.output.println("SCORE " + this.opponent.Score + ';' + this.Score);
            			}
            		}
            		else {
            			game.powerupCount--;
            			switch (collectedObject.getName()) {
            			case "DOUBLE_SPEED":
            				this.setMovementStrategy(new MovementDoubleSpeed());
            				this.speedCount += 1;
            				game.timer.CollectedPowerUp(this, "speed");
            				break;
            			case "HALF_SPEED":
            				this.setMovementStrategy(new MovementHalfSpeed());
            				this.speedCount += 1;
            				game.timer.CollectedPowerUp(this, "speed");
            				break;
            			case "DOUBLE_POINTS":
            				this.scoreMultiplier = 2;
            				this.scoreCount += 1;
            				game.timer.CollectedPowerUp(this, "points");
            				break;
            			case "ZERO_POINTS":
            				this.scoreMultiplier = 0;
            				this.scoreCount += 1;
            				game.timer.CollectedPowerUp(this, "points");
            				break;
            			default:
            				break;
            			}
            		}
            	}
            	game.objectComposite.draw(game.former);
                this.looks.draw();
                this.opponent.looks.draw();
                output.println("POS " + game.former.message);
                opponent.output.println("POS " + game.former.message);
            } catch (IllegalStateException e) {
                output.println("MESSAGE " + e.getMessage());
            }
        }

		@Override
		public void update(String msg) {
			this.Score = 0;
		}
    }
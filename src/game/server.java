package game;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.Executors;



public class server {
	public static void main(String[] args) throws Exception {
        try (var listener = new ServerSocket(58901)) {
            System.out.println("Game Server is Running...");
            var pool = Executors.newFixedThreadPool(200);
            while (true) {
                Game game = new Game();
                pool.execute(game.new Player(listener.accept(), 'A'));
                pool.execute(game.new Player(listener.accept(), 'B'));
            }
        }
    }
}

class Game {	
	Player player1;
	
	public void move(char direction, Player player) {
        
        if (player.opponent == null) {
            throw new IllegalStateException("Can't move without an opponent");
        } else if (direction == 'U') {
        	player.position.Up();
        } else if (direction == 'D') {
        	player.position.Down();
        } else if (direction == 'R') {
        	player.position.Right();
        } else if (direction == 'L') {
        	player.position.Left();
        }
    }

	
	class Position {
		int X;
		int Y;
		
		public Position(int x, int y) {
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
	
	class Player implements Runnable {
		char mark;
        Player opponent;
        Socket socket;
        Scanner input;
        PrintWriter output;
        Position position;

        public Player(Socket socket, char mark) {
            this.socket = socket;
            this.mark = mark;
            this.position = new Position(0,0);
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
        
        private void setup() throws IOException {
            input = new Scanner(socket.getInputStream());
            output = new PrintWriter(socket.getOutputStream(), true);
            output.println("WELCOME " + mark);
            
            if (mark == 'A') {
                player1 = this;
                output.println("MESSAGE Waiting for opponent to connect");
            } else {
                opponent = player1;
                opponent.opponent = this;
                output.println("POS " + this.position.X + ';' + this.position.Y + ';' + this.opponent.position.X + ';' + this.opponent.position.Y);
                opponent.output.println("POS " + this.opponent.position.X + ';' + this.opponent.position.Y + ';' + this.position.X + ';' + this.position.Y);
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
                move(direction, this);
                output.println("POS " + this.position.X + ';' + this.position.Y + ';' + this.opponent.position.X + ';' + this.opponent.position.Y);
                opponent.output.println("POS " + this.opponent.position.X + ';' + this.opponent.position.Y + ';' + this.position.X + ';' + this.position.Y);
            } 
            catch (IllegalStateException e) {
                output.println("MESSAGE " + e.getMessage());
            }
        }


	}
	
}
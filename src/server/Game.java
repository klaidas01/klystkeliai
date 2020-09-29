package server;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import enums.Constants;

import java.io.IOException;

class Game {
    Player player1;
    Logger logger = Logger.getInstance();

    public void move(char direction, Player player) {
        logger.addMessage(String.valueOf(direction));

        if (player.opponent == null) {
            throw new IllegalStateException("Can't move without an opponent");
        } else if (direction == 'U') {
            player.northWestCoord.Up();
            player.southEastCoord.Up();
        } else if (direction == 'D') {
            player.northWestCoord.Down();
            player.southEastCoord.Down();
        } else if (direction == 'R') {
            player.northWestCoord.Right();
            player.southEastCoord.Right();
        } else if (direction == 'L') {
            player.northWestCoord.Left();
            player.southEastCoord.Left();
        }
    }

    class Player extends BoardObject implements Runnable {
        char mark;
        Player opponent;
        Socket socket;
        Scanner input;
        PrintWriter output;
        MessageFormer former;

        public Player(Socket socket, char mark) {
            former = new MessageFormer(Constants.ROWS_VALUE);
            this.socket = socket;
            this.mark = mark;
            if (mark == 'A') {
                this.northWestCoord = new Coordinates(1, 1);
                this.southEastCoord = new Coordinates(2, 2);
            } else {
                this.northWestCoord = new Coordinates(6, 6);
                this.southEastCoord = new Coordinates(7, 7);
            }
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
            logger.logInfo("WELCOME " + mark);
            if (mark == 'A') {
                player1 = this;
                output.println("MESSAGE Waiting for opponent to connect");
            } else {
                opponent = player1;
                opponent.opponent = this;
                former.newMessage();
                former.AddObject(this.opponent, "P1");
                former.AddObject(this, "P2");
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
                move(direction, this);
                former.newMessage();
                former.AddObject(player1, "P1");
                former.AddObject(player1.opponent, "P2");
                output.println("POS " + former.message);
                opponent.output.println("POS " + former.message);
            } catch (IllegalStateException e) {
                output.println("MESSAGE " + e.getMessage());
            }
        }


    }
}
	
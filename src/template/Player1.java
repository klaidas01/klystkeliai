package template;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import looks.BaseLooks;
import looks.CenterDecorator;
import looks.CornersDecorator;
import looks.color.Blue;
import looks.color.White;
import server.Coordinates;
import server.Game;
import server.Game.Player;

public class Player1 extends Player {

	public Player1(Game game, Socket socket) {
		game.super(socket, game);
	}

	@Override
	public void setLooks() {
		this.looks = new CornersDecorator(new CenterDecorator (new BaseLooks(game.former, this), new White()), new Blue());
		
	}

	@Override
	public void setName() {
		this.name = "P1";
		
	}

	@Override
	public void setupGame() throws IOException {
		input = new Scanner(socket.getInputStream());
        output = new PrintWriter(socket.getOutputStream(), true);
        output.println("WELCOME P1");
        game.consoleLogger.logInfo("WELCOME P1");
        game.fileLogAdapter.logInfo("WELCOME P1");
        game.player1 = this;
        output.println("MESSAGE Waiting for opponent to connect");
    }

	@Override
	public void setPosition() {
		northWestCoord = new Coordinates(1, 1);
		southEastCoord = new Coordinates(4, 4);
	}
}

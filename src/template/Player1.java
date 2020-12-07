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
import server.Player;
import visitor.Visitor;

public class Player1 extends Player {

	public Player1(Socket socket) throws IOException {
		super(socket);
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
        game.consoleLogger.logInfo("WELCOME P1");
        game.fileLogAdapter.logInfo("WELCOME P1");
        game.player1 = this;
    }

	@Override
	public void setPosition() {
		northWestCoord = new Coordinates(2, 2);
		southEastCoord = new Coordinates(5, 5);
	}

	@Override
	public void accept(Visitor<StringBuilder> v) {
		v.visit(this);
	}
}

package template;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import looks.BaseLooks;
import looks.CenterDecorator;
import looks.CornersDecorator;
import looks.color.Blue;
import looks.color.Red;
import server.Coordinates;
import server.Game;
import server.Game.Player;

public class Player2 extends Player {
	public Player2(Game game, Socket socket) {
		game.super(socket, game);
	}

	@Override
	public void setLooks() {
		this.looks = new CornersDecorator(new CenterDecorator (new BaseLooks(game.former, this), new Blue()), new Red());
		
	}

	@Override
	public void setName() {
		this.name = "P2";
	}

	@Override
	public void setupGame() throws IOException {
		input = new Scanner(socket.getInputStream());
        output = new PrintWriter(socket.getOutputStream(), true);
        output.println("WELCOME P2");
		game.logHandler.LogInfo("message", "loginfo.txt");
        game.consoleLogger.logInfo("WELCOME P2");
        game.fileLogAdapter.logInfo("WELCOME P2");
        opponent = game.player1;
        opponent.opponent = this;
        this.opponent.looks.draw();
        this.looks.draw();
        output.println("POS " + game.former.message);
        opponent.output.println("POS " + game.former.message);
        
        game.startTimer();
        
        game.timer.setupSpawnTimers(game);
    }
	
	@Override
	public void setPosition() {
		northWestCoord = new Coordinates(40, 40);
		southEastCoord = new Coordinates(43, 43);
	}

}

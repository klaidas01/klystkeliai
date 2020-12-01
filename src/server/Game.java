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
import logging.ConsoleLogAdapter;
import logging.FileIterator;
import logging.FileLogAdapter;
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
    public volatile boolean shutdown = false;
    
    public Game () throws FileNotFoundException {
    	rand = new Random();
    	timer = new TimerFacade();
    	this.obs = new ArrayList<>();
    	checkLogs();
    }
    
    public void setLevel(String name)
    {
    	switch (name) {
    		case "BOX":
    			currentLevel = LevelBuilder.createBoxLevel();
    			break;
    		case "IBOX":
    			currentLevel = LevelBuilder.createInvisibleBoxLevel();
    			break;
    		case "CROSS":
    			currentLevel = LevelBuilder.createBibleLevel();
    			break;
    	}
    	former = new MessageFormer(Constants.ROWS_VALUE, currentLevel.levelString());
    }
    
    public void startTimer() {
    	this.gameTimer = new GameTimer(this);
    	gameTimer.start();
    }

    private void checkLogs() throws FileNotFoundException {
        for(FileIterator i = fileLogAdapter.getMessages(); i.hasNext();)
            System.out.printf("ITERATOR RETURNED: %s%n", i.next());
    }
}
	
package client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.PrintWriter;

import javax.swing.JPanel;
import javax.swing.Timer;

import enums.Constants;

public class GameFrame extends JPanel implements KeyListener,ActionListener {
	private static final long serialVersionUID = 1L;
	private Timer timer;
	private PrintWriter out;
	boolean left = false, right = false, up = false, down = false;
	KeyPressHandler keyHandler;
	
	public boolean canMove = false;

	public GameFrame(PrintWriter output, boolean useProxy)
	{
		if (useProxy) keyHandler =  new ProxyKeyHandler();
		else keyHandler = new RealPressHandler();
		out = output;
		this.setPreferredSize(new Dimension(Constants.BOARD_SIZE, Constants.BOARD_SIZE));
		this.addKeyListener(this);
        this.setBackground(Color.white);
        this.setLayout(new GridLayout(Constants.ROWS_VALUE, Constants.ROWS_VALUE, 0, 0));
        this.setFocusable(true);
        timer = new Timer(Constants.MOVE_DELAY, this);
        timer.start();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (!canMove) {
			left = false; right = false; up = false; down = false;
		}
		if (left) out.println('L');
		else if (right) out.println('R');
		else if (up) out.println('U');
		else if (down) out.println('D');
		repaint();
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		keyHandler.handleKeyPress(e, this);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		keyHandler.handleKeyRelease(e, this);
	}
	
}

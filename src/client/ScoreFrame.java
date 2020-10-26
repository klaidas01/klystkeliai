package client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JPanel;

import enums.Constants;

public class ScoreFrame extends JPanel{
	private static final long serialVersionUID = 1L;
	
	public ScoreFrame()
	{
		this.setLayout(new GridLayout(1, 3, 10, 10));
		this.setBackground(Color.white);
		this.setPreferredSize(new Dimension(Constants.BOARD_SIZE, (int)(Constants.BOARD_SIZE * 0.05)));
	}
}

package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import client.Interpreter.ExpressionHandler;
import client.Interpreter.FunctionExpression;
import client.Interpreter.MoveExpression;
import client.Interpreter.NumberExpression;
import enums.Constants;

public class client {
	private JFrame frame = new JFrame("Klystkeliai");
	
    private Square[] board = new Square[Constants.ROWS_VALUE * Constants.ROWS_VALUE];
    private JLabel[] scoreboard = new JLabel[3];

    private Socket socket;
    private Scanner in;
    private PrintWriter out;
    GameFrame boardPanel;
    
    public client(String serverAddress) throws Exception {

        socket = new Socket(serverAddress, 8080);
        in = new Scanner(socket.getInputStream());
        out = new PrintWriter(socket.getOutputStream(), true);

        boardPanel = new GameFrame(out, true);
        for (var i = 0; i < board.length; i++) {
            board[i] = new Square();
            boardPanel.add(board[i]);
        }
        
        ScoreFrame scorePanel = new ScoreFrame();
        
        for (var i = 0; i < scoreboard.length; i++) {
        	String text = "";
        	switch (i)
        	{
        		case 0: 
        			text =  "Your score: 0";
        			break;
        		case 1:
        			text = "Time left: 5:00";
        			break;
        		case 2:
        			text = "Opponents score: 0";
        			
        	}
        	scoreboard[i] = new JLabel(text, SwingConstants.CENTER);
        	scoreboard[i].setLayout(new GridBagLayout());
        	Border border = BorderFactory.createLineBorder(Color.BLACK, 5);
        	scoreboard[i].setBorder(border);
        	scorePanel.add(scoreboard[i]);
        }
        
        JPanel mainPanel = new JPanel();
        TextField textField = new TextField();
        JTextField field = new JTextField(60);
        textField.add(field);
        JButton submit = new JButton("Submit");
        submit.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String textFromBox = field.getText();
                ExpressionHandler expressionHandler = new ExpressionHandler(out);
                expressionHandler.exec(textFromBox);
                field.setText("");
                boardPanel.grabFocus();
            }
        });
        textField.add(submit);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(boardPanel);
        mainPanel.add(scorePanel);
        mainPanel.add(textField);
        frame.getContentPane().add(mainPanel, BorderLayout.CENTER);
    }
    
    static class Square extends JPanel {
		private static final long serialVersionUID = 1L;
		JLabel label = new JLabel();

        public Square() {
            setBackground(Color.white);
            setLayout(new GridBagLayout());
            label.setFont(new Font("Arial", Font.BOLD, 40));
            add(label);
        }

        public void setText(char text) {
            label.setText(text + "");
        }
    }
    
    public void play() throws Exception {
        try {
            var response = in.nextLine();
            var mark = response.substring(8);
            frame.setTitle("Player " + mark);
            while (in.hasNextLine()) {
            	frame.repaint();
                response = in.nextLine();
                if (response.startsWith("SCORE"))
                {
                	var scoreString = response.substring(6);
                	String[] scoreStrings = scoreString.split(";", 0);
                	scoreboard[0].setText("Your score: " + scoreStrings[0]);
                	scoreboard[2].setText("Opponents score: " + scoreStrings[1]);
                }
                else if (response.startsWith("WAITING"))
                {
                	scoreboard[1].setText("Waiting for other player..");
                	boardPanel.canMove = false;
                	out.println("WAITING");
                }
                else if (response.startsWith("PLAY"))
                {
                	boardPanel.canMove = true;
                }
                else if (response.startsWith("TIME"))
                {
                	int seconds = Integer.parseInt(response.substring(5));
                	scoreboard[1].setText("Time left: " + (int)(Math.floor(seconds / 60)) + ":" + String.format("%02d", seconds % 60));
                }
                else if (response.startsWith("LEVEL_SELECT"))
                {
                	new LevelSelect(out);
                }
                else if (response.startsWith("POS")) {
                	var map = response.substring(4).toCharArray();
                	for (int i = 0; i < map.length; i++)
                	{
                		switch (map[i]) {
	                		case '1':
	                			board[i].setBackground(Color.lightGray);
	                			break;
	                		case '2':
	                			board[i].setBackground(Color.lightGray);
	                			break;
	                		case 'w':
	                			board[i].setBackground(Color.black);
	                			break;
	                		case 'f':
	                			board[i].setBackground(Color.green);
	                			break;
	                		case 'p':
	                			board[i].setBackground(Color.orange);
	                			break;
	                		case 's':
	                			board[i].setBackground(Color.cyan);
	                			break;
	                		case 'R':
	                			board[i].setBackground(Color.red);
	                			break;
	                		case 'B':
	                			board[i].setBackground(Color.blue);
	                			break;
	                		default:
	                			board[i].setBackground(Color.white);
                		}
                	}
                	
                }
                //System.out.println(response); //for debugging
            }
            out.println("QUIT");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            socket.close();
            frame.dispose();
        }
    }


    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.err.println("Pass the server IP as the sole command line argument");
            return;
        }
        client client = new client(args[0]);
        client.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        client.frame.setSize(Constants.BOARD_SIZE, Constants.BOARD_SIZE + (int)(Constants.BOARD_SIZE * 0.05));
        client.frame.setVisible(true);
        client.frame.setResizable(false);
        client.play();
    }

}

package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import enums.Constants;

public class client {
	private JFrame frame = new JFrame("Klystkeliai");
	
    private Square[] board = new Square[Constants.ROWS_VALUE * Constants.ROWS_VALUE];
    private JLabel[] scoreboard = new JLabel[2];

    private Socket socket;
    private Scanner in;
    private PrintWriter out;
    
    public client(String serverAddress) throws Exception {

        socket = new Socket(serverAddress, 58901);
        in = new Scanner(socket.getInputStream());
        out = new PrintWriter(socket.getOutputStream(), true);

        var boardPanel = new GameFrame(out);
        for (var i = 0; i < board.length; i++) {
            board[i] = new Square();
            boardPanel.add(board[i]);
        }
        
        ScoreFrame scorePanel = new ScoreFrame();
        
        for (var i = 0; i < scoreboard.length; i++) {
        	String text = ((i == 0) ? "Your score: 0" : "Opponents score: 0");
        	scoreboard[i] = new JLabel(text, SwingConstants.CENTER);
        	scoreboard[i].setLayout(new GridBagLayout());
        	Border border = BorderFactory.createLineBorder(Color.BLACK, 5);
        	scoreboard[i].setBorder(border);
        	scorePanel.add(scoreboard[i]);
        }
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(boardPanel);
        mainPanel.add(scorePanel);
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
            var mark = response.charAt(8);
            frame.setTitle("Player " + mark);
            while (in.hasNextLine()) {
            	frame.repaint();
                response = in.nextLine();
                if (response.startsWith("SCORE"))
                {
                	var scoreString = response.substring(6);
                	String[] scoreStrings = scoreString.split(";", 0);
                	scoreboard[0].setText("Your score: " + scoreStrings[0]);
                	scoreboard[1].setText("Opponents score: " + scoreStrings[1]);
                }
                if (response.startsWith("POS")) {
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

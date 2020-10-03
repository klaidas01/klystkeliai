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

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import enums.Constants;

public class client {
	private JFrame frame = new JFrame("Klystkeliai");
    private JLabel messageLabel = new JLabel("...");

    private Square[] board = new Square[Constants.ROWS_VALUE * Constants.ROWS_VALUE];

    private Socket socket;
    private Scanner in;
    private PrintWriter out;
    
    public client(String serverAddress) throws Exception {

        socket = new Socket(serverAddress, 58901);
        in = new Scanner(socket.getInputStream());
        out = new PrintWriter(socket.getOutputStream(), true);

        messageLabel.setBackground(Color.lightGray);
        frame.getContentPane().add(messageLabel, BorderLayout.SOUTH);

        var boardPanel = new JPanel();
        boardPanel.setBackground(Color.white);
        boardPanel.setLayout(new GridLayout(Constants.ROWS_VALUE, Constants.ROWS_VALUE, 0, 0));
        boardPanel.setFocusable(true);
        boardPanel.addKeyListener(new KeyListener() {
        	
        	Timer timer = new Timer(100, (ActionListener)this);
        	
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				timer.start();
				int keyCode = e.getKeyCode();
			    switch( keyCode ) { 
			        case KeyEvent.VK_UP:
			            out.println('U');
			            break;
			        case KeyEvent.VK_DOWN:
			        	out.println('D');
			            break;
			        case KeyEvent.VK_LEFT:
			        	out.println('L');
			            break;
			        case KeyEvent.VK_RIGHT :
			        	out.println('R');
			            break;
			     }
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}
        	
        });
        for (var i = 0; i < board.length; i++) {
            final int j = i;
            board[i] = new Square();
            boardPanel.add(board[i]);
        }
        frame.getContentPane().add(boardPanel, BorderLayout.CENTER);
    }
    
    static class Square extends JPanel {
        JLabel label = new JLabel();

        public Square() {
            setBackground(Color.white);
            setLayout(new GridBagLayout());
            label.setFont(new Font("Arial", Font.BOLD, 40));
            add(label);
        }

        public void setText(char text) {
            label.setForeground(text == 'A' ? Color.BLUE : Color.RED);
            label.setText(text + "");
        }
    }
    
    public void play() throws Exception {
        try {
            var response = in.nextLine();
            var mark = response.charAt(8);
            frame.setTitle("Player " + mark);
            while (in.hasNextLine()) {
                response = in.nextLine();
                if (response.startsWith("POS")) {
                	var map = response.substring(4).toCharArray();
                	for (int i = 0; i < map.length; i++)
                	{
                		switch (map[i]) {
	                		case '1':
	                			board[i].setBackground(Color.blue);
	                			break;
	                		case '2':
	                			board[i].setBackground(Color.red);
	                			break;
	                		case 'w':
	                			board[i].setBackground(Color.black);
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
        client.frame.setSize(1000, 1000);
        client.frame.setVisible(true);
        client.frame.setResizable(false);
        client.play();
    }

}

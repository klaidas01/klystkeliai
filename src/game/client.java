package game;

import java.awt.Font;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.Scanner;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class client {
	private JFrame frame = new JFrame("Tic Tac Toe");
    private JLabel messageLabel = new JLabel("...");

    private Square[] board = new Square[64];

    private Socket socket;
    private Scanner in;
    private PrintWriter out;
    
    int PlayerX = 0;
    int PlayerY = 0;
    int OpponentX = 0;
    int OpponentY = 0;
    
    public client(String serverAddress) throws Exception {

        socket = new Socket(serverAddress, 58901);
        in = new Scanner(socket.getInputStream());
        out = new PrintWriter(socket.getOutputStream(), true);

        messageLabel.setBackground(Color.lightGray);
        frame.getContentPane().add(messageLabel, BorderLayout.SOUTH);

        var boardPanel = new JPanel();
        boardPanel.setBackground(Color.black);
        boardPanel.setLayout(new GridLayout(8, 8, 2, 2));
        boardPanel.setFocusable(true);
        boardPanel.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
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
				// TODO Auto-generated method stub
				
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
            var opponentMark = mark == 'A' ? 'B' : 'A';
            frame.setTitle("Player " + mark);
            while (in.hasNextLine()) {
                response = in.nextLine();
                if (response.startsWith("POS")) {
                	var pos = response.substring(4);
                	int[] coords = Arrays.stream(pos.split(";")).mapToInt(Integer::parseInt).toArray();
                	
                	if (!(PlayerX == OpponentX && PlayerY == OpponentY)) {
                		board[8*OpponentY + OpponentX].setText(' ');
                		board[8*PlayerY + PlayerX].setText(' ');
                	}
                	board[8*coords[3] + coords[2]].setText(opponentMark);
                	
                	board[8*coords[1] + coords[0]].setText(mark);
                	
                	board[8*OpponentY + OpponentX].repaint();
                	board[8*coords[3] + coords[2]].repaint();
                	board[8*PlayerY + PlayerX].repaint();
                	board[8*coords[1] + coords[0]].repaint();
                	
                	PlayerX = coords[0];
                	PlayerY = coords[1];
                	OpponentX = coords[2];
                	OpponentY = coords[3];
                }
                System.out.println(response);
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
        client.frame.setSize(320, 320);
        client.frame.setVisible(true);
        client.frame.setResizable(false);
        client.play();
    }

}

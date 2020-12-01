package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;

import javax.swing.JButton;  
import javax.swing.JFrame;
import javax.swing.JRootPane;  
public class LevelSelect {
	
	PrintWriter output;
	
	LevelSelect(PrintWriter out) {
		output = out;
        JFrame frame=new JFrame();  
        
        frame.setUndecorated(true);
        frame.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
                                 
        JButton box=new JButton("Box level");
        box.setBounds(0,0,300,90);
        
        box.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                out.println("BOX");
                frame.dispose();
            }
        });
        
        JButton ibox=new JButton("Invisible box level");
        ibox.setBounds(0,90,300,90);
        
        ibox.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                out.println("IBOX");
                frame.dispose();
            }
        });
        
        JButton cross=new JButton("Cross level");
        cross.setBounds(0,180,300,90);
        
        cross.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                out.println("CROSS");
                frame.dispose();
            }
        });
             
        frame.add(box);
        frame.add(ibox);  
        frame.add(cross);  
                  
        frame.setSize(300,270);  
        
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }  
}
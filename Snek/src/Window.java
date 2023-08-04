import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Window {
	private JFrame frame;
	
	public Window(int height, int width, String name, Game game) {
		frame = new JFrame(name); //create JFrame to create a window
		WindowListener exitListener = new WindowAdapter() { 
		    @Override
		    public void windowClosing(WindowEvent e) {//if window close button clicked then only set game.running to false
		    	game.running=false;
		    }
		};
		frame.addWindowListener(exitListener);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setResizable(false);
		frame.setBounds(0, 0, width, height);
		frame.add(game); //adding Canvas extended component to JFrame so drawing is easier
		
		frame.setVisible(true);
	}
}

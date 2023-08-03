import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Window {
	private JFrame frame;
	
	public Window(int height, int width, String name, Game game) {
		frame = new JFrame(name); //create JFrame to create a window
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setBounds(0, 0, width, height);
		frame.add(game); //adding Canvas extended component to JFrame so drawing is easier
		
		frame.setVisible(true);
	}
}

import java.awt.Event;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class KeyInput extends KeyAdapter {
	private ArrayList<Entity> Entities;

	Game game;
	public KeyInput(ArrayList<Entity> a) {
		Entities=a;
	}
	
	public void keyPressed(KeyEvent e) {//if key pressed then go through all entities
										//and run their respective actions for a key pressed
		int key = e.getKeyCode();
		for(Entity i:Entities) {
			i.keyinstr(key);
		}
	}
}

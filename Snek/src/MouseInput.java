import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class MouseInput extends MouseAdapter{
	private ArrayList<Entity> Entities;
	public MouseInput(ArrayList<Entity> Entities) {
		this.Entities=Entities;
	}
	
	public void mousePressed(MouseEvent e) {
		int mouse = e.getButton();
		for(int i = 0; i<Entities.size(); i++) {
			Entities.get(i).mousepress(mouse, e.getX(), e.getY());
		}
	}
}

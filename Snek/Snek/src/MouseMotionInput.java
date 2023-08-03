import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;

public class MouseMotionInput extends MouseMotionAdapter {
	private ArrayList<Entity> Entities;
	public MouseMotionInput(ArrayList<Entity> Entities) {
		this.Entities=Entities;
	}
	public void mouseMoved(MouseEvent e) {
		for(Entity i: Entities) {
			i.mousemove(e.getX(), e.getY());
		}
	}

}

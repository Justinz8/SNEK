import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

public class MenuHandler {
	private ArrayList<Entity> Entities;
	private Game game;
	public MenuHandler(ArrayList<Entity> Entities, Game game) {
		this.Entities=Entities;
		this.game=game;
	}
	
	public void init() {
		Entities.add(new Button(200,400,300,100, id.Button, "play snek", Color.black, Color.white, Color.yellow, Color.black, 53, 60, 50) {
			public void mousepress(int mouse, int mx, int my) {
				if(mouse==1&&Tools.inbounds(x, x+velx, mx, mx)&&Tools.inbounds(y, y+vely, my, my)) {
					game.displayChange(DisplayID.Game);
				}
			}
		});
	}
	
	public void tick() {
		for(int i = 0; i<Entities.size(); i++) { //updates all movement
			Entities.get(i).tick();
		}
	}
	
	public void render(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(0, 0, Tools.width, Tools.height);
		
		Font bigFont = new Font("Serif", Font.BOLD, 200);
		g.setColor(Color.black);
		g.setFont(bigFont);
		
		int width = g.getFontMetrics().stringWidth("SNEK");
		g.drawString("SNEK", (Tools.width-width)/2, 200);
		
		Font fot = new Font("Serif", Font.BOLD, 40);
		g.setFont(fot);
		width = g.getFontMetrics().stringWidth("By: Justinz8");
		g.drawString("By: Justinz8", (Tools.width-width)/2, 250);
		
		for(int i = 0; i<Entities.size(); i++) {
			Entities.get(i).render(g);
		}
	}
}

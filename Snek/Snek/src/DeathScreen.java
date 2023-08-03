import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

public class DeathScreen {
	private ArrayList<Entity> Entities;
	private Game game;
	
	public DeathScreen(ArrayList<Entity> Entities, Game game) {
		this.Entities=Entities;
		this.game=game;
	}
	
	public void init() {
		game.amountOfTicks=2;
		game.best[game.speed][game.apples]=Math.max(game.best[game.speed][game.apples], game.lastbodyidx+1);
		Entities.add(new Button(200, 300, 300, 100, id.Button, "Retry?", Color.black, Color.black, Color.yellow, Color.black, 30) {
			public void mousepress(int mouse, int mx, int my) {
				if(mouse==1&&Tools.inbounds(x, x+velx, mx, mx)&&Tools.inbounds(y, y+vely, my, my)) {
					game.displayChange(DisplayID.Game);
				}
			}
		});
		Entities.add(new Button(200, 425, 300, 100, id.Button, "Menu", Color.black, Color.black, Color.yellow, Color.black, 30) {
			public void mousepress(int mouse, int mx, int my) {
				if(mouse==1&&Tools.inbounds(x, x+velx, mx, mx)&&Tools.inbounds(y, y+vely, my, my)) {
					game.displayChange(DisplayID.Menu);
				}
			}
		});
	}
	
	public void tick() {
		for(int i = 0; i<Entities.size(); i++) {
			Entities.get(i).tick();
		}
	}
	public void render(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(0, 0, Tools.width, Tools.height);
		
		g.setColor(Color.black);
		g.setFont(new Font("Serif", Font.BOLD, 30));
		
		int width = (Tools.width-g.getFontMetrics().stringWidth("Final Length: "+(game.lastbodyidx+1)))/2;
		g.drawString("Final Length: "+(game.lastbodyidx+1), width, 100);
		
		width = (Tools.width-g.getFontMetrics().stringWidth("Best on settings: speed: "+game.speed+"x and apple#: "+game.apples+"x"))/2;
		g.drawString("Best on settings: speed: "+game.speed+"x and apple#: "+game.apples+"x", width, 150);
		
		g.setFont(new Font("Serif", Font.BOLD, 40));
		width = (Tools.width-g.getFontMetrics().stringWidth("-"+game.best[game.speed][game.apples]+"-"))/2;
		g.drawString("-"+game.best[game.speed][game.apples]+"-", width, 190);
		
		for(int i = 0; i<Entities.size(); i++) {
			Entities.get(i).render(g);
		}
	}
}

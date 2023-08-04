import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

public class SessionLeaderHandler {
	private Game game;
	private ArrayList<Entity> Entities;
	
	public SessionLeaderHandler(Game game, ArrayList<Entity> Entities) {
		this.game=game;
		this.Entities=Entities;
	}
	public void init() {
		Entities.add(new Button(25,25,100,50, id.Button, "Menu", Color.black, Color.white, Color.yellow, Color.black, 15) {
			public void mousepress(int mouse, int mx, int my) {
				if(mouse==1&&Tools.inbounds(x, x+velx, mx, mx)&&Tools.inbounds(y, y+vely, my, my)) {
					game.displayChange(DisplayID.Menu);
				}
			}
		});
		Entities.add(new Button(465,25,200,50, id.Button, "Local Leaderboard", Color.black, Color.white, Color.yellow, Color.black, 15) {
			public void mousepress(int mouse, int mx, int my) {
				if(mouse==1&&Tools.inbounds(x, x+velx, mx, mx)&&Tools.inbounds(y, y+vely, my, my)) {
					game.displayChange(DisplayID.LocalLeader);
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
		
		
		
		g.setFont(new Font("Serif", Font.BOLD, 23));
		int width = g.getFontMetrics().stringWidth("Session LeaderBoard");
		g.drawString("Session LeaderBoard", (Tools.width-width)/2, 75);
		
		g.setFont(new Font("Serif", Font.BOLD, 20));
		g.drawString("Settings", 150, 125);
		g.drawString("Score", 450, 125);
		
		int idx = 0;
		
		for(int i = 1; i<=5; i++) {
			for(int j = 1; j<=3; j++) {
				g.drawString("Speed: "+i+" Apple#: "+j, 150, idx*30+155);
				idx++;
			}
		}
		
		idx=0;
		
		for(int i = 1; i<=5; i++) {
			for(int j = 1; j<=3; j++) {
				g.drawString(game.best[i][j]+"", 450, idx*30+155);
				idx++;
			}
		}
		
		
		for(int i = 0; i<Entities.size(); i++) {
			Entities.get(i).render(g);
		}
	}
}

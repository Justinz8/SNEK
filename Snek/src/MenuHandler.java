import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;

public class MenuHandler { //Handler for menu page
	private ArrayList<Entity> Entities;
	private Game game;
	private int initsize;
	
	public MenuHandler(ArrayList<Entity> Entities, Game game) {
		this.Entities=Entities;
		this.game=game;
	}
	
	public void init() {
		game.amountOfTicks=2;
		
		//play button
		Entities.add(new Button(200,275,300,100, id.Button, "play snek", Color.black, Color.white, Color.yellow, Color.black, 50) {
			public void mousepress(int mouse, int mx, int my) {
				if(mouse==1&&Tools.inbounds(x, x+velx, mx, mx)&&Tools.inbounds(y, y+vely, my, my)) {
					game.displayChange(DisplayID.Game);
				}
			}
		});
		
		//change speed button
		Entities.add(new Button(200,390,125,100, id.Button, "speed:"+game.speed+"x", Color.black, Color.white, Color.yellow, Color.black, 25) {
			public void mousepress(int mouse, int mx, int my) {
				if(mouse==1&&Tools.inbounds(x, x+velx, mx, mx)&&Tools.inbounds(y, y+vely, my, my)) {
					game.speed=game.speed+1;
					if(game.speed>=6) game.speed=1;
				}
			}
			public void render(Graphics g) { //update render to accomodate for varying text in button
				g.setColor(curbordercolor);
				g.drawRect(x, y, velx, vely);
				g.setColor(curbordercolor);
				Font bigFont = new Font("Serif", Font.BOLD, fontsize);
				g.setFont(bigFont);
				int width = (velx-g.getFontMetrics().stringWidth("speed:"+game.speed+"x"))/2+x;
				int height = y+vely-(vely-g.getFontMetrics().getLeading())/2;
				g.drawString("speed:"+game.speed+"x", width, height);
			}
		});
		
		//change #apples button
		Entities.add(new Button(375,390,125,100, id.Button, "#apps:"+game.apples+"x", Color.black, Color.white, Color.yellow, Color.black, 25) {
			public void mousepress(int mouse, int mx, int my) {
				if(mouse==1&&Tools.inbounds(x, x+velx, mx, mx)&&Tools.inbounds(y, y+vely, my, my)) {
					game.apples=game.apples+1;
					if(game.apples>=4) game.apples=1;
				}
			}
			public void render(Graphics g) { //update render to accomodate for varying text in button
				g.setColor(curbordercolor);
				g.drawRect(x, y, velx, vely);
				g.setColor(curbordercolor);
				Font bigFont = new Font("Serif", Font.BOLD, fontsize);
				g.setFont(bigFont);
				int width = (velx-g.getFontMetrics().stringWidth("#apps:"+game.apples+"x"))/2+x;
				int height = y+vely-(vely-g.getFontMetrics().getLeading())/2;
				g.drawString("#apps:"+game.apples+"x", width, height);
			}
		});
		
		//Leaderboards button
		Entities.add(new Button(200,505,300,100, id.Button, "Leaderboards", Color.black, Color.white, Color.yellow, Color.black, 25) {
			public void mousepress(int mouse, int mx, int my) {
				if(mouse==1&&Tools.inbounds(x, x+velx, mx, mx)&&Tools.inbounds(y, y+vely, my, my)) {
					game.displayChange(DisplayID.SessionLeader);
				}
			}
		});
		
		//spawn snake thats in the menu
		SpawnMenuSnake();

		initsize=Entities.size();
	}
	
	private void SpawnMenuSnake() {
		Arrays.fill(game.bodydir, -1);
		Entities.add(new PlayerHead(-50, 400, 0,0,1, id.PlayerHead, game) { //spawn PlayerHead but without any key inputs
			public void keyinstr(int key) { }
			public void ded() {}
		});
		
		for(int i = 1; i<=3; i++) {//3 player bodies with its proper bodyidx, i
			Entities.add(new PlayerBody(-50, 400, 0,0, id.PlayerBody, game, i));
		}
	}
	
	public void tick() {
		if(initsize-4>=Entities.size()) { //if entire menusnake despawned then spawn another
			SpawnMenuSnake();
		}
		//System.out.println(Entities.size()+" "+initsize);
		for(int i = 0; i<Entities.size(); i++) { //updates all movement
			Entities.get(i).tick();
			if(Entities.get(i).ID==id.PlayerHead||Entities.get(i).ID==id.PlayerBody) {
				if(Entities.get(i).x>=700) {//if menu snake segment goes off screen then despawn
					Entities.remove(i);
					i--;
				}
			}
		}
	}
	
	public void render(Graphics g) {
		//background
		g.setColor(Color.white);
		g.fillRect(0, 0, Tools.width, Tools.height);
		
		Font bigFont = new Font("Serif", Font.BOLD, 200);
		g.setColor(Color.black);
		g.setFont(bigFont);
		
		//width=x coord needed for message to be in the middle of the screen
		int width = g.getFontMetrics().stringWidth("SNEK");
		g.drawString("SNEK", (Tools.width-width)/2, 200);
		
		Font fot = new Font("Serif", Font.BOLD, 40);
		g.setFont(fot);
		width = g.getFontMetrics().stringWidth("By: Justinz8");
		g.drawString("By: Justinz8", (Tools.width-width)/2, 250);
		
		for(int i = 0; i<Entities.size(); i++) { //render snake first so its underneath everything else

			if(Entities.get(i).ID==id.PlayerHead||Entities.get(i).ID==id.PlayerBody) {
				Entities.get(i).render(g);
			}
		}
		for(int i = 0; i<Entities.size(); i++) {
			if(!(Entities.get(i).ID==id.PlayerHead||Entities.get(i).ID==id.PlayerBody)) {
				Entities.get(i).render(g);
			}
		}
	}
}

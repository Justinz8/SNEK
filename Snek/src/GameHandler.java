import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

public class GameHandler {
	private ArrayList<Entity> Entities;
	Game game;

	public GameHandler(ArrayList<Entity> Entities, Game game) {
		this.game=game;
		this.Entities=Entities;
	}

	public void init() {
		//spawn player head
		Entities.add(new PlayerHead(300, 300, 0, 0, 0, id.PlayerHead, game));
		game.spawnApple();
	}

	public void render(Graphics g) {
		g.setColor(Color.white);//background
		g.fillRect(0, 0, Tools.width, Tools.height);

		//board
		g.setColor(Color.black);

		g.setFont(new Font("Serif", Font.BOLD, 25));
		g.drawString("Length: "+(game.lastbodyidx+1), 50, 50);

		if(game.playing==false) {
			g.setFont(new Font("Serif", Font.BOLD, 30));
			int width = g.getFontMetrics().stringWidth("Press arrow keys to start!");
			g.drawString("Press arrow keys to start!", (Tools.width-width)/2, 75);
		}

		for(int curx = 100; curx<=550; curx+=50) {
			for(int cury = 100; cury<=550; cury+=50) {
				g.drawRect(curx, cury, 50, 50);
			}
		}

		//render all other game objects
		for(Entity i:Entities) {
			i.render(g);
		}
	}

	public void tick() {
		for(int i = 0; i<Entities.size(); i++) { //updates all movement
			Entities.get(i).tick();
		}

		for(int i = 0; i<Entities.size(); i++) { //after movement checks
			if(Entities.get(i).ID==id.Apple) { //check if apple has been eaten
				Entities.get(i).tick();
				if(game.eaten==true) {//adds new player body to end
					Entities.add(new PlayerBody(game.tailcoord[0], game.tailcoord[1], 0, 0, id.PlayerBody, game, ++game.lastbodyidx));
					Entities.remove(i); //removes apple
					i--;
				}
				//check if head has collided with body
				//if apple has been eaten this tick then new body spawned potentially on
				//head is not considered as a collision
			}else if(Entities.get(i).ID==id.PlayerBody&&game.eaten==false) { 
				if(Entities.get(i).x==game.headcoord[0]&&Entities.get(i).y==game.headcoord[1]) {
					game.dead();
				}
			}
		}
		if(game.eaten==true) {
			game.eaten=false;
			game.spawnApple();
		}
	}
}

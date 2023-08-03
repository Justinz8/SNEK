import java.awt.Color;
import java.awt.Graphics;

public class Apple extends Entity {

	Game game;
	public Apple(int x, int y, int vx, int vy, id ID, Game game) {
		super(x, y, vx, vy, ID);
		this.game=game;
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.red);
		g.fillRect(x, y, 50, 50);
	}

	@Override
	public void tick() { //if apple on head of snake then it has been eaten
		if(game.headcoord[0]==x&&game.headcoord[1]==y) {
			game.eaten=true;
		}
	}

}

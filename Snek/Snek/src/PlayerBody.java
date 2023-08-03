import java.awt.Color;
import java.awt.Graphics;

public class PlayerBody extends Entity {
	
	private Game game;
	private int bodyidx, curdir=-1; //0 = up, 1 = right, 2 = left, 3 = down
	
	public PlayerBody(int x, int y, int vx, int vy, id ID, Game game, int bodyidx) {
		super(x, y, vx, vy, ID);
		this.game=game;
		this.bodyidx=bodyidx;
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.green);
		g.fillRect(x, y, 50, 50);
		g.setColor(Color.black);
		g.drawRect(x, y, 50, 50);
		
	}

	@Override
	public void tick() {
		switch(curdir) {//go in current set direction
		case 0:
			game.bodydir[bodyidx] = 0;
			velx = 0;
			vely = -50;
			break;
		case 1:
			game.bodydir[bodyidx] = 1;
			velx = 50;
			vely = 0;
			break;
		case 2:
			game.bodydir[bodyidx] = 2;
			velx = -50;
			vely=0;
			break;
		case 3:
			game.bodydir[bodyidx] = 3;
			velx = 0;
			vely=50;
			break;
		default:
			break;
		}
		//update position based on velocity
		x+=velx;
		y+=vely;
		//if this body part is last body part update the tailcoords
		if(bodyidx==game.lastbodyidx) {
			game.tailcoord[0]=x;
			game.tailcoord[1]=y;
		}
		//take on new direction from the body part in front of this one
		//using bodydir array
		curdir = game.bodydir[bodyidx-1];
		
	}

}

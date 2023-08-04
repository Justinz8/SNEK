import java.awt.Color;
import java.awt.Event;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PlayerHead extends Entity{
	private Game game;
	private int dir, dir1=-1; //0 = up, 1 = right, 2 = left, 3 = down

	public PlayerHead(int x, int y, int vx, int vy,int dir, id ID, Game game) {
		super(x, y, vx, vy, ID);
		this.game=game;
		this.dir1=dir;
	}

	public void render(Graphics g) {
		g.setColor(new Color(1, 50, 32));
		g.fillRect(x, y, 50, 50);
		g.setColor(Color.black);
		g.drawRect(x, y, 50, 50);
	}


	public void tick() {
		dir=dir1;
		switch(dir) { //go in current set direction
		case 0:
			game.bodydir[0] = 0;
			velx = 0;
			vely = -50;
			break;
		case 1:
			game.bodydir[0] = 1;
			velx = 50;
			vely = 0;
			break;
		case 2:
			game.bodydir[0] = 2;
			velx = -50;
			vely=0;
			break;
		case 3:
			game.bodydir[0] = 3;
			velx = 0;
			vely=50;
			break;
		default:
			break;
		}
		//update position based on velocity
		x = x+velx;
		y = y+vely;
		//update head coords
		game.headcoord[0]=x;
		game.headcoord[1]=y;
		//if head is tail the update tail coords as well
		if(0==game.lastbodyidx) {
			game.tailcoord[0]=x;
			game.tailcoord[1]=y;
		}
		//if head hits edge then gg
		if(!Tools.inbounds(100, 550, x, x)||!Tools.inbounds(100, 550, y, y)) {
			ded();
		}
	}
	
	public void ded() {
		velx=0;
		vely=0;
		game.displayChange(DisplayID.Death);;
	}

	@Override
	public void keyinstr(int key) {//key updates which direction head is going to
		if(key==KeyEvent.VK_RIGHT&&(dir!=2||(dir==2&&game.lastbodyidx==0))) {
			game.playing=true;
			dir1 = 1;
		}
		if(key==KeyEvent.VK_UP&&(dir!=3||(dir==3&&game.lastbodyidx==0))) {
			game.playing=true;
			dir1 = 0;
		}
		if(key==KeyEvent.VK_DOWN&&(dir!=0||(dir==0&&game.lastbodyidx==0))) {
			game.playing=true;
			dir1 = 3;
		}
		if(key==KeyEvent.VK_LEFT&&(dir!=1||(dir==1&&game.lastbodyidx==0))) {
			game.playing=true;
			dir1 = 2;
		}
	}





}

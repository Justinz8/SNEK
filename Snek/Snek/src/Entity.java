import java.awt.Graphics;

public abstract class Entity {
	//game object class for all game objects
	protected int x;
	protected int y;
	protected int velx;
	protected int vely;
	protected id ID;
	
	public Entity(int x, int y, int vx, int vy, id ID) {
		this.x=x;
		this.y=y;
		velx=vx;
		vely=vy;
		this.ID=ID;
	}
	
	public abstract void render(Graphics g);
	
	public abstract void tick();
	
	public void keyinstr(int key) {};//instructions for when a key is pressed
	
	public void mousepress(int mouse, int mx, int my) {};
	
	public void mousemove(int mx, int my) {};
	
	public int getx() {
		return x;
	}
	public int gety() {
		return y;
	}
	public int getvelx() {
		return velx;
	}
	public int getvely() {
		return vely;
	}
	public void setx(int n) {
		x=n;
	}
	public void sety(int n) {
		y=n;
	}
	public void setvelx(int n) {
		velx=n;
	}
	public void setvely(int n) {
		vely=n;
	}
	
	
	
	
}

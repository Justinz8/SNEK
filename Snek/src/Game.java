import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Game extends Canvas implements Runnable{
	//Canvas is a component that you draw on
	//extending to Canvas adds more functionality onto it through own code

	//is running and is alive
	public static boolean running, alive;

	private static final long serialVersionUID = 3626645571913123114L;
	
	private DisplayID DID;

	private ArrayList<Entity> Entities; //list of entities to be rendered
	private Thread thread; //single thread to run game
	//coords of both the head of the snek and tail
	public int headcoord[]=new int[2], tailcoord[] = new int[2]; //0=x, 1=y
	public int lastbodyidx=0;//tracks the last idx of the body
	public boolean eaten = false;//tracks if an apple has been eaten this tick
	private Random rand = new Random();//random object for apple
	private GameHandler gh;
	private MenuHandler mh;
	private DeathScreen ds;
	public int best[][]=new int[6][4];
	
	public double amountOfTicks;
	
	public int speed=1;
	public int apples=1;
	

	public int bodydir[];//array of directions for each body segment
	public boolean playing;

	public Game() {
		//create a window
		new Window(Tools.height, Tools.width, "snek kek", this);
		DID=DisplayID.Menu;
		running = true;
		alive=true;
		Entities = new ArrayList<Entity>();
		bodydir = new int[1005];
		Arrays.fill(bodydir, -1);
		gh = new GameHandler(Entities, this);
		mh = new MenuHandler(Entities, this);
		ds = new DeathScreen(Entities, this);
		//add keylistener so we can input using keyboard
		//uses KeyInput class for keylistener as it extends KeyAdapter
		this.addKeyListener(new KeyInput(Entities));
		this.addMouseListener(new MouseInput(Entities));
		this.addMouseMotionListener(new MouseMotionInput(Entities));
		thread = new Thread(this);
		thread.start();
	}

	public void run() {
		mh.init();

		//game loop:
		long lastTime = System.nanoTime();
		amountOfTicks = 2; //amount of ticks/s we want
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		while(running){
			long now = System.nanoTime();
			delta += (now - lastTime) / (1000000000 / amountOfTicks); //amount of ticks we suppose to run this loop
			lastTime = now;
			while(delta >= 1){
				tick();
				delta--;
			}
			if(running)
				render();
			frames++;

			if(System.currentTimeMillis() - timer > 1000){
				timer += 1000;
				System.out.println("FPS: " + frames);
				frames = 0;
			}
		}
		stop();
	}

	public void stop() {
		playing=false;
		running=false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void tick() {
		//System.out.println(Entities.size());
		//System.out.println(amountOfTicks);
		if(DID==DisplayID.Game) {
			gh.tick();
		}else if(DID==DisplayID.Menu) {
			mh.tick();
		}else if(DID==DisplayID.Death) {
			ds.tick();
		}
	}

	public void render() {
		BufferStrategy bs = this.getBufferStrategy(); //grab bufferstrategy from our canvas component
		if(bs==null) {//create bs for canvas if we dont have one already
			this.createBufferStrategy(2);
			return;
		}

		Graphics g = bs.getDrawGraphics();

		if(DID==DisplayID.Game) {
			gh.render(g);
		}else if(DID==DisplayID.Menu) {
			mh.render(g);
		}else if(DID==DisplayID.Death) {
			ds.render(g);
		}

		g.dispose();
		bs.show();
	}

	public void dead() { //clear every object if we ded
		alive=false;
		displayChange(DisplayID.Death);
	}

	public void spawnApple() {
		//spawn random apple on grid that is not directly on players head or body
		
		//available slots left = total slots-how big the snake is
		//if not enough slots left for apple then dont spawn apple and reduce apple count by one
		if(100-(lastbodyidx+1)<apples) {
			apples--;
			return;
		}
		
		int x = rand.nextInt(10)*50+100;
		int y = rand.nextInt(10)*50+100;
		boolean reroll = false;
		while(true) {
			reroll=false;
			for(Entity i: Entities) {
				if(x==i.x&&y==i.y&&(i.ID==id.PlayerBody||i.ID==id.PlayerHead||i.ID==id.Apple)) {
					reroll=true;
				}
			}
			if(reroll==true) {
				x = rand.nextInt(10)*50+100;
				y = rand.nextInt(10)*50+100;
			}else {
				break;
			}
		}
		Entities.add(new Apple(x, y, 0, 0, id.Apple, this));
	}
	
	public void displayChange(DisplayID changeid) {
		DID=changeid;
		Entities.clear();
		switch(changeid) {
		case Menu:
			mh.init();
			break;
		case Game:
			gh.init();
			break;
		case Death:
			ds.init();
			break;
		}
	}
}

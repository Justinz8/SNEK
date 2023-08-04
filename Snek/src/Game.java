import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferStrategy;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Game extends Canvas implements Runnable{
	//Canvas is a component that you draw on
	//extending to Canvas adds more functionality onto it through own code

	//is running and is alive
	public static boolean running;

	private static final long serialVersionUID = 3626645571913123114L;

	private DisplayID DID;

	private ArrayList<Entity> Entities; //list of entities to be rendered
	private Thread thread; //single thread to run game
	//coords of both the head of the snek and tail
	public int headcoord[]=new int[2], tailcoord[] = new int[2]; //0=x, 1=y
	public int lastbodyidx=0;//tracks the last idx of the body
	public boolean eaten = false;//tracks if an apple has been eaten this tick
	private Random rand = new Random();//random object for apple
	
	//Handlers for each page in the game
	private GameHandler gh;
	private MenuHandler mh;
	private DeathScreen ds;
	private LocalLeaderHandler llh;
	private SessionLeaderHandler slh;
	
	//File handle classes to read, write, and create txt file for leaderboard stored locally to be compared
	//between game instances
	private FileWriter fw;
	private FileReader reader;
	private File LocalLeaderText;
	
	//Leaderboard storage for each potential setting both locally and also for just this instance
	public int best[][];
	public int localbest[][];
	
	//amount of ticks ran per second in the game loop
	public double amountOfTicks;

	//settings of the game
	public int speed=1;
	public int apples=1;
	
	//is the game in play
	public boolean playing;

	//array of directions for each body segment
	public int bodydir[];
	

	public Game() {

		//create a window
		new Window(Tools.height, Tools.width, "snek kek", this);
		//start at menu screen
		DID=DisplayID.Menu;
		
		running = true;
		Entities = new ArrayList<Entity>();
		
		bodydir = new int[1005];
		best=new int[6][4];
		localbest = new int[6][4];
		Arrays.fill(bodydir, -1);
		
		gh = new GameHandler(Entities, this);
		mh = new MenuHandler(Entities, this);
		ds = new DeathScreen(Entities, this);
		slh=new SessionLeaderHandler(this, Entities);
		llh = new LocalLeaderHandler(this, Entities);
		
		//add keylistener so we can input using keyboard
		//uses KeyInput class for keylistener as it extends KeyAdapter
		this.addKeyListener(new KeyInput(Entities));
		//same concept is used for mouselistener and mousemotionlistener
		this.addMouseListener(new MouseInput(Entities));
		this.addMouseMotionListener(new MouseMotionInput(Entities));
		
		//initialize LocalLeaderboard
		try {
			initializeLocalLeader();
		} catch (IOException e) {
			e.printStackTrace();
		} catch(java.lang.NumberFormatException e) {//if format for localleaderboard is wrong then recreate leaderboard
			try {
				reconstructLeader();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
		//start thread
		thread = new Thread(this);
		thread.start();
	}

	public void run() {
		mh.init();//run menu handler initializer as we start at menu screen

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
			//render every loop
			if(running)
				render();
			
			frames++;

			if(System.currentTimeMillis() - timer > 1000){//output fps
				timer += 1000;
				System.out.println("FPS: " + frames);
				frames = 0;
			}
		}
		stop();
	}

	public void stop() {//called once program stops
		playing=false;
		running=false;
		System.out.println("Stopped");
		
		try {//update local leaderboard
			UpdateLocal();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		System.exit(0);//close application		
	}

	public void tick() {
		if(!running) return;
		//System.out.println(Entities.size());
		//System.out.println(amountOfTicks);
		
		//use the designated handler depending which screen we're currently on
		if(DID==DisplayID.Game) {
			gh.tick();
		}else if(DID==DisplayID.Menu) {
			mh.tick();
		}else if(DID==DisplayID.Death) {
			ds.tick();
		}else if(DID==DisplayID.SessionLeader) {
			slh.tick();
		}
		else if(DID==DisplayID.LocalLeader) {
			llh.tick();
		}
	}

	public void render() {
		if(!running) return;
		BufferStrategy bs = this.getBufferStrategy(); //grab bufferstrategy from our canvas component
		if(bs==null) {//create bs for canvas if we dont have one already
			this.createBufferStrategy(2);
			return;
		}
		Graphics g = bs.getDrawGraphics();

		//use the designated handler depending which screen we're currently on
		if(DID==DisplayID.Game) {
			gh.render(g);
		}else if(DID==DisplayID.Menu) {
			mh.render(g);
		}else if(DID==DisplayID.Death) {
			ds.render(g);
		}else if(DID==DisplayID.SessionLeader) {
			slh.render(g);
		}else if(DID==DisplayID.LocalLeader) {
			llh.render(g);
		}
		g.dispose(); //clear graphic memory
		bs.show(); //show drawn on bufferedstrategy
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
		case SessionLeader:
			slh.init();
			break;
		case LocalLeader:
			llh.init();
			break;
		}
	}
	
	private void UpdateLocal() throws IOException{
		fw = new FileWriter("Leader.txt");
		for(int i = 0; i<=5; i++) {
			for(int j = 0; j<=3; j++) {
				fw.write(localbest[i][j]+"");
				if(j!=3)fw.write(' ');
			}
			fw.write("\r\n");
		}
		fw.close();
	}
	
	private void reconstructLeader() throws IOException {
		fw = new FileWriter("Leader.txt");
		for(int i = 0; i<=6; i++) {
			for(int j = 0; j<=3; j++) {
				fw.write('0');
				if(j!=3)fw.write(' ');
			}
			fw.write("\r\n");
		}
		fw.close();
	}

	private void initializeLocalLeader() throws IOException {
		LocalLeaderText = new File("Leader.txt");

		if(!LocalLeaderText.exists()) {
			LocalLeaderText.createNewFile();
			reconstructLeader();
			return;
		}

		reader = new FileReader("Leader.txt");

		BufferedReader br = new BufferedReader(reader);

		for(int i = 0; i<=5; i++) {
			String A[] = br.readLine().split(" ");
			if(i==0) continue;
			for(int j = 1; j<=3; j++) {
				localbest[i][j]=Integer.parseInt(A[j]);
			}
		}
	}
}

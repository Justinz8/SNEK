import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Button extends Entity { //class to create a button
	
	public String content; //text inside of button
	public Color border, inside, hiborder, textcolor, curbordercolor; //color scheme of button
	public int fontsize; //fontsize of text inside of button
	
	
	public Button(int x, int y, int vx, int vy, id ID, String content, Color b,Color i,Color hb,Color tc, int fz) {
		super(x, y, vx, vy, ID);
		this.content=content;
		border=b;
		inside=i;
		hiborder=hb;
		textcolor=tc;
		curbordercolor=b;

		fontsize=fz;
	}

	@Override
	public void render(Graphics g) {
		g.setColor(inside);
		//g.fillRect(x, y, velx, vely);
		
		g.setColor(curbordercolor);
		g.drawRect(x, y, velx, vely);
		
		g.setColor(curbordercolor);
		Font bigFont = new Font("Serif", Font.BOLD, fontsize); //create new font with proper fontsize
		g.setFont(bigFont);
		
		//get proper coords as to where to draw the string in order for the string to be positioned in the middle of the button
		int width = (velx-g.getFontMetrics().stringWidth(content))/2+x;
		int height = y+vely-(vely-g.getFontMetrics().getLeading())/2;
		g.drawString(content, width, height);
	}
	
	public void mousemove(int mx, int my) {
		//if mouse if hovering over the button switch colors to highlighted colors
		if(Tools.inbounds(x, x+velx, mx, mx)&&Tools.inbounds(y, y+vely, my, my)) {
			curbordercolor=hiborder;
		}else {
			curbordercolor=border;
		}
	}
	


	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}

}

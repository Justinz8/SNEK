import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Button extends Entity {
	
	public String content;
	public Color border, inside, hiborder, textcolor, curbordercolor;
	public int tx, ty, fontsize;
	
	
	public Button(int x, int y, int vx, int vy, id ID, String content, Color b,Color i,Color hb,Color tc, int tx, int ty, int fz) {
		super(x, y, vx, vy, ID);
		this.content=content;
		border=b;
		inside=i;
		hiborder=hb;
		textcolor=tc;
		curbordercolor=b;
		this.tx=tx+x;
		this.ty=ty+y;
		fontsize=fz;
	}

	@Override
	public void render(Graphics g) {
		g.setColor(inside);
		g.fillRect(x, y, velx, vely);
		g.setColor(curbordercolor);
		g.drawRect(x, y, velx, vely);
		g.setColor(curbordercolor);
		Font bigFont = new Font("Serif", Font.BOLD, fontsize);
		g.setFont(bigFont);
		int width = (velx-g.getFontMetrics().stringWidth(content))/2+x;
		int height = y+g.getFontMetrics().getHeight();
		g.drawString(content, width, height);
	}
	
	public void mousemove(int mx, int my) {
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

package interfaz;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;

import javax.swing.JComponent;
import javax.swing.Painter;

public class ScrollPainter implements Painter<JComponent> {

	public static int THUMB = 0;
	public static int BUTTON = 1;
	public static int TRACK = 2;
	
	private Color claro; 
	private Color oscuro;
	private int parte;
	
	public ScrollPainter(int parte) {
		this.parte = parte;
		this.claro = new Color(156, 74, 8);
		this.oscuro = new Color(96, 44, 8);
	}
	
	
	@Override
	public void paint(Graphics2D g, JComponent object, int width, int height) {
		if(parte == THUMB) {
			 thumb(g, object, width, height);
		}
		else if(parte == BUTTON) {
			 boton(g, object, width, height);
		}
		else if(parte == TRACK) {
			 track(g, object, width, height);
		}
	}


	private void boton(Graphics2D g, JComponent object, int width, int height) {
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setColor(Color.ORANGE);
		Polygon p = new Polygon() ;
		p.addPoint(2, height/2);
		p.addPoint(width-2, height-2);
		p.addPoint(width-2, 2);
		g.fillPolygon(p);
	}


	private void thumb(Graphics2D g, JComponent object, int width, int height) {
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		GradientPaint gradPaint = new GradientPaint(width/2, height, oscuro, width/2, height/2, claro, true);
	    g.setPaint(gradPaint);
		g.fillRoundRect(0, 0, width, height, height/2, height/2);
		g.setStroke(new BasicStroke());
		g.setColor(Color.BLACK);
		g.drawRoundRect(0, 0, width, height, height/2, height/2);
	}
	
	private void track(Graphics2D g, JComponent object, int width, int height) {
		
	}
}

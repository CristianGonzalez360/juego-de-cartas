package interfaz;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.RenderingHints;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.Painter;

public class BotonPainter implements Painter<JButton> {
	
	private int tipo;
	private Color claro; 
	private Color oscuro;
	
	public static int ENABLED = 0;
	public static int MOUSE_OVER = 1;
	public static int DISABLED = 2;
	public static int PRESSED = 3;
	
	public BotonPainter(int tipo) {
		this.tipo = tipo;
		this.claro = new Color(156, 74, 8);
		this.oscuro = new Color(96, 44, 8);
		
	}

	@Override
	public void paint(Graphics2D g, JButton object, int width, int height) {
		if(tipo == ENABLED) {
			enabled(g, object, width, height);
		}
		else if(tipo == MOUSE_OVER) {
			mouseOver(g, object, width, height);
		}
		else if(tipo == DISABLED) {
			disabled(g, object, width, height);
		}
	}

	private void disabled(Graphics2D g, JButton object, int width, int height) {
		g.setColor(Color.BLACK);
		g.fillRoundRect(1, 1, width-3, height-3, height/2, height/2);
		g.setColor(Color.WHITE);
		g.drawRoundRect(1, 1, width-3, height-3, height/2, height/2);
	}

	private void enabled(Graphics2D g, JButton object, int width, int height) {
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		GradientPaint gradPaint = new GradientPaint(0, 0, claro, width-3, height-3 , oscuro);
	    g.setPaint(gradPaint);
		g.fillRoundRect(1, 1, width-3, height-3, height/2, height/2);
		g.setStroke(new BasicStroke());
		g.setColor(Color.BLACK);
		g.drawRoundRect(1, 1, width-3, height-3, height/2, height/2);
	}
	
	private void mouseOver(Graphics2D g, JButton object, int width, int height) {
		enabled(g, object, width, height);
		g.setStroke(new BasicStroke(1));
		g.setColor(Color.ORANGE);
		g.drawRoundRect(0, 0, width-1, height-1, height/2, height/2);
	}
	
}

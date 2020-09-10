package interfaz;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

public class Cartel extends JLabel {
	
	private LinkedList<Mensaje> mensajes;
	private Timer t;
	private boolean mostrando;
	private VistaTablero vistaTablero;
	
	public Cartel(VistaTablero vistaTablero) {
		this.vistaTablero = vistaTablero;
		setHorizontalTextPosition(SwingConstants.CENTER);
		mensajes = new LinkedList<>();	
		t = new Timer();
		
		setOpaque(true);
		setBorder(new LineBorder(Color.ORANGE));
		setBackground(Color.BLACK);
		setForeground(Color.ORANGE);
		setHorizontalAlignment(SwingConstants.CENTER);
		setFont(new Font("Gwent", Font.BOLD, Graficos.getTamañoFuente(70)));
		setVisible(false);
	}
	
	public void encolarMensaje(String mensaje, int tiempo) {
		mensajes.add(new Mensaje(mensaje, tiempo));
	}
	
	public void mostrarMensaje(String mensaje, int tiempo) {
		mensajes.add(new Mensaje(mensaje, tiempo));
		if(!mostrando) {
			vistaTablero.bloquear();
			siguienteMensaje();
		}
	}
	
	public void siguienteMensaje() {
		if(!mensajes.isEmpty()) {
			mostrando = true;
			Mensaje m = mensajes.remove();
			setText(m.mensaje);
			ajustar();
			setVisible(true);
			t.schedule(new TimerTask() {
				@Override
				public void run() {
					siguienteMensaje();
				}
			}, m.tiempo);
		}
		else {
			mostrando = false;
			setVisible(false);
			vistaTablero.desbloquear();
		}
	}
	
	private void ajustar() {
		Graphics2D g = (Graphics2D) getGraphics();
		FontMetrics m = g.getFontMetrics();	
		int alto = m.getHeight() + 10;
		int y = (Graficos.getTamañoTablero().height/2) - (alto/2);
		int ancho = m.stringWidth(getText()) + 10;
		int x = (Graficos.getTamañoTablero().width/2) - (ancho/2);
		setBounds(x, y, ancho, alto);
	}

	private class Mensaje{
		
		private String mensaje;
		private int tiempo;
		
		public Mensaje(String mensaje, int tiempo) {
			this.mensaje = mensaje;
			this.tiempo = tiempo;
		}
	}
}

package interfaz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import negocio.carta.Carta;

public class PanelDescartes extends JPanel {

	private ArrayList<Carta> cartas;
	private VistaCarta primera;
	private VistaTablero vista;
	private boolean bloqueado;
	
	public PanelDescartes(int jugador, VistaTablero vista) {
		this.vista = vista;
		this.cartas = new ArrayList<>();
		setBounds(Graficos.getPosicionDescarte(jugador));
		setOpaque(false);
		setLayout(new BorderLayout());
	}
	
	public void agregarCarta(Carta carta) {
		this.cartas.add(carta);
		mostrar();
	}

	public void quitarCarta(Carta carta) {
		cartas.remove(carta);
		mostrar();
	}
	
	private void mostrar() {
		if(!cartas.isEmpty()) {
			Carta carta = cartas.get(cartas.size()-1);
			primera = new VistaCarta(carta, VistaCarta.CHICA,null);
			primera.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if(e.getButton() == MouseEvent.BUTTON3) {
						if(!bloqueado) {
							vista.mostrarCartas(cartas, cartas.indexOf(primera.getCarta()));
						}
					}
				}
				@Override
				public void mouseEntered(MouseEvent arg0) {
					if(!bloqueado) {
						primera.setBorder(new EtchedBorder(EtchedBorder.LOWERED, Color.CYAN, Color.BLUE));
					}
				}
				@Override
				public void mouseExited(MouseEvent e) {
					primera.setBorder(null);
				}
			});
			desbloquear();
			removeAll();
			add(primera,BorderLayout.CENTER);
			revalidate();
			repaint();
		}
		else {
			bloquear();
			removeAll();
			revalidate();
			repaint();
		}
	}

	public void vaciar() {
		removeAll();
		revalidate();
		repaint();
		this.cartas.clear();
	}
		
	public void bloquear() {
		this.bloqueado = true;
	}
	
	public void desbloquear() {
		this.bloqueado = false;
	}
	
}

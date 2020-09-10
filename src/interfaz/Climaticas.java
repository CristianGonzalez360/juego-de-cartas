package interfaz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import negocio.carta.Carta;
import negocio.carta.Climatica;

public class Climaticas extends JPanel {

	private Fila fila;
	private ArrayList<Carta> cartas;
	private MouseAdapter seleccion;
	private boolean seleccionable;
	private VistaTablero vistaTablero;
	
	public Climaticas(VistaTablero vistaTablero ) {
		setLayout(new BorderLayout());
		Rectangle r = Graficos.getPosicionClimaticas();
		setBounds(r);
		setOpaque(false);
		
		this.vistaTablero = vistaTablero;
	
		this.fila = new Fila(-1, null);
		this.fila.setPreferredSize(new Dimension((int)r.getWidth(), (int)r.getHeight()));
		add(fila);
		
		this.cartas =  new ArrayList<>();
		
		this.seleccion = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				seleccionar();
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				setBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 165, 0), new Color(255, 69, 0)));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				setBorder(null);
			}
		};
	}
	
	public void modoSeleccion(boolean estado) {
		if(estado == true) {
			addMouseListener(seleccion);
		}
		else {
			removeMouseListener(seleccion);
		}
		seleccionable = estado;
	}
	
	public void seleccionar() {
		if (seleccionable) {
			setBorder(null);
			modoSeleccion(false);
			vistaTablero.jugarCartaSeleccionada();
		}
	}
	
	public void agregarCarta(Climatica climatica) {
		VistaCarta vc = new VistaCarta(climatica, VistaCarta.CHICA, vistaTablero);
		vc.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getButton() == MouseEvent.BUTTON1) {
					seleccionar();
				}
				if(e.getButton() == MouseEvent.BUTTON3) {
					vistaTablero.mostrarCartas(cartas, cartas.indexOf(climatica));
					vc.setBorder(null);
				}
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				if(seleccionable) {
					setBorder(new EtchedBorder(EtchedBorder.LOWERED, Color.ORANGE, Color.RED));
				}
				vc.setBorder(new EtchedBorder(EtchedBorder.LOWERED, Color.CYAN, Color.BLUE));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				vc.setBorder(null);
			}
		});
		
		this.fila.agregarCarta(vc);
		this.cartas.add(climatica);
	}
	
	public boolean contiene(Carta carta) {
		boolean ret = false;
		for(Carta c : cartas) {
			if(c.getNombre().equals(carta.getNombre())) {
				ret = true;
			}
		}
		return ret;
	}

	public void vaciar() {
		cartas.clear();
		fila.vaciar();		
	}
}

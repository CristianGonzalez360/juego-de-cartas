package interfaz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import negocio.carta.Carta;

public class Mano extends JPanel {
	
	private Fila fila;
	private VistaTablero vista;
	private boolean bloqueado;
	private boolean seleccionable;
	private VistaCarta resaltado;
	private VistaCarta seleccionado;
	
	public Mano(VistaTablero vista) {
		setOpaque(false);
		setBounds(Graficos.getPosicionMano());
		setLayout(new BorderLayout());
		this.fila = new Fila(-1, vista);
		add(fila,BorderLayout.CENTER);
		this.vista = vista;
	}

	public void agregarCarta(Carta carta) {
		VistaCarta vc = new VistaCarta(carta, VistaCarta.CHICA, vista);
		vc.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getButton() == MouseEvent.BUTTON1 && seleccionable && !bloqueado) {
					vista.seleccionarCarta(vc.getCarta());
					if(seleccionado != null && !seleccionado.equals(vc)) {
						seleccionado.setBorder(null);
					}
					seleccionado = vc;
					resaltado = vc;
				}
				else if(e.getButton() == MouseEvent.BUTTON3 && !bloqueado) {
					vista.mostrarCartas(fila.getCartas(), fila.getCartas().indexOf(carta));
					vc.setBorder(new EtchedBorder(EtchedBorder.LOWERED, Color.CYAN, Color.BLUE));
				}
			}
			@Override
			public void mouseEntered(MouseEvent arg0) {
				if(seleccionable && !bloqueado) {
					vc.setBorder(new EtchedBorder(EtchedBorder.LOWERED, Color.ORANGE, Color.RED));
				}
				else if (!bloqueado) {
					vc.setBorder(new EtchedBorder(EtchedBorder.LOWERED, Color.CYAN, Color.BLUE));
				}
				if (!bloqueado) {
					if (resaltado != null && !resaltado.equals(vc)) {
						resaltado.setBorder(null);
					}
					resaltado = vc;
				}
			}
			@Override
			public void mouseExited(MouseEvent e) {
				if (!bloqueado && ((seleccionado != null && !seleccionado.equals(vc)) || seleccionado == null)) {
					vc.setBorder(null);
				}
				if(!bloqueado && seleccionado != null) {
					seleccionado.setBorder(new EtchedBorder(EtchedBorder.LOWERED, Color.ORANGE, Color.RED));
					resaltado = seleccionado;
				}
			}
		});
		this.fila.agregarCarta(vc);
	}
	
	public void quitarCarta(Carta carta) {
		this.fila.quitarCarta(carta);
	}

	public void modoSeleccionCarta(boolean estado) {
		this.seleccionable = estado;
	}

	public void vaciar() {
		fila.vaciar();
	}
	
	public void bloquear() {
		bloqueado = true;
	}
	
	public void desbloquear() {
		bloqueado = false;
		if(seleccionado != null) {
			seleccionado.setBorder(new EtchedBorder(EtchedBorder.LOWERED, Color.ORANGE, Color.RED));
		}
	}

	public void deseleccionar() {
		if(this.seleccionado != null) {
			this.seleccionado.setBorder(null);
		}
		this.seleccionado = null;
	}

}

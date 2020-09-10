package interfaz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import negocio.carta.Carta;
import negocio.carta.Lider;

public class PanelLider extends JPanel {
	

	private VistaTablero vista;
	private boolean bloqueado;
	private boolean seleccionable;
	private boolean seleccionado;
	private VistaCarta vistaCarta;;
	
	public PanelLider(int jugador, VistaTablero vista) {
		this.vista = vista;
		setOpaque(false);
		setLayout(new BorderLayout());
		setBounds(Graficos.getPosicionLider(jugador));
	}
	
	public void modoSeleccion(boolean estado) {
		this.seleccionable = estado;
	}
	
	public void setLider(Lider lider){
		removeAll();
		revalidate();
		vistaCarta = new VistaCarta(lider, VistaCarta.CHICA, vista);
		add(vistaCarta,BorderLayout.CENTER);
		vistaCarta.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getButton() == MouseEvent.BUTTON1 && seleccionable && !bloqueado) {
					vista.seleccionarCarta(vistaCarta.getCarta());
					vistaCarta.setBorder(new EtchedBorder(EtchedBorder.LOWERED, Color.ORANGE, Color.RED));
					seleccionado = true;
				}
				else if(e.getButton() == MouseEvent.BUTTON3 && !bloqueado) {
					vista.mostrarCarta((Carta)lider);
					vistaCarta.setBorder(new EtchedBorder(EtchedBorder.LOWERED, Color.CYAN, Color.BLUE));
				}
			}
			@Override
			public void mouseEntered(MouseEvent arg0) {
				if(seleccionable && !bloqueado) {
					vistaCarta.setBorder(new EtchedBorder(EtchedBorder.LOWERED, Color.ORANGE, Color.RED));
				}
				else if (!bloqueado) {
					vistaCarta.setBorder(new EtchedBorder(EtchedBorder.LOWERED, Color.CYAN, Color.BLUE));
				}
			}
			@Override
			public void mouseExited(MouseEvent e) {
				if(!seleccionado && !bloqueado) {
					vistaCarta.setBorder(null);
				}
			}
		});
		repaint();
	}
	
	public void bloquear() {
		bloqueado = true;
	}
	
	public void desbloquear() {
		bloqueado = false;
		if(seleccionado) {
			vistaCarta.setBorder(new EtchedBorder(EtchedBorder.LOWERED, Color.ORANGE, Color.RED));
		}
	}
	
	public void deseleccionar() {
		seleccionado = false;
		vistaCarta.setBorder(null);
	}
}

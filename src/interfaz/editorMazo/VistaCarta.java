package interfaz.editorMazo;

import javax.swing.JLabel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.border.EtchedBorder;

import interfaz.Graficos;

import java.awt.Color;
import negocio.carta.Carta;
import negocio.carta.Lider;

import javax.swing.SwingConstants;

public class VistaCarta extends JLabel {

	public static int ANCHO = 83;
	public static int ALTO = 150;
	public static int GRANDE = 0;
	public static int CHICA = 1;
	
	private Carta carta;
	
	public VistaCarta(Carta carta) {
		setHorizontalAlignment(SwingConstants.CENTER);
		setBorder(null);
		
		this.carta = carta;
		
		setPreferredSize(Graficos.getTamañoVistaCartaGrande());
		setIcon(Graficos.getImagenCartaGrande(carta.getRutaImagen()));
	}

	public Carta getCarta() {
		return this.carta;
	}
	
	@Override
	public boolean equals(Object o) {
		boolean ret = false;
		
		if(o instanceof VistaCarta) {
			VistaCarta vc = (VistaCarta)o;
			ret = this.getCarta().equals(vc.getCarta());
		}
		
		return ret;
	}

}

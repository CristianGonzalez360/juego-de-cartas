package interfaz;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

public class PanelClimas extends JPanel {
	
	private PanelClima lluvia;
	private PanelClima niebla;
	private PanelClima frio;
	private PanelClima[] climas;
	private Dimension tamano;

	public PanelClimas(int jugador) {
		this.tamano = Graficos.getPosicionCampo(jugador).getSize();
		setPreferredSize(tamano);
		setOpaque(false);
		setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

		this.frio = new PanelClima(Fila.CUERPOACUERPO);
		this.niebla = new PanelClima(Fila.ADISTANCIA);
		this.lluvia = new PanelClima(Fila.ASEDIO);
		
		climas = new PanelClima[3];
		climas[0] = frio;
		climas[1] = niebla;
		climas[2] = lluvia;
			
		if (jugador == 0) {
			add(frio);
			add(niebla);
			add(lluvia);
		} 
		else if (jugador == 1) {	
			add(lluvia);
			add(niebla);
			add(frio);
		}
	}
	
	public void clima(int fila) {
		if(fila>=0 && fila<3) {
			this.climas[fila].clima();
		}
		else if(fila == 3){
			this.climas[1].clima();
			this.climas[2].clima();
		}
	}
	
	public void limpiar() {
		for(PanelClima clima : climas) {
			clima.limpiar();
		}
	}
	
	private class PanelClima extends JLabel {

		private int fila;
		
		public PanelClima(int fila) {
			this.fila = fila;
			setPreferredSize(new Dimension(tamano.width, tamano.height/3));
			setHorizontalAlignment(SwingConstants.CENTER);
			
		}
		
		public void limpiar() {
			setIcon(null);
			setBorder(null);
		}

		public void clima() {
			setIcon(Graficos.getImagenClima(fila));
		}
	}
}

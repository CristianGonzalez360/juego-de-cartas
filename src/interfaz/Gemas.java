package interfaz;

import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class Gemas extends JPanel {

	public Gemas(int jugador) {
		setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		setBounds(Graficos.getPosicionPanelGemas(jugador));
		setOpaque(false);
		iniciar();
	}
	
	private void iniciar() {
		for(int i = 0; i < 2; i++) {
			JLabel gema = new JLabel();
			gema.setIcon(Graficos.getImagenGema(true));
			add(gema);
		}
	}
	public void perder() {
		remove(0);
		JLabel gema = new JLabel();
		gema.setIcon(Graficos.getImagenGema(false));
		add(gema);
	}
	
	public void reiniciar() {
		removeAll();
		iniciar();
	}
}

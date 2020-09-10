package interfaz;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class Paso extends JLabel {

	public Paso(int jugador) {
		setBounds(Graficos.getPosicionPaso(jugador));
		setForeground(Color.ORANGE);
		setHorizontalAlignment(SwingConstants.CENTER);
		setFont(new Font("gwent", Font.PLAIN, Graficos.getTamañoFuente(50)));
	}
	
	public void paso() {
		setText("paso");
	}
	
	public void reiniciar() {
		setText("");
	}
}

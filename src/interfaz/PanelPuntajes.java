package interfaz;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import negocio.calculador.Calculadora;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

public class PanelPuntajes extends JPanel {

	private Marcador cuerpoACuerpo;
	private Marcador aDistancia;
	private Marcador asedio;
	
	private Marcador[] puntajes;
	
	public PanelPuntajes(int jugador) {
		FlowLayout flowLayout = new FlowLayout();
		flowLayout.setVgap(0);
		flowLayout.setHgap(0);
		
		setLayout(flowLayout);
		cuerpoACuerpo = new Marcador();
		aDistancia = new Marcador();
		asedio = new Marcador();
		
		puntajes = new Marcador[3];
		puntajes[0] = cuerpoACuerpo;
		puntajes[1] = aDistancia;
		puntajes[2] = asedio;
		
		setOpaque(false);
		setBounds(Graficos.getPosicionPanelPuntajes(jugador));
		
		if (jugador == 0) {
			add(cuerpoACuerpo);
			add(aDistancia);
			add(asedio);
		} 
		else if (jugador == 1) {	
			add(asedio);
			add(aDistancia);
			add(cuerpoACuerpo);	
		}
		
		iniciar();
	}
	
	public void mostrarPuntos(Calculadora calculadora) {
		for (int i = 0; i < puntajes.length; i++) {
			int puntos = calculadora.getFuerzaFila(i);
			puntajes[i].mostrarPuntos(puntos);
		}
	}
	
	private class Marcador extends JLabel{
				
		public Marcador() {
			setForeground(Color.BLACK);
			setFont(new Font("Gwent", Font.PLAIN, Graficos.getTamañoFuente(50)));
			setPreferredSize(Graficos.getTamañoMarcador());
			setHorizontalAlignment(SwingConstants.CENTER);
		}
		
		public void mostrarPuntos(int puntos) {
			this.setText(puntos+"");
		}		
	}

	public void iniciar() {
		for (int i = 0; i < puntajes.length; i++) {
			puntajes[i].setText("0");
		}
	}
}

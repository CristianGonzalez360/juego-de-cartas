package interfaz;

import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;

import negocio.calculador.Calculadora;
import negocio.carta.Carta;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PanelCampo extends JPanel {

	private Fila[] filas;
	private VistaTablero vistaTablero;
	private boolean seleccionarCampo;
	private boolean seleccionarFila;
	private boolean seleccionarCarta;

	public PanelCampo(int jugador, VistaTablero vistaTablero) {
		this.vistaTablero = vistaTablero;
		
		setOpaque(false);
		setPreferredSize(Graficos.getTamañoPanelCampo());	
		setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		
		Fila cuerpoACuerpo = new Fila(Fila.CUERPOACUERPO, vistaTablero);
		Fila aDistancia = new Fila(Fila.ADISTANCIA, vistaTablero);
		Fila asedio = new Fila(Fila.ASEDIO, vistaTablero);
		
		cuerpoACuerpo.setCampo(this);
		aDistancia.setCampo(this);
		asedio.setCampo(this);

		this.filas = new Fila[3];
		this.filas[0] = cuerpoACuerpo;
		this.filas[1] = aDistancia;
		this.filas[2] = asedio;
		setBorder(new LineBorder(Color.WHITE));
		
		if (jugador == 0) {
			add(cuerpoACuerpo);
			add(aDistancia);
			add(asedio);
		} else if (jugador == 1) {
			add(asedio);
			add(aDistancia);
			add(cuerpoACuerpo);
		}
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getButton() == MouseEvent.BUTTON1) {
					seleccinar();
				}
			}
			@Override
			public void mouseEntered(MouseEvent arg0) {
				resaltar(true);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				resaltar(false);
			}
		});
	}

	public void jugarCarta(Carta carta) {
		int fila = carta.getFila();	
		this.filas[fila].agregarCarta(carta);
		modoSeleccionFila(0, false);
	}

	public void modoSeleccionFila(int fila, boolean estado) {
		if (fila>=-1 && fila<3) {
			for (int i = 0; i < 3; i++) {
				this.filas[i].modoSeleccion(false);
			} 
			this.seleccionarFila = estado;
		}
		if (estado == true) {
			if (fila < 3 && fila >= 0) {
				this.filas[fila].modoSeleccion(true);
			} else if (fila < 0) {
				this.filas[0].modoSeleccion(true);
				this.filas[1].modoSeleccion(true);
			} 
		}
	}
	
	public void modoSeleccionCarta(boolean estado) {
		for(int i = 0; i<3; i++) {
			this.filas[i].modoDevolverCarta(estado);
		}
		this.seleccionarCarta = estado;
	}
	
	public void modoMostrarCartas(boolean estado) {
		for(int i = 0; i<3; i++) {
			this.filas[i].modoMostrarCarta(estado);
		}
	}
	
	public void modoSeleccion(boolean estado) {
		this.seleccionarCampo = estado;
	}

	public void quitarCarta(Carta carta) {
		if(carta.getFila()<3 && carta.getFila()>=0) {
			this.filas[carta.getFila()].quitarCarta(carta);
		}
	}

	public void vaciar() {
		for (int i = 0; i < filas.length; i++) {
			this.filas[i].vaciar();
		}
	}
	
	public void bloquear() {
		setBorder(null);
		boolean seleccionarCampo = this.seleccionarCampo;
		boolean seleccionarFila = this.seleccionarFila;
 		boolean seleccionarCarta = this.seleccionarCarta;
 		
 		modoSeleccion(false);
 		modoSeleccionCarta(false);
 		modoMostrarCartas(false);
 		
 		for(int i = 0; i<3; i++) {
 			this.filas[i].bloquear();
		}
 		
 		this.seleccionarCampo = seleccionarCampo;
 		this.seleccionarFila = seleccionarFila;
 		this.seleccionarCarta = seleccionarCarta;
	}
	
	public void desbloquear() {

		modoSeleccion(seleccionarCampo);
		if(this.seleccionarFila) {
			for(int i = 0; i<3; i++) {
	 			this.filas[i].desbloquear();
			}
		}
		modoSeleccionCarta(seleccionarCarta);
		modoMostrarCartas(true);
	}

	public void seleccinar() {
		if(seleccionarCampo) {
			modoSeleccion(false);
			setBorder(null);
			this.vistaTablero.jugarCartaSeleccionada();
		}
	}
	
	public void resaltar(boolean estado) {
		if(seleccionarCampo && estado) {
			setBorder(new EtchedBorder(EtchedBorder.LOWERED, Color.ORANGE, Color.RED));
		}
		else {
			setBorder(null);
		}
	}

	public void mostrarPuntos(Calculadora calculadora) {
		for(int i = 0;  i<filas.length; i++) {
			filas[i].mostrarPuntos(calculadora);
		}
	}
}
